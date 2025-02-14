package Examen;

import java.util.List;
import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		ArticuloDAO dao = new ArticuloDAO();
		Scanner input = new Scanner(System.in);
		int eleccion = 0;

		while (eleccion != 9)
		{
			System.out.println("1. Buscar producto por nombre.");
			System.out.println("2. Buscar producto por categoría.");
			System.out.println("3. Mostrar productos con stock inferior a un valor.");
			System.out.println("4. Actualizar los precios de una categoria mediante un %.");
			System.out.println("5. Añade el dato del proveedor a los productos.");
			System.out.println("6. Hacer todo lo anterior con valores predeterminados.");
			System.out.printf("\nIntroduzca su elección:");
			eleccion = input.nextInt();
			input.nextLine();

			switch (eleccion)
			{
			case 1:
				buscarProductoNombre(input, dao);
				break;
			case 2:
				buscarProductoCategoria(input, dao);
				break;
			case 3:
				mostrarProductosCantidadMenor(input, dao);
				break;
			case 4:
				actualizarPreciosCategoriaPerc(input, dao);
				break;
			case 5:
				anyadirDatoProveedor(input, dao);
				break;
			case 6:
				ejecutarEnunciado(dao);
				break;
			case 9:
				System.out.println("Adios.");
				break;
			default:
				System.out.println("Elección incorrecta, vuelva a intentarlo.");
				break;
			}
		}
	}

	public static void buscarProductoNombre(Scanner input, ArticuloDAO dao)
	{
		System.out.println("\nIntroduce el nombre del producto que quieres buscar: ");
		String nombre = input.nextLine();
		List<Articulo> encontrados = dao.buscarPorNombre(nombre);
		encontrados.forEach(a -> System.out.println("Encontrado: " + a.toDocument()));
	}

	public static void buscarProductoCategoria(Scanner input, ArticuloDAO dao)
	{
		System.out.println("\nIntroduce el nombre de la categoria que quieres buscar: ");
		String nombre = input.nextLine();

		List<Articulo> encontrados = dao.buscarPorCategoria(nombre);
		encontrados.forEach(a -> System.out.println("Encontrado: " + a.toDocument()));
	}

	public static void mostrarProductosCantidadMenor(Scanner input, ArticuloDAO dao)
	{
		System.out.println("Introduce la cantidad maxima de stock para buscar los menores: ");
		int num;
		try
		{
			num = input.nextInt();
			input.nextLine();
			
			List<Articulo> pocosStock = dao.buscarPorStockInferior(num);
			pocosStock.forEach(a -> System.out.println("Bajo stock: " + a.toDocument()));
		}
		catch(Exception ex) // por si no mete un numero en el INT
		{
			System.out.println("Error, vuelve a intentarlo.");
		}
	}

	public static void actualizarPreciosCategoriaPerc(Scanner input, ArticuloDAO dao)
	{
		System.out.println("Introduce la categoria y el porcentaje que quieres rebajar de esta manera: Electrónica;5");
		String[] str = input.nextLine().split(";"); // Para solo pedir una vez los datos
		
		try
		{
			dao.actualizarPreciosPorCategoria(str[0], Integer.parseInt(str[1])); // pasa el porcentaje a int
			System.out.printf("\nPrecios actualizados en %s.\n", str[0]);
		}
		catch (Exception ex) // por si no mete los dos datos-
		{
			System.out.println("Error, vuelva a intentarlo.");
		}
	}

	public static void anyadirDatoProveedor(Scanner input, ArticuloDAO dao)
	{
		System.out.println("Dime el articulo y el proveedor de esta manera: Monitor 4K;Proveedor A");
		String[] str = input.nextLine().split(";");
		
		try
		{
			dao.añadirProveedor(str[0], str[1]);
			System.out.println("Proveedores añadidos.");
		}
		catch (Exception ex) // por si no mete los dos datos-
		{
			System.out.println("Error, vuelva a intentarlo.");
		}
	}

	public static void ejecutarEnunciado(ArticuloDAO dao)
	{
		// Insertar artículo de prueba
		Articulo articulo = new Articulo("Monitor 4K", "Monitor de alta resolución", 399.99, "Electrónica", 15);
		dao.insertarArticulo(articulo);

		// Buscar por nombre
		List<Articulo> resultado = dao.buscarPorNombre("Monitor 4K");
		resultado.forEach(a -> System.out.println("Encontrado: " + a.toDocument()));

		// Buscar por categoría
		List<Articulo> electronicos = dao.buscarPorCategoria("Electrónica");
		electronicos.forEach(a -> System.out.println("Electrónico: " + a.toDocument()));

		// Buscar stock bajo
		List<Articulo> pocosStock = dao.buscarPorStockInferior(20);
		pocosStock.forEach(a -> System.out.println("Bajo stock: " + a.toDocument()));

		// Actualizar precios
		dao.actualizarPreciosPorCategoria("Electrónica", 5);
		System.out.println("Precios actualizados en Electrónica.");

		// Añadir proveedores
		dao.añadirProveedor("Monitor 4K", "Proveedor A");
		dao.añadirProveedor("Monitor 4K", "Proveedor B");
		System.out.println("Proveedores añadidos.");
	}
}
