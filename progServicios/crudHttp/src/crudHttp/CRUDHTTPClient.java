package crudHttp;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class CRUDHTTPClient {
    private static final String BASE_URL = "http://damplaya.hopto.org:3000/usuarios";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOpciones CRUD:");
            System.out.println("1. Consultar todos los usuarios (GET)");
            System.out.println("2. Consultar un usuario (GET)");
            System.out.println("3. Alta de usuario (POST)");
            System.out.println("4. Actualizar teléfono de usuario (PUT)");
            System.out.println("5. Borrar usuario (DELETE)");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            try {
                switch (opcion) {
                    case 1:
                        sendRequest(BASE_URL, "GET", null);
                        break;
                    case 2:
                        System.out.print("Introduce el ID del usuario: ");
                        String id = scanner.nextLine();
                        sendRequest(BASE_URL + "/buscar?id=" + id, "GET", null);
                        break;
                    case 3:
                        System.out.print("Introduce el nombre del usuario: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Introduce el teléfono del usuario: ");
                        String telefono = scanner.nextLine();
                        String jsonInput = "{\"nombre\": \"" + nombre + "\", \"telefono\": \"" + telefono + "\"}";
                        sendRequest(BASE_URL, "POST", jsonInput);
                        break;
                    case 4:
                        System.out.print("Introduce el ID del usuario: ");
                        String userId = scanner.nextLine();
                        System.out.print("Nuevo teléfono: ");
                        String newPhone = scanner.nextLine();
                        String updateInput = "{\"telefono\": \"" + newPhone + "\"}";
                        sendRequest(BASE_URL + "/" + userId + "/telefono", "PUT", updateInput);
                        break;
                    case 5:
                        System.out.print("Introduce el ID del usuario a borrar: ");
                        String deleteId = scanner.nextLine();
                        sendRequest(BASE_URL + "/" + deleteId, "DELETE", null);
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error al realizar la operación: " + e.getMessage());

                if (e instanceof java.io.IOException) {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) new URL(BASE_URL).openConnection();
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        String line;
                        System.out.println("Detalles del error:");
                        while ((line = errorReader.readLine()) != null) {
                            System.out.println(line);
                        }
                        errorReader.close();
                    } catch (Exception ex) {
                        System.out.println("No se pudo obtener más información del error.");
                    }
                }
            }
        }
    }

    private static void sendRequest(String urlString, String method, String body) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");

        if (body != null && (method.equals("POST") || method.equals("PUT"))) {
            connection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(body);
                wr.flush();
            }
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Código de respuesta HTTP: " + responseCode);

        BufferedReader in;
        if (responseCode >= 200 && responseCode < 300) {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }
        in.close();

        System.out.println("Respuesta del servidor:");
        System.out.println(content);

        System.out.println("Cabeceras de la respuesta:");
        for (Map.Entry<String, java.util.List<String>> header : connection.getHeaderFields().entrySet()) {
            System.out.println(header.getKey() + ": " + header.getValue());
        }

        connection.disconnect();
    }
}

/*
Explicación del código:

1. **Opciones CRUD:** El programa ofrece un menú para seleccionar la operación.
2. **Método `sendRequest`:** Maneja la solicitud HTTP con diferentes métodos (`GET`, `POST`, `PUT`, `DELETE`).
3. **Entradas del Usuario:** Se solicitan datos según la operación, como IDs, nombres, o teléfonos.
4. **Cabeceras HTTP:** Se muestra la respuesta del servidor y sus cabeceras.
5. **Manejo de Errores:** Se añaden detalles adicionales en caso de errores HTTP para depuración.
6. **Salida:** El programa continúa en bucle hasta que el usuario elige salir.
*/
