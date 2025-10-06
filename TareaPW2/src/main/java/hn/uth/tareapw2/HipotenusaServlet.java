package hn.uth.tareapw2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hipotenusa")
public class HipotenusaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Cuando se accede por GET, redirigimos al index
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double catetoA = Double.parseDouble(request.getParameter("catetoA"));
        double catetoB = Double.parseDouble(request.getParameter("catetoB"));
        
        double hipotenusa = calcularHipotenusa(catetoA, catetoB);
        String entrada = "Cateto a = " + catetoA + ", Cateto b = " + catetoB;
        
        generarRespuestaHTML(response, entrada, String.format("%.2f", hipotenusa), request);
    }

    private double calcularHipotenusa(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    private void generarRespuestaHTML(HttpServletResponse response, String entrada, 
                                    String resultado, HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Resultado - Cálculo de Hipotenusa</title>");
            out.println("<link rel=\"stylesheet\" href=\"" + request.getContextPath() + "/css/results.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
            out.println("<p class=\"student-info\">Nombre: Oscar Noe Espino Aguirre</p>");
            out.println("<p class=\"student-info\">Cuenta: 202310110465</p>");
            out.println("<h3>Operación Realizada: Cálculo de Hipotenusa</h3>");
            out.println("<table>");
            out.println("<tr><th>Entrada</th><th>Respuesta</th></tr>");
            out.println("<tr><td>" + entrada + "</td><td>" + resultado + "</td></tr>");
            out.println("</table>");
            out.println("<br/><a href='index.jsp'>Volver al Menú Principal</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
