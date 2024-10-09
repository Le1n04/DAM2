package Ejercicio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Ejercicio
{

	public static void main(String[] args)
	{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		productos.add(new Producto("Nombre1", 101, 101));
		productos.add(new Producto("Nombre2", 102, 102));
		productos.add(new Producto("Nombre3", 103, 103));
		
		try (FileOutputStream fos = new FileOutputStream("productos.dat");
				DataOutputStream dos = new DataOutputStream(fos))
		{
			for (Producto prod : productos)
			{
				dos.writeUTF(prod.getArticulo());
				dos.writeDouble(prod.getPrecio());
				dos.writeInt(prod.getExistencias());
			}
			System.out.println("Productos guardados en el fichero binario.");
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		try (FileInputStream fis = new FileInputStream("productos.dat");
	             DataInputStream dis = new DataInputStream(fis))
		{
	        while (dis.available() > 0)
	        {
	            String articulo = dis.readUTF();
	            double precio = dis.readDouble();
	            int existencias = dis.readInt();
	            System.out.println("Producto: " + articulo + ", Precio: " + precio + ", Existencias: " + existencias);
	        }
	    }
		catch (IOException ex)
		{
	        ex.printStackTrace();
	    }

	}

}
