@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String idParam = request.getParameter("id");
    String name = request.getParameter("name");
    String description = request.getParameter("description");
    double price = Double.parseDouble(request.getParameter("price"));

    Part filePart = request.getPart("image");
    String fileName = filePart.getSubmittedFileName();

    String uploadPath = "/home/site/wwwroot/images";
    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) uploadDir.mkdirs();

    if (fileName != null && !fileName.isEmpty()) {
        filePart.write(uploadPath + File.separator + fileName);
    } else if (idParam != null && !idParam.isEmpty()) {
        Wine oldWine = wineDAO.findById(Integer.parseInt(idParam));
        fileName = oldWine.getImage();
    }

    Wine wine = new Wine();
    wine.setName(name);
    wine.setDescription(description);
    wine.setPrice(price);
    wine.setImage(fileName);

    // Corrigido: verifica se idParam é não nulo E não vazio antes de parsear
    if (idParam != null && !idParam.isEmpty() && !idParam.equals("0")) {
        wine.setId(Integer.parseInt(idParam));
        wineDAO.update(wine);
    } else {
        wineDAO.insertWine(wine);
    }

    response.sendRedirect("wine?action=list");
}