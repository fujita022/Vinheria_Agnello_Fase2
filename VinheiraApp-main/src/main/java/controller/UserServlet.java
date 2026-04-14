package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String LOGIN_PAGE = "/WEB-INF/login.jsp";
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("login")) {
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);

        } else if (action.equals("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("user?action=login");

        } else if (action.equals("register")) {
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (userDAO.findByEmail(email) != null) {
                request.setAttribute("error", "Este e-mail já está cadastrado.");
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                return;
            }

            User newUser = new User(name, email, password);
            userDAO.insert(newUser);

            request.setAttribute("success", "Cadastro realizado! Faça o login.");
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);

        } else {
            // Ação padrão: login
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = userDAO.authenticate(email, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("error", "E-mail ou senha inválidos.");
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            }
        }
    }
}
