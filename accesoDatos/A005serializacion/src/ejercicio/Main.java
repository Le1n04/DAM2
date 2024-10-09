package ejercicio;

import java.io.FileOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main
{

	public static void main(String[] args)
    {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Nombre1", 101, 101));
        productos.add(new Producto("Nombre2", 102, 102));
        productos.add(new Producto("Nombre3", 103, 103));
        
        try (FileOutputStream fos = new FileOutputStream("productos.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
        	for (Producto producto : productos)
        		oos.writeObject(producto);
            System.out.println("Productos guardados en el fichero binario.");
        }
        catch (IOException ex)
        {
            System.out.println("Error al guardar los productos: " + ex);
        }
        
        try (FileInputStream fis = new FileInputStream("productos.dat");
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
            System.out.println("Error al leer los productos: " + ex.getMessage());
        }
    }
}
