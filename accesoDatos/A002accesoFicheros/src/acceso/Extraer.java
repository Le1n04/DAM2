/**
 * 
 */
package acceso;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Extraer
{
	
	private static void extraerObjetos(ArrayList<Vehiculo> vehiculos, Scanner input)
	{
		System.out.println("¿Como se llama tu archivo?");
		String tmp = input.nextLine();
		try
		{
		    Scanner reader = new Scanner(new FileReader(tmp));
	        int contador = 0;
		    
		    while(reader.hasNextLine())
		    {
		        String linea = reader.nextLine();
		        String[] datos = linea.split(";");
		        System.out.println(linea);
		        
		        if (datos.length == 4)
		        {
		            vehiculos.add(new Vehiculo(datos[0], datos[1], datos[2], datos[3]));
					contador++;
		        }
		        else
		            System.out.println("Error: línea mal formateada.");
		    }
			System.out.println("Se han leido " + contador + " objetos.");

		    reader.close();
		}
		catch (FileNotFoundException ex)
		{
		    System.out.println("Error: no se ha encontrado un archivo con ese nombre.");
		}
		catch(Exception ex)
		{
			System.out.println("Error: " + ex.getMessage());
		}

	}
	
	private static String buscarMatricula(ArrayList<Vehiculo> vehiculos, Scanner input)
	{
		System.out.println("Introduce la matricula: ");
		String matri = input.nextLine();
		String datos = "No se ha encontrado.";
		
		for (int i = 0; i < vehiculos.size(); i--)
			if (vehiculos.get(i).getMatricula().equals(matri))
				datos = vehiculos.get(i).toString();
		
		return datos;
	}

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		int user = 0;
		
		while (user != 9)
		{
			System.out.println("¿Que desea hacer?");
			System.out.println("\n1. Recuperar vehiculos de archivo de texto.");
			System.out.println("2. Imprimir objetos vehiculo.");
			System.out.println("3. Buscar matricula.");
			System.out.println("9. Salir.");
			System.out.println("\nIntroduce tu eleccion: ");
			user = input.nextInt();
			input.nextLine();
			switch(user)
			{
			case 1:
				extraerObjetos(vehiculos, input);
				break;
			case 2:
				System.out.println(vehiculos.toString());
				break;
			case 3:
				buscarMatricula(vehiculos, input);
				break;
			case 9:
				System.out.println("Adios.");
				break;
			default:
				System.out.println("Seleccion erronea.");
			}
			
		}
	}

}
