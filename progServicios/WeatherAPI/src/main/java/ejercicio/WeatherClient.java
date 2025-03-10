package ejercicio;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WeatherClient {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Definir el endpoint "/clima"
        server.createContext("/clima", new ClimaHandler());

        // Crear un thread pool para manejar múltiples solicitudes
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);

        // Iniciar el servidor
        server.start();
        System.out.println("Servidor corriendo en http://0.0.0.0:" + port);
    }

    // Manejador para la ruta "/clima"
    static class ClimaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                // Datos simulados de clima
                JSONObject json = new JSONObject();
                json.put("ciudad", "Málaga");
                json.put("temperatura", "15.4°C");
                json.put("condicion", "Partly Cloudy");
                json.put("humedad", "82%");
                json.put("viento", "15.8 km/h");

                // Convertir el JSON a string para enviarlo como respuesta
                String response = json.toString();

                // Configurar encabezados de la respuesta
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);

                // Escribir la respuesta al cliente
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                System.out.println("Solicitud GET recibida, respuesta enviada");
            } else {
                // Si el método HTTP no es GET, devolver error 405 (Method Not Allowed)
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}
