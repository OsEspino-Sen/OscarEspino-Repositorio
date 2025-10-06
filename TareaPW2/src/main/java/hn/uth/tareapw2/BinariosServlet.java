package hn.uth.tareapw2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/binarios")
public class BinariosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Cuando se accede por GET, redirigimos al index
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String operacion = request.getParameter("operacion");
        String numero = request.getParameter("numero");
        String resultado = "";
        String tipoOperacion = "";
        
        if ("Binario a Decimal".equals(operacion)) {
            resultado = String.valueOf(binarioADecimal(numero));
            tipoOperacion = "Conversión de Binario a Decimal";
        } else if ("Decimal a Binario".equals(operacion)) {
            resultado = decimalABinario(Integer.parseInt(numero));
            tipoOperacion = "Conversión de Decimal a Binario";
        }
        
        generarRespuestaHTML(response, tipoOperacion, numero, resultado, request);
    }

    private int binarioADecimal(String binario) {
        int decimal = 0;
        int potencia = 0;
        
        for (int i = binario.length() - 1; i >= 0; i--) {
            if (binario.charAt(i) == '1') {
                decimal += (int)Math.pow(2, potencia);
            }
            potencia++;
        }
        return decimal;
    }

    private String decimalABinario(int decimal) {
        if (decimal == 0) return "0";
        
        StringBuilder binario = new StringBuilder();
        while (decimal > 0) {
            binario.insert(0, decimal % 2);
            decimal = decimal / 2;
        }
        return binario.toString();
    }

    private void generarRespuestaHTML(HttpServletResponse response, String tipoOperacion, 
                                    String entrada, String resultado, HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Resultado - " + tipoOperacion + "</title>");
            out.println("<link rel=\"stylesheet\" href=\"" + request.getContextPath() + "/css/results.css\">");
            out.println("<link rel=\"stylesheet\" href=\"" + request.getContextPath() + "/css/results.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
            out.println("<p class=\"student-info\">Nombre: Oscar Noe Espino Aguirre</p>");
            out.println("<p class=\"student-info\">Cuenta: 202310110465</p>");
            out.println("<h3>Operación Realizada: " + tipoOperacion + "</h3>");
            out.println("<table>");
            out.println("<tr><th>Entrada</th><th>Respuesta</th></tr>");
            out.println("<tr><td>" + entrada + "</td><td>" + resultado + "</td></tr>");
            out.println("</table>");
            out.println("<br/><a href='index.jsp' class='button'>Volver al Menú Principal</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
