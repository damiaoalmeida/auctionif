package br.edu.ifpb.webcontroller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Define o tipo de resposta (HTML)
        response.setContentType("text/html");

        // Escreve a resposta para o navegador
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Olá, mundo! Este é meu primeiro servlet.</h1>");
        out.println("</body></html>");
    }
}

