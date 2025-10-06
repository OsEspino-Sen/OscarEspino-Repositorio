package hn.uth.tareapw2;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/numeros")
public class NumerosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Cuando se accede por GET, redirigimos al index
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String operacion = request.getParameter("operacion");
        String resultado = "";
        String tipoOperacion = "";
        String entrada = "";

        switch (operacion) {
            case "Encontrar Mayor":
                int num1 = Integer.parseInt(request.getParameter("num1"));
                int num2 = Integer.parseInt(request.getParameter("num2"));
                int num3 = Integer.parseInt(request.getParameter("num3"));
                resultado = String.valueOf(encontrarMayor(num1, num2, num3));
                entrada = num1 + ", " + num2 + ", " + num3;
                tipoOperacion = "Encontrar el Mayor de Tres Números";
                break;
            
            case "Encontrar Menor":
                num1 = Integer.parseInt(request.getParameter("num1"));
                num2 = Integer.parseInt(request.getParameter("num2"));
                num3 = Integer.parseInt(request.getParameter("num3"));
                resultado = String.valueOf(encontrarMenor(num1, num2, num3));
                entrada = num1 + ", " + num2 + ", " + num3;
                tipoOperacion = "Encontrar el Menor de Tres Números";
                break;
            
            case "Calcular Moda":
                String numerosStr = request.getParameter("numeros");
                resultado = String.valueOf(encontrarModa(numerosStr));
                entrada = numerosStr;
                tipoOperacion = "Encontrar la Moda de una Lista de Números";
                break;
        }
        
        generarRespuestaHTML(response, tipoOperacion, entrada, resultado, request);
    }

    private int encontrarMayor(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    private int encontrarMenor(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private int encontrarModa(String numerosStr) {
        String[] numerosArray = numerosStr.split(",");
        int[] numeros = Arrays.stream(numerosArray)
                             .map(String::trim)
                             .mapToInt(Integer::parseInt)
                             .toArray();
        
        Map<Integer, Integer> frecuencias = new HashMap<>();
        for (int num : numeros) {
            frecuencias.merge(num, 1, Integer::sum);
        }
        
        int moda = numeros[0];
        int maxFrecuencia = 0;
        
        for (Map.Entry<Integer, Integer> entry : frecuencias.entrySet()) {
            if (entry.getValue() > maxFrecuencia) {
                maxFrecuencia = entry.getValue();
                moda = entry.getKey();
            }
        }
        
        return moda;
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
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
            out.println("<p class=\"student-info\">Nombre: Luis Eduardo Sagastume Rodríguez</p>");
            out.println("<p class=\"student-info\">Cuenta: 201910040199</p>");
            out.println("<h3>Operación Realizada: " + tipoOperacion + "</h3>");
            out.println("<table>");
            out.println("<tr><th>Entrada</th><th>Respuesta</th></tr>");
            out.println("<tr><td>" + entrada + "</td><td>" + resultado + "</td></tr>");
            out.println("</table>");
            out.println("<a href='index.jsp' class='button'>Volver al Menú Principal</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
