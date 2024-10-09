package configuracion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Main
{
	
	private static void buscarPropiedad(Scanner input, Properties configuracion)
	{
		System.out.printf("Introduzca el nombre de la propiedad: ");
		String prop = input.nextLine();
		System.out.println("El resultado de su busqueda es: " + ((configuracion.getProperty(prop) != null) ? configuracion.getProperty(prop) : "No se ha encontrado la propiedad"));
	}
	
	private static void cambiarConfig(Scanner input, Properties configuracion)
	{
		System.out.println("Introduzca el nombre del archivo con su extension: ");
		try
		{
			configuracion.load(new FileInputStream(input.nextLine()));
		}
		catch (FileNotFoundException fnfe)
        {
            System.out.println("Archivo de configuración no encontrado: " + fnfe.getMessage());
        }
        catch (IOException ex)
        {
            System.out.println("Error al leer el archivo: " + ex.getMessage());
        }
	}
	
    public static void main(String[] args)
    {
        Properties configuracion = new Properties();
        Scanner input = new Scanner(System.in);
        
        try
        {
        	
        	cambiarConfig(input, configuracion);
        	
            int user;
            
            do
            {
            	System.out.println("1. Buscar propiedad");
            	System.out.println("2. Cambiar fichero de configuracion");
            	System.out.println("9. Salir");
            	System.out.printf("Introduzca su eleccion:");
            	user = input.nextInt();
            	input.nextLine();
            	switch (user)
            	{
            	case 1:
            		buscarPropiedad(input, configuracion);
            		break;
            	case 2:
            		cambiarConfig(input, configuracion);
            		break;
            	case 9:
            		System.out.println("Adios");
            		break;
            	default:
            		System.out.println("Elección erronea.");
            	}
            } while (user != 9);

        }
        catch (NumberFormatException nfe)
        {
            System.out.println("Error al convertir el puerto a entero: " + nfe.getMessage());
        }
        catch (InputMismatchException ime)
        {
        	System.out.println("Error en la entrada de datos: " + ime.getMessage());
        }
    }
}

/*String usuario = configuracion.getProperty("user");
String password = configuracion.getProperty("password");
String servidor = configuracion.getProperty("server");
int puerto = Integer.parseInt(configuracion.getProperty("port"));

System.out.println("Usuario: " + usuario);
System.out.println("Contraseña: " + password);
System.out.println("Servidor: " + servidor);
System.out.println("Puerto: " + puerto);*/