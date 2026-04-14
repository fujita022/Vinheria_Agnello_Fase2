<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vinheria Agnello — Acesso Reservado</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@400;500;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/auth.css' />">

    <style>
        #age-gate-overlay {
            position: fixed;
            top: 0; left: 0; width: 100%; height: 100%;
            background: rgba(0, 0, 0, 0.9);
            backdrop-filter: blur(10px);
            z-index: 10000;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .age-gate-box {
            background: #1a1a1a;
            border: 1px solid #d4af37;
            padding: 40px;
            text-align: center;
            max-width: 400px;
            box-shadow: 0 0 30px rgba(212, 175, 55, 0.2);
        }
        .age-gate-box h2 {
            font-family: 'Cormorant Garamond', serif;
            color: #d4af37;
            margin-bottom: 15px;
            letter-spacing: 2px;
        }
        .age-gate-box p {
            color: #888;
            font-size: 14px;
            margin-bottom: 25px;
        }
        .age-buttons { display: flex; gap: 15px; justify-content: center; }
        .btn-age {
            padding: 10px 25px;
            cursor: pointer;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 12px;
            transition: 0.3s;
        }
        .btn-yes { background: #d4af37; color: black; border: none; }
        .btn-no { background: transparent; color: #888; border: 1px solid #444; }
        .btn-yes:hover { background: #b8962d; }
        .btn-no:hover { border-color: #ff4d4d; color: #ff4d4d; }
    </style>
</head>
<body class="auth-login">

<div id="age-gate-overlay">
    <div class="age-gate-box">
        <h2>VERIFICAÇÃO DE IDADE</h2>
        <p>Você tem 18 anos ou mais para acessar este conteúdo?</p>
        <div class="age-buttons">
            <button class="btn-age btn-yes" onclick="verifyAge(true)">Sim</button>
            <button class="btn-age btn-no" onclick="verifyAge(false)">Não</button>
        </div>
    </div>
</div>

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
            <div class="brand-subtitle">Acesso Reservado</div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form action="user" method="post">
            <input type="hidden" name="action" value="login">
            <div class="form-group">
                <label for="login-email">E-mail</label>
                <input type="email" id="login-email" name="email" required autocomplete="email" placeholder="seu@email.com">
            </div>
            <div class="form-group">
                <label for="login-password">Senha</label>
                <input type="password" id="login-password" name="password" required autocomplete="current-password" placeholder="••••••••">
            </div>
            <button type="submit" class="btn-signin">Entrar</button>
        </form>

        <a href="user?action=register" class="register-link">Solicitar acesso</a>
    </div>
</div>

<script>
    function verifyAge(isAdult) {
        if (isAdult) {
            document.getElementById('age-gate-overlay').style.display = 'none';
            sessionStorage.setItem('isAdult', 'true');
        } else {
            window.location.href = "https://www.google.com";
        }
    }

    window.onload = function() {
        if (sessionStorage.getItem('isAdult') === 'true') {
            document.getElementById('age-gate-overlay').style.display = 'none';
        }
    };
</script>

</body>
</html>