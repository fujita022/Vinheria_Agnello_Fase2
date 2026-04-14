<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vinheria Agnello - Minha Adega</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/cart.css' />">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="title-box">
        <p>Sua compra</p>
        <h2>Minha Adega</h2>
    </div>

    <c:if test="${not empty cartMessage}">
        <div class="alert-success">
            ${cartMessage}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty cartItems}">
            <div class="empty-box">
                <i class="fa-solid fa-wine-bottle"></i>
                <h3>Sua adega está vazia</h3>
                <p>Adicione vinhos para montar seu pedido.</p>
                <a href="wine?action=list" class="btn-main" style="max-width: 260px;">Explorar Vinhos</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cart-layout">
                <div class="cart-list">
                    <div class="cart-header">
                        <span>Vinho</span>
                        <span>Preço</span>
                        <span>Quantidade</span>
                        <span>Ação</span>
                    </div>

                    <c:forEach var="item" items="${cartItems}">
                        <div class="cart-item">
                            <div class="item-main">
                                <img class="item-image" src="<c:url value='/images/${item.wine.image}'/>" alt="${item.wine.name}">
                                <div>
                                    <h3>${item.wine.name}</h3>
                                    <p>Subtotal: R$ <fmt:formatNumber value="${item.subtotal}" minFractionDigits="2" maxFractionDigits="2"/></p>
                                </div>
                            </div>
                            <div class="price">
                                R$ <fmt:formatNumber value="${item.wine.price}" minFractionDigits="2" maxFractionDigits="2"/>
                            </div>
                            <div>
                                <form class="qty-form" action="cart" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${item.wine.id}">
                                    <input type="number" name="quantity" min="0" value="${item.quantity}">
                                    <button class="btn-mini" type="submit">Atualizar</button>
                                </form>
                            </div>
                            <div>
                                <a class="btn-remove" href="cart?action=remove&id=${item.wine.id}" title="Remover item">
                                    <i class="fa fa-trash"></i>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="summary">
                    <h3>Resumo do Pedido</h3>
                    <div class="summary-row">
                        <span>Itens</span>
                        <span>${cartItemsCount}</span>
                    </div>
                    <div class="summary-row total">
                        <span>Total</span>
                        <span>R$ <fmt:formatNumber value="${cartTotal}" minFractionDigits="2" maxFractionDigits="2"/></span>
                    </div>

                    <a href="cart?action=checkout" class="btn-main">Finalizar Compra</a>
                    <a href="wine?action=list" class="btn-secondary">Continuar Comprando</a>
                    <a href="cart?action=clear" class="btn-secondary">Limpar Carrinho</a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
