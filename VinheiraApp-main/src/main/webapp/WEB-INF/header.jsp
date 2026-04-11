<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<style>
    * { margin: 0; padding: 0; box-sizing: border-box; }

    body {
        font-family: 'Inter', -apple-system, sans-serif;
        background-color: #0b0b0b;
        color: #ffffff;
        line-height: 1.6;
    }

    header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 25px 60px;
        background: rgba(0, 0, 0, 0.9);
        position: fixed;
        width: 100%;
        top: 0;
        z-index: 1000;
    }

    .logo {
        display: flex;
        align-items: center;
        text-decoration: none;
        font-size: 22px;
        font-weight: 400;
        letter-spacing: 1px;
        color: #d4af37; /* Dourado */
    }

    .logo span { color: #fff; font-weight: 400; }

    .logo-text {
        display: flex;
        gap: 8px;
    }

    /* O Círculo Externo */
    .logo-icon {
        width: 50px;
        height: 50px;
        background-color: #4a0000; /* Tom bordô/vinho */
        border: 2px solid #D4AF37; /* Borda dourada */
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 15px;
        position: relative;
    }

    .glass {
        width: 14px;
        height: 16px;
        border: 2px solid #D4AF37;
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
        position: relative;
        top: -5px;
    }

    .glass::before {
        content: '';
        position: absolute;
        width: 2px;
        height: 10px;
        background-color: #D4AF37;
        bottom: -12px;
        left: 50%;
        transform: translateX(-50%);
    }

    .glass::after {
        content: '';
        position: absolute;
        width: 12px;
        height: 2px;
        background-color: #D4AF37;
        bottom: -12px;
        left: 50%;
        transform: translateX(-50%);
    }

    nav a {
        color: #e0e0e0;
        text-decoration: none;
        margin: 0 18px;
        font-size: 13px;
        font-weight: 500;
        transition: color 0.3s;
    }

    nav a:hover { color: #d4af37; }

    .header-icons {
        display: flex;
        gap: 20px;
        font-size: 16px;
    }
</style>

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
        <a href="#">Minha adega</a>
        <a href="#">Somelier de IA</a>
    </nav>
    <div class="header-icons">
        <i class="fas fa-search"></i>
        <i class="fas fa-user"></i>
        <i class="fas fa-shopping-bag"></i>
    </div>
</header>