package controller;

import dao.OrderDAO;
import dao.WineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.User;
import model.Wine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String CART_SESSION_KEY = "cart";

    private final WineDAO  wineDAO  = new WineDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "view";

        switch (action) {
            case "add"      -> handleAdd(request, response);
            case "remove"   -> handleRemove(request, response);
            case "clear"    -> handleClear(request, response);
            case "checkout" -> handleCheckout(request, response);
            default         -> loadCartView(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("update".equals(action)) {
            int wineId   = parseInt(request.getParameter("id"));
            int quantity = parseInt(request.getParameter("quantity"));
            Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
            if (wineId > 0) {
                if (quantity <= 0) cart.remove(wineId);
                else               cart.put(wineId, quantity);
                updateCartBadge(request.getSession(), cart);
            }
        }
        response.sendRedirect("cart?action=view");
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int wineId = parseInt(request.getParameter("id"));
        if (wineId > 0) {
            Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
            cart.put(wineId, cart.getOrDefault(wineId, 0) + 1);
            updateCartBadge(request.getSession(), cart);
        }
        String referer = request.getHeader("referer");
        response.sendRedirect(referer != null && !referer.isBlank() ? referer : "cart?action=view");
    }

    private void handleRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int wineId = parseInt(request.getParameter("id"));
        if (wineId > 0) {
            Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
            cart.remove(wineId);
            updateCartBadge(request.getSession(), cart);
        }
        response.sendRedirect("cart?action=view");
    }

    private void handleClear(HttpServletRequest request, HttpServletResponse response) throws IOException {
        getOrCreateCart(request.getSession()).clear();
        updateCartBadge(request.getSession(), getOrCreateCart(request.getSession()));
        response.sendRedirect("cart?action=view");
    }

    private void handleCheckout(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // Não autenticado – redireciona para login
            response.sendRedirect("WEB-INF/login.jsp");
            return;
        }

        Map<Integer, Integer> cart = getOrCreateCart(session);

        if (cart.isEmpty()) {
            session.setAttribute("cartMessage", "Sua adega está vazia. Adicione vinhos antes de finalizar a compra.");
            response.sendRedirect("cart?action=view");
            return;
        }

        // Monta lista de CartItem e calcula total
        List<CartItem> cartItems = new ArrayList<>();
        double total = 0.0;

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Wine wine = wineDAO.findById(entry.getKey());
            if (wine == null) continue;
            CartItem item = new CartItem(wine, entry.getValue());
            cartItems.add(item);
            total += item.getSubtotal();
        }

        try {
            int orderId = orderDAO.createOrder(user.getId(), cartItems, total);
            cart.clear();
            updateCartBadge(session, cart);
            session.setAttribute("cartMessage",
                    "✅ Pedido #" + orderId + " confirmado! Sua adega será preparada para envio.");
        } catch (RuntimeException e) {
            session.setAttribute("cartMessage",
                    "❌ Erro ao finalizar pedido: " + e.getMessage() + ". Tente novamente.");
        }

        response.sendRedirect("cart?action=view");
    }

    private void loadCartView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
        List<CartItem> cartItems = new ArrayList<>();
        double total = 0.0;

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Wine wine = wineDAO.findById(entry.getKey());
            if (wine == null) continue;
            CartItem item = new CartItem(wine, entry.getValue());
            cartItems.add(item);
            total += item.getSubtotal();
        }

        request.setAttribute("cartItems",      cartItems);
        request.setAttribute("cartTotal",      total);
        request.setAttribute("cartItemsCount", getTotalItems(cart));

        Object message = request.getSession().getAttribute("cartMessage");
        if (message != null) {
            request.setAttribute("cartMessage", message);
            request.getSession().removeAttribute("cartMessage");
        }

        request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getOrCreateCart(HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    private int getTotalItems(Map<Integer, Integer> cart) {
        return cart.values().stream().mapToInt(q -> q == null ? 0 : q).sum();
    }

    private void updateCartBadge(HttpSession session, Map<Integer, Integer> cart) {
        session.setAttribute("cartCount", getTotalItems(cart));
    }

    private int parseInt(String value) {
        try { return Integer.parseInt(value); } catch (Exception e) { return 0; }
    }
}