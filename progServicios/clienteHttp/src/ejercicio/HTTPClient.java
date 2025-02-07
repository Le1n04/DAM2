package ejercicio;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class HTTPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Introduce una URL (o escribe 'salir' para terminar): ");
            String urlString = scanner.nextLine();

            if (urlString.equalsIgnoreCase("salir")) {
                break;
            }

            try {
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                System.out.println("Código de respuesta HTTP: " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine).append("\n");
                }
                in.close();

                System.out.println("Contenido de la página:");
                System.out.println(content);

                System.out.println("Cabeceras de la respuesta:");
                for (Map.Entry<String, java.util.List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println(header.getKey() + ": " + header.getValue());
                }

                connection.disconnect();

            } catch (Exception e) {
                System.out.println("Error al obtener la página: " + e.getMessage());
            }
        }

        scanner.close();
    }
}

/*
Explicación del código:

1. **Entrada de URL:** El programa solicita una URL al usuario en un bucle.
2. **Conexión HTTP:** Usa `HttpURLConnection` para abrir una conexión y configurar el método como `GET`.
3. **Código de Respuesta:** Se obtiene con `getResponseCode()`, que puede ser 200 (OK), 404 (No encontrado), etc.
4. **Lectura del Contenido:** Se usa `BufferedReader` para leer el contenido HTML de la página.
5. **Cabeceras:** Se listan todas las cabeceras de la respuesta usando `getHeaderFields()`.
6. **Salir del Bucle:** El programa finaliza cuando el usuario escribe 'salir'.

Puedes adaptarlo para otros métodos HTTP como `POST` o `DELETE` cambiando `setRequestMethod("GET")` por el método deseado.
*/
