<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vinheria Agnello — Solicitar Acesso</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@400;500;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/auth.css' />">
</head>
<body class="auth-register">

<div class="bg-image"></div>
<div class="bg-overlay"></div>
<div class="bg-glow"></div>

<div class="login-wrapper">
    <div class="login-box">
        <div class="brand">
            <div class="brand-top-line"></div>
            <h1 class="brand-name">VINHERIA<br>AGNELLO</h1>
            <div class="brand-divider">
                <div class="line line-left"></div>
                <span class="brand-est">Desde 1985</span>
                <div class="line line-right"></div>
            </div>
            <div class="brand-subtitle">Solicitar Acesso</div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="user" method="post">
            <input type="hidden" name="action" value="register">

            <div class="form-group">
                <label for="register-name">Nome Completo</label>
                <input type="text" id="register-name" name="name" required autocomplete="name" placeholder="Seu nome completo">
            </div>

            <div class="form-group">
                <label for="register-email">E-mail</label>
                <input type="email" id="register-email" name="email" required autocomplete="email" placeholder="seu@email.com">
            </div>

            <div class="form-group">
                <label for="register-password">Senha</label>
                <input type="password" id="register-password" name="password" required minlength="4" autocomplete="new-password" placeholder="Mínimo 4 caracteres">
            </div>

            <button type="submit" class="btn-signin">Entrar para a Adega</button>
        </form>

        <a href="user?action=login" class="forgot-link">Já tem conta? Entrar</a>
    </div>
</div>

</body>
</html>
