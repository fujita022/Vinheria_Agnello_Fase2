<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Vinheria Agnello - Gerenciamento</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --bg-dark: #0b0b0b;
            --card-bg: #1a1a1a;
            --gold: #d4af37;
            --text-gray: #888;
            --danger: #ff4d4d;
        }
        body {
            background-color: var(--bg-dark);
            color: white;
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 120px 0 0 0;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px 80px 20px;
        }
        .section-header {
            margin-bottom: 40px;
            border-left: 4px solid var(--gold);
            padding-left: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .btn-add {
            background-color: var(--gold);
            color: black;
            padding: 12px 24px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        /* GRID PARA FICAR LADO A LADO */
        .wine-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 30px;
            width: 100%;
        }
        .wine-card {
            background-color: var(--card-bg);
            border-radius: 12px;
            padding: 20px;
            border: 1px solid #333;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            transition: 0.3s;
        }
        .wine-card:hover { transform: translateY(-10px); border-color: var(--gold); }
        .wine-image-placeholder {
            width: 100%;
            height: 300px;
            background: #252525;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .wine-image-placeholder img { max-height: 90%; max-width: 90%; object-fit: contain; }
        .price-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-top: 1px solid #333;
            padding-top: 15px;
        }
        .price { font-size: 22px; font-weight: bold; color: var(--gold); }
        .actions { display: flex; gap: 10px; }
        .btn-action {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            border: 1px solid #444;
            color: white;
        }
        .btn-edit:hover { border-color: var(--gold); color: var(--gold); }
        .btn-delete:hover { border-color: var(--danger); color: var(--danger); }
    </style>
</head>
<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="section-header">
        <div>
            <p>Sua Seleção de Vinhos </p>
            <h2>Adega Agnello</h2>
        </div>
        <a href="wine?action=new" class="btn-add">
            <i class="fa fa-plus"></i> Novo Vinho
        </a>
    </div>

    <div class="wine-grid">
        <c:forEach var="wine" items="${wines}">
            <div class="wine-card">
                <div class="wine-image-placeholder">
                    <img src="<c:url value='/images/${wine.image}'/>" alt="${wine.name}">
                </div>
                <div class="wine-info">
                    <h3>${wine.name}</h3>
                    <p style="color: var(--text-gray); font-size: 14px; height: 50px; overflow: hidden;">
                            ${wine.description}
                    </p>
                </div>
                <div class="price-row">
                    <div class="price">R$ ${wine.price}</div>
                    <div class="actions">
                        <a href="wine?action=edit&id=${wine.id}" class="btn-action btn-edit"><i class="fa fa-edit"></i></a>
                        <a href="wine?action=delete&id=${wine.id}" class="btn-action btn-delete" onclick="return confirm('Excluir ${wine.name}?')"><i class="fa fa-trash"></i></a>
                        <a href="cart?action=add&id=${wine.id}" class="btn-action btn-cart" title="Adicionar ao Carrinho"><i class="fa fa-shopping-cart"></i>
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>