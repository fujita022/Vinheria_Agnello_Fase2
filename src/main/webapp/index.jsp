<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vinheria Agnello - Home</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: 'Inter', -apple-system, sans-serif;
            background-color: #0b0b0b;
            color: #ffffff;
            line-height: 1.6;
        }

        .hero {
            height: calc(100vh - 60px);
            background: linear-gradient(rgba(0, 0, 0, 0.75), rgba(0, 0, 0, 0.75)),
            url('<%= request.getContextPath() %>/images/vinho.png');
            background-size: cover;
            background-position: center;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            padding: 0 20px;
            margin-top: 60px;
        }

        .hero span.tagline {
            color: #d4af37;
            text-transform: uppercase;
            letter-spacing: 4px;
            font-size: 12px;
            margin-bottom: 20px;
            font-weight: 600;
        }

        .hero h1 {
            font-size: 56px;
            font-weight: 700;
            margin-bottom: 20px;
            line-height: 1.1;
        }

        .hero h1 span { color: #d4af37; }

        .hero p.description {
            max-width: 600px;
            color: #ccc;
            font-size: 16px;
            margin-bottom: 35px;
        }

        .btn-container { display: flex; gap: 15px; }

        .btn {
            padding: 14px 32px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.3s;
        }

        .btn-primary {
            background-color: #800000; /* Tom Vinho */
            color: white;
            border: 1px solid #800000;
        }

        .btn-primary:hover { background-color: #a00000; }

        .btn-outline {
            border: 1px solid #ffffff;
            color: white;
        }

        .btn-outline:hover { background: white; color: black; }

        /* Grade de Vinhos (Dinamizada pelo Java) */
        .wine-section { padding: 80px 60px; }
        .wine-section h2 { margin-bottom: 40px; text-align: center; font-size: 28px; }

        .wine-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 30px;
        }

        .wine-card {
            background: #151515;
            border: 1px solid #252525;
            padding: 25px;
            border-radius: 8px;
            transition: transform 0.3s;
        }

        .wine-card:hover { transform: translateY(-5px); border-color: #d4af37; }

        .wine-card h3 { color: #d4af37; margin-bottom: 10px; font-size: 20px; }
        .wine-card p { color: #aaa; font-size: 14px; margin-bottom: 15px; }
        .wine-card .price { font-weight: 700; color: #fff; font-size: 18px; }

    </style>
</head>
<body>

<jsp:include page="/WEB-INF/header.jsp" />

<section class="hero">
    <span class="tagline">Converse com o Sommelier AI</span>
    <h1>A Arte do Vinho,<br><span>Curada para Você</span></h1>
    <p class="description">Descubra vinhos selecionados por nossa inteligência artificial baseada nos seus gostos mundiais, safra e harmonização única.</p>

    <div class="btn-container">
        <a href="#" class="btn btn-primary">Iniciar Consultoria</a>
        <a href="wine?action=list" class="btn btn-outline">Explorar adega</a>
    </div>
</section>

<jsp:include page="/WEB-INF/footer.jsp" />

</body>
</html>