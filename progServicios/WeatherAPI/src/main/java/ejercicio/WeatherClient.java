package ejercicio;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Scanner;

public class WeatherClient
{
	// clave de la api para acceder a los datos del clima
	private static final String API_KEY = "c082ee6538b341639de180414251402"; // reemplaza con una clave valida
	// url base de la api
	private static final String BASE_URL = "http://api.weatherapi.com/v1";
	// cliente http para hacer las peticiones
	private static final OkHttpClient client = new OkHttpClient();
	// objeto scanner para leer entrada del usuario
	private static final Scanner input = new Scanner(System.in);

	public static void main(String[] args)
	{
		while (true)
		{
			// mostrar menu de opciones
			System.out.println("\nmenu:");
			System.out.println("1. consultar el clima actual de una ciudad");
			System.out.println("2. consultar el pronostico de los proximos dias");
			System.out.println("3. consultar el clima de una ciudad en un dia concreto");
			System.out.println("4. salir");
			System.out.print("elige una opcion: ");
			int opcion = input.nextInt();
			input.nextLine();

			// ejecutar la opcion seleccionada
			switch (opcion)
			{
			case 1:
				consultarClimaActual();
				break;
			case 2:
				consultarPronostico();
				break;
			case 3:
				consultarClimaFecha();
				break;
			case 4:
				System.out.println("saliendo...");
				return;
			default:
				System.out.println("opcion no valida.");
			}
		}
	}

	private static void consultarClimaActual()
	{
		// solicitar ciudad al usuario
		System.out.print("ingrese el nombre de la ciudad: ");
		String ciudad = input.nextLine();
		String url = BASE_URL + "/current.json?key=" + API_KEY + "&q=" + ciudad;

		try
		{
			// hacer la peticion a la api
			String response = getRequest(url);
			JSONObject jsonResponse = new JSONObject(response);
			JSONObject current = jsonResponse.getJSONObject("current");

			// mostrar datos del clima actual
			System.out.println("\nclima en " + ciudad + ":");
			System.out.println("temperatura: " + current.getDouble("temp_c") + "°c");
			System.out.println("condicion: " + current.getJSONObject("condition").getString("text"));
			System.out.println("humedad: " + current.getInt("humidity") + "%");
			System.out.println("velocidad del viento: " + current.getDouble("wind_kph") + " km/h");
		} catch (Exception e)
		{
			System.out.println("error al obtener el clima: " + e.getMessage());
		}
	}

	private static void consultarPronostico()
	{
		// solicitar ciudad y numero de dias al usuario
		System.out.print("ingrese el nombre de la ciudad: ");
		String ciudad = input.nextLine();
		System.out.print("ingrese el numero de dias de pronostico (max. 10): ");
		int dias = input.nextInt();
		input.nextLine();

		String url = BASE_URL + "/forecast.json?key=" + API_KEY + "&q=" + ciudad + "&days=" + dias;

		try
		{
			// hacer la peticion a la api
			String response = getRequest(url);
			JSONObject jsonResponse = new JSONObject(response);
			JSONObject forecast = jsonResponse.getJSONObject("forecast");

			// mostrar datos del pronostico
			System.out.println("\npronostico para " + ciudad + ":");
			for (int i = 0; i < dias; i++)
			{
				JSONObject day = forecast.getJSONArray("forecastday").getJSONObject(i);
				System.out.println("fecha: " + day.getString("date"));
				JSONObject dayInfo = day.getJSONObject("day");
				System.out.println("temperatura maxima: " + dayInfo.getDouble("maxtemp_c") + "°c");
				System.out.println("temperatura minima: " + dayInfo.getDouble("mintemp_c") + "°c");
				System.out.println("condicion: " + dayInfo.getJSONObject("condition").getString("text"));
				System.out.println("-------------------------------------------------");
			}
		} catch (Exception e)
		{
			System.out.println("error al obtener el pronostico: " + e.getMessage());
		}
	}

	private static void consultarClimaFecha()
	{
		// solicitar ciudad y fecha al usuario
		System.out.print("ingrese el nombre de la ciudad: ");
		String ciudad = input.nextLine();
		System.out.print("ingrese la fecha en formato yyyy-mm-dd: ");
		String fecha = input.nextLine();

		String url = BASE_URL + "/history.json?key=" + API_KEY + "&q=" + ciudad + "&dt=" + fecha;

		try
		{
			// hacer la peticion a la api
			String response = getRequest(url);
			JSONObject jsonResponse = new JSONObject(response);
			JSONObject forecast = jsonResponse.getJSONObject("forecast");

			// mostrar datos del clima historico
			System.out.println("\nhistorial del clima en " + ciudad + " para " + fecha + ":");
			JSONObject day = forecast.getJSONArray("forecastday").getJSONObject(0).getJSONObject("day");
			System.out.println("temperatura maxima: " + day.getDouble("maxtemp_c") + "°c");
			System.out.println("temperatura minima: " + day.getDouble("mintemp_c") + "°c");
			System.out.println("condicion: " + day.getJSONObject("condition").getString("text"));
		} catch (Exception e)
		{
			System.out.println("error al obtener el historial: " + e.getMessage());
		}
	}

	private static String getRequest(String url) throws IOException
	{
		// realizar la peticion http
		Request request = new Request.Builder().url(url).build();
		try (Response response = client.newCall(request).execute())
		{
			if (!response.isSuccessful())
				throw new IOException("error en la respuesta: " + response);
			return response.body().string();
		}
	}
}
