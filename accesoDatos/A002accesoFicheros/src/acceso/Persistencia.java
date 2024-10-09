/**
 * 
 */
package acceso;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */
public class Persistencia
{
	
	private static void anyadirVehiculo(Scanner input, ArrayList<Vehiculo> vehiculos, File file, FileWriter writer)
	{
		String tmp[] = new String[4];
		System.out.printf("Introduzca la matricula: ");
		tmp[0] = input.nextLine();
		System.out.printf("\nIntroduzca la marca: ");
		tmp[1] = input.nextLine();
		System.out.printf("\nIntroduzca el modelo: ");
		tmp[2] = input.nextLine();
		System.out.printf("\nIntroduzca el tipo: ");
		tmp[3] = input.nextLine();
		
		try
		{
			vehiculos.add(new Vehiculo(tmp[0], tmp[1], tmp[2], tmp[3]));
			writer.write(tmp[0] + ";" + tmp[1] + ";" + tmp[2] + ";" + tmp[3] + '\n');
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	private static void procesarEleccion(int num, Scanner input, ArrayList<Vehiculo> vehiculos, File file, FileWriter writer)
	{
		switch (num)
		{
		case 1:
			anyadirVehiculo(input, vehiculos, file, writer);
			break;
		case 9:
			System.out.println("Adios.");
			break;
		default:
			System.out.println("Error, eleccion incorrecta.");
		}
	}
	
	private static void procesarMenu(Scanner input, ArrayList<Vehiculo> vehiculos, File file, FileWriter writer)
	{
		int eleccion = 0;
		
		while (eleccion != 9)
		{
			System.out.println("Que desea hacer?");
			System.out.println("1. Anyadir un nuevo vehiculo");
			System.out.println("9. Salir");
			System.out.println("\nIntroduzca su eleccion:");
			
			eleccion = input.nextInt();
			input.nextLine();
			procesarEleccion(eleccion, input, vehiculos, file, writer);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ArrayList<Vehiculo> vehiculos = new ArrayList<>();
		Scanner input = new Scanner(System.in);
		File archivo = null;
		FileWriter writer = null;
		
		try
		{
			archivo = new File("src/Persistencia/texto.txt");
			writer = new FileWriter(archivo, true);
			procesarMenu(input, vehiculos, archivo, writer);
			writer.close();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

}
