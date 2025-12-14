<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exemplo de Scriptlet JSP</title>
</head>
<body>
    <h1>Bem-vindo!</h1>

    <%-- Scriptlet: bloco de código Java --%>
    <%
        String nome = request.getParameter("nome");
        if (nome == null || nome.isEmpty()) {
            nome = "Visitante";
        }
    %>

    <p>Olá, <%= nome %>! Seja bem-vindo à página JSP.</p>

    <form method="get">
        <label>Digite seu nome:</label>
        <input type="text" name="nome">
        <input type="submit" value="Enviar">
    </form>

    <hr>

    <%-- Exemplo de laço Java dentro da JSP --%>
    <ul>
        <%
            for (int i = 1; i <= 5; i++) {
        %>
            <li>Item número <%= i %></li>
        <%
            }
        %>
    </ul>
</body>
</html>


