package hibernate_java;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class Main
{
	private static SessionFactory factory;

	public static void main(String[] args)
	{
		factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cantante.class)
				.buildSessionFactory();

		Scanner scanner = new Scanner(System.in);
		int opcion;

		do
		{
			mostrarMenu();
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer

			switch (opcion)
			{
			case 1:
				agregarCantante(scanner);
				break;
			case 2:
				listarCantantes();
				break;
			case 3:
				actualizarCantante(scanner);
				break;
			case 4:
				eliminarCantante(scanner);
				break;
			case 5:
				System.out.println("Saliendo del programa...");
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
		while (opcion != 5);

		factory.close();
		scanner.close();
	}

	private static void mostrarMenu()
	{
		System.out.println("\n--- Menú de Gestión de Cantantes ---");
		System.out.println("1. Agregar cantante");
		System.out.println("2. Listar cantantes");
		System.out.println("3. Actualizar cantante");
		System.out.println("4. Eliminar cantante");
		System.out.println("5. Salir");
	}

	private static void agregarCantante(Scanner scanner)
	{
		try (Session session = factory.openSession())
		{
			Transaction transaction = session.beginTransaction();

			System.out.println("Ingrese los datos del nuevo cantante:");
			System.out.print("Nombre: ");
			String nombre = scanner.nextLine();
			System.out.print("Año: ");
			int anio = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer
			System.out.print("Álbum: ");
			String album = scanner.nextLine();

			Cantante cantante = new Cantante();
			cantante.setNombreCantante(nombre);
			cantante.setAnio(anio);
			cantante.setAlbum(album);

			session.persist(cantante);
			transaction.commit();

			System.out.println("Cantante agregado exitosamente: " + cantante);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void listarCantantes()
	{
		try (Session session = factory.openSession())
		{
			List<Cantante> cantantes = session.createQuery("FROM Cantante", Cantante.class).list();
			if (cantantes.isEmpty())
				System.out.println("No hay cantantes registrados.");
			else
			{
				System.out.println("\n--- Lista de Cantantes ---");
				cantantes.forEach(System.out::println);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void actualizarCantante(Scanner scanner)
	{
		try (Session session = factory.openSession())
		{
			Transaction transaction = session.beginTransaction();

			System.out.print("Ingrese el código del cantante a actualizar: ");
			int codigo = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer

			Cantante cantante = session.get(Cantante.class, codigo);
			if (cantante == null)
			{
				System.out.println("Cantante no encontrado con código: " + codigo);
				return;
			}

			System.out.println("Cantante actual: " + cantante);
			System.out.print("Nuevo nombre (deje en blanco para no cambiar): ");
			String nuevoNombre = scanner.nextLine();
			if (!nuevoNombre.isBlank())
				cantante.setNombreCantante(nuevoNombre);

			System.out.print("Nuevo año (0 para no cambiar): ");
			int nuevoAnio = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer
			if (nuevoAnio != 0)
				cantante.setAnio(nuevoAnio);

			System.out.print("Nuevo álbum (deje en blanco para no cambiar): ");
			String nuevoAlbum = scanner.nextLine();
			if (!nuevoAlbum.isBlank())
				cantante.setAlbum(nuevoAlbum);

			session.merge(cantante);
			transaction.commit();

			System.out.println("Cantante actualizado exitosamente: " + cantante);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void eliminarCantante(Scanner scanner)
	{
		try (Session session = factory.openSession())
		{
			Transaction transaction = session.beginTransaction();

			System.out.print("Ingrese el código del cantante a eliminar: ");
			int codigo = scanner.nextInt();
			scanner.nextLine(); // Limpiar el buffer

			Cantante cantante = session.get(Cantante.class, codigo);
			if (cantante == null)
			{
				System.out.println("Cantante no encontrado con código: " + codigo);
				return;
			}

			session.remove(cantante);
			transaction.commit();

			System.out.println("Cantante eliminado exitosamente.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
