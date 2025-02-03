package servicioSaludos;

import java.io.*;
import java.net.*;
import java.util.Random;

public class SaludosServidor {
	public static void main(String[] args) {
		int port = 5000;
		String[] greetings = { "¡Hola, %s! Bienvenido al servidor de saludos.", "¡Qué gusto verte, %s!",
				"¡Saludos cordiales, %s!", "¡Hey %s! Espero que tengas un gran día.",
				"¡%s, tu presencia ilumina el servidor!" };

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Servidor escuchando en el puerto " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Conexión aceptada de " + clientSocket.getInetAddress());

				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				out.println("¿Cuál es tu nombre?");

				String name = in.readLine();

				Random rand = new Random();
				String greeting = String.format(greetings[rand.nextInt(greetings.length)], name);
				out.println(greeting);

				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
