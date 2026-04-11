<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>${not empty wine ? 'Editar Vinho' : 'Novo Vinho'} - Agnello</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root { --bg-dark: #0b0b0b; --card-bg: #1a1a1a; --gold: #d4af37; --text-gray: #888; }
        body { background-color: var(--bg-dark); color: white; font-family: 'Inter', sans-serif; padding-top: 120px; }
        .container { max-width: 600px; margin: 0 auto; padding: 40px; background: var(--card-bg); border-radius: 12px; border: 1px solid #333; }
        h2 { border-left: 4px solid var(--gold); padding-left: 15px; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: var(--gold); font-size: 14px; }
        input, textarea { width: 100%; padding: 12px; background: #252525; border: 1px solid #444; color: white; border-radius: 6px; box-sizing: border-box; }
        input:focus { border-color: var(--gold); outline: none; }
        .btn-save { background: var(--gold); color: black; border: none; padding: 15px 30px; border-radius: 6px; font-weight: bold; cursor: pointer; width: 100%; transition: 0.3s; }
        .btn-save:hover { background: #b8962d; }
        .btn-back { display: block; text-align: center; margin-top: 20px; color: var(--text-gray); text-decoration: none; font-size: 14px; }
    </style>
</head>
<body>

<%@ include file="header.jsp" %>

<div class="container">
    <h2>${not empty wine ? 'Editar Vinho' : 'Cadastrar Novo Vinho'}</h2>

    <%-- ÚNICO FORMULÁRIO COM ENCTYPE --%>
    <form action="wine" method="post" enctype="multipart/form-data">

        <%-- ID oculto para edição --%>
        <input type="hidden" name="id" value="${wine.id}">

        <div class="form-group">
            <label>Nome do Vinho</label>
            <input type="text" name="name" value="${wine.name}" required placeholder="Ex: Merlot Reserva">
        </div>

        <div class="form-group">
            <label>Descrição</label>
            <textarea name="description" rows="4" required>${wine.description}</textarea>
        </div>

        <div class="form-group">
            <label>Preço (R$)</label>
            <input type="number" step="0.01" name="price" value="${wine.price}" required>
        </div>

        <div class="form-group">
            <label>Imagem do Vinho</label>
            <input type="file" name="image" accept="image/*">

            <c:if test="${not empty wine.image}">
                <p style="font-size: 11px; color: var(--text-gray); margin-top: 5px;">
                    Imagem atual: <strong>${wine.image}</strong> (deixe vazio para manter)
                </p>
            </c:if>
        </div>

        <button type="submit" class="btn-save">
            <i class="fa fa-save"></i> ${not empty wine ? 'Salvar Alterações' : 'Cadastrar Vinho'}
        </button>

        <a href="wine?action=list" class="btn-back">Voltar para a lista</a>
    </form>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>