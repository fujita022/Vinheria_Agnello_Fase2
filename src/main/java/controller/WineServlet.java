package controller;

import dao.WineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Wine;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/wine")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class WineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private WineDAO wineDAO = new WineDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            List<Wine> wines = wineDAO.list();
            request.setAttribute("wines", wines);
            request.getRequestDispatcher("/WEB-INF/list-wines.jsp").forward(request, response);

        } else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            wineDAO.delete(id);
            response.sendRedirect("wine?action=list");

        } else if (action.equals("edit")) {
            // Abre o formulário preenchido para edição
            int id = Integer.parseInt(request.getParameter("id"));
            Wine wine = wineDAO.findById(id);

            if (wine != null) {
                request.setAttribute("wine", wine);
                request.getRequestDispatcher("/WEB-INF/form-wine.jsp").forward(request, response);
            } else {
                response.sendRedirect("wine?action=list");
            }

        } else if (action.equals("new")) {
            // Abre o formulário vazio para novo cadastro
            request.getRequestDispatcher("/WEB-INF/form-wine.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();

        String uploadPath = getServletContext().getRealPath("/") + "images";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        if (fileName != null && !fileName.isEmpty()) {
            filePart.write(uploadPath + File.separator + fileName);
        } else if (idParam != null) {
            // Se for edição e não subiu foto nova, mantém a antiga
            Wine oldWine = wineDAO.findById(Integer.parseInt(idParam));
            fileName = oldWine.getImage();
        }

        Wine wine = new Wine();
        wine.setName(name);
        wine.setDescription(description);
        wine.setPrice(price);
        wine.setImage(fileName); // Salva o nome do arquivo no banco

        if (idParam != null && !idParam.isEmpty() && !idParam.equals("0")) {
            wine.setId(Integer.parseInt(idParam));
            wineDAO.update(wine);
        } else {
            wineDAO.insertWine(wine);
        }

        response.sendRedirect("wine?action=list");
    }
}