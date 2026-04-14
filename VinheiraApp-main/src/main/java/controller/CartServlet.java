package controller;

import dao.WineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
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

	private final WineDAO wineDAO = new WineDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null || action.equals("view")) {
			loadCartView(request, response);
			return;
		}

		if (action.equals("add")) {
			int wineId = parseInt(request.getParameter("id"));
			if (wineId > 0) {
				Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
				int currentQty = cart.getOrDefault(wineId, 0);
				cart.put(wineId, currentQty + 1);
				updateCartBadge(request.getSession(), cart);
			}

			String referer = request.getHeader("referer");
			if (referer != null && !referer.trim().isEmpty()) {
				response.sendRedirect(referer);
			} else {
				response.sendRedirect("cart?action=view");
			}
			return;
		}

		if (action.equals("remove")) {
			int wineId = parseInt(request.getParameter("id"));
			if (wineId > 0) {
				Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
				cart.remove(wineId);
				updateCartBadge(request.getSession(), cart);
			}

			response.sendRedirect("cart?action=view");
			return;
		}

		if (action.equals("clear")) {
			Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
			cart.clear();
			updateCartBadge(request.getSession(), cart);
			response.sendRedirect("cart?action=view");
			return;
		}

		if (action.equals("checkout")) {
			Map<Integer, Integer> cart = getOrCreateCart(request.getSession());

			if (!cart.isEmpty()) {
				cart.clear();
				updateCartBadge(request.getSession(), cart);
				request.getSession().setAttribute("cartMessage", "Compra finalizada com sucesso! Sua adega sera preparada para envio.");
			} else {
				request.getSession().setAttribute("cartMessage", "Sua adega esta vazia. Adicione vinhos antes de finalizar a compra.");
			}

			response.sendRedirect("cart?action=view");
			return;
		}

		response.sendRedirect("cart?action=view");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null || !action.equals("update")) {
			response.sendRedirect("cart?action=view");
			return;
		}

		int wineId = parseInt(request.getParameter("id"));
		int quantity = parseInt(request.getParameter("quantity"));

		Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
		if (wineId > 0) {
			if (quantity <= 0) {
				cart.remove(wineId);
			} else {
				cart.put(wineId, quantity);
			}
			updateCartBadge(request.getSession(), cart);
		}

		response.sendRedirect("cart?action=view");
	}

	private void loadCartView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<Integer, Integer> cart = getOrCreateCart(request.getSession());
		List<CartItem> cartItems = new ArrayList<>();
		double total = 0.0;

		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
			Integer wineId = entry.getKey();
			Integer quantity = entry.getValue();

			Wine wine = wineDAO.findById(wineId);
			if (wine == null) {
				continue;
			}

			CartItem item = new CartItem(wine, quantity);
			cartItems.add(item);
			total += item.getSubtotal();
		}

		request.setAttribute("cartItems", cartItems);
		request.setAttribute("cartTotal", total);
		request.setAttribute("cartItemsCount", getTotalItems(cart));

		Object message = request.getSession().getAttribute("cartMessage");
		if (message != null) {
			request.setAttribute("cartMessage", message);
			request.getSession().removeAttribute("cartMessage");
		}

		request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
	}

	private Map<Integer, Integer> getOrCreateCart(HttpSession session) {
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_SESSION_KEY);
		if (cart == null) {
			cart = new LinkedHashMap<>();
			session.setAttribute(CART_SESSION_KEY, cart);
		}
		return cart;
	}

	private int getTotalItems(Map<Integer, Integer> cart) {
		int totalItems = 0;
		for (Integer qty : cart.values()) {
			if (qty != null) {
				totalItems += qty;
			}
		}
		return totalItems;
	}

	private void updateCartBadge(HttpSession session, Map<Integer, Integer> cart) {
		session.setAttribute("cartCount", getTotalItems(cart));
	}

	private int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}
}
