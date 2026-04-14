<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="<c:url value='/css/layout.css' />">

<header>
    <a href="<c:url value='/' />" class="logo">
        <div class="logo-icon">
            <div class="glass"></div>
        </div>

        <div class="logo-text">
            <span>Vinheria</span>Agnello
        </div>
    </a>
    <nav>
        <a href="#">Explorar por momento</a>
        <a href="wine?action=list">Sua Seleção</a>
        <a href="cart?action=view">Minha adega</a>
        <a href="#">Somelier de IA</a>
    </nav>
    <div class="header-icons">
        <i class="fas fa-search"></i>
        <c:choose>
            <c:when test="${not empty sessionScope.loggedUser}">
                <span class="user-greeting">Olá, ${sessionScope.loggedUser.name}</span>
                <a href="<c:url value='/user?action=logout' />" title="Sair">
                    <i class="fas fa-sign-out-alt"></i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/user?action=login' />" title="Entrar">
                    <i class="fas fa-user"></i>
                </a>
            </c:otherwise>
        </c:choose>
        <a href="cart?action=view" title="Minha adega">
            <i class="fas fa-shopping-bag"></i>
            <c:if test="${sessionScope.cartCount > 0}">
                <span class="cart-badge">${sessionScope.cartCount}</span>
            </c:if>
        </a>
    </div>
</header>