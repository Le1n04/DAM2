package ejercicio1;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Main
{
	
	private static ArrayList<Cliente> readCsv(String nombre)
	{
		ArrayList<Cliente> clientes = new ArrayList<>();
		
		try
		{
		    Scanner readercsv = new Scanner(new FileReader(nombre));
		    
		    while(readercsv.hasNextLine())
		    {
		        String linea = readercsv.nextLine();
		        String[] datos = linea.split(",");
		        
		        if (datos.length == 4)
		        {
		        	try
		        	{
			        	clientes.add(new Cliente(datos[0], Integer.parseInt(datos[1]), datos[2], datos[3]));
		        	}
		        	catch (Exception ex) // para quemar la linea del principio del csv
		        	{
		        	}
		        }
		        else
		            System.out.println("Error: línea mal formateada.");
		    }
		    readercsv.close();
		}
		catch(Exception ex)
		{
			System.out.println("Error: " + ex.getMessage());
		}
		
		return clientes;
	}
	
	private static ArrayList<String> readConf()
	{
    	ArrayList<String> conf = new ArrayList<>();
		
		try
	    {
			Scanner readerconf = new Scanner(new FileReader("programa.conf"));
			
			while (readerconf.hasNextLine())
			{
				String linea = readerconf.nextLine();
		        String[] datos = linea.split("=");
		        conf.add(datos[1]);
			}
			
			readerconf.close();
		}
	    catch (FileNotFoundException e)
	    {
			e.printStackTrace();
		}
		
		return conf;
	}
	
	
	public static void main(String[] args)
	{
    	ArrayList<String> conf = readConf();
	    ArrayList<Cliente> clientes = readCsv(conf.get(0));
	    ArrayList<Cliente> clientesChecked = new ArrayList<>();
		
	    for (int i = Integer.parseInt(conf.get(1)), j = Integer.parseInt(conf.get(2)); i <= j; i++)
	    {
	    	Cliente cliente = clientes.get(i-1);
	    	if (cliente.getAge() >= Integer.parseInt(conf.get(3)) && cliente.getAge() <= Integer.parseInt(conf.get(4)))
	    		clientesChecked.add(cliente);
	    }
	    
	    try (FileOutputStream fos = new FileOutputStream("clienteBIN.dat");
	             ObjectOutputStream oos = new ObjectOutputStream(fos))
	    {
	        	for (Cliente cliente: clientesChecked)
	        		oos.writeObject(cliente);
	    }
	    catch (IOException ex)
	    {
	        System.out.println("Error al guardar los clientes: " + ex);
	    }
	        
	    try (FileInputStream fis = new FileInputStream("clienteBIN.dat");
	         ObjectInputStream ois = new ObjectInputStream(fis))
	    {
	        for (;;)
	        	System.out.printf("%s\n", ois.readObject());
	    }
	    catch(EOFException ex1)
	    {
	    }
	    catch (IOException | ClassNotFoundException ex)
	    {
	        System.out.println("Error al leer los clientes: " + ex.getMessage());
	    }
	    
	}

}
