package com.ejemplo.hibernate.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.ejemplo.hibernate.model.Cantante;
import com.ejemplo.hibernate.model.GeneroMusical;

import java.util.List;
import java.util.Scanner;

public class Main
{
	private static SessionFactory sessionFactory;

	public static void main(String[] args)
	{
		sessionFactory = new Configuration().configure().buildSessionFactory();

		Scanner scanner = new Scanner(System.in);
		int opcion;

		do
		{
			System.out.println("\n--- MENÚ ---");
			System.out.println("1. Alta de Cantante");
			System.out.println("2. Consulta por Género Musical");
			System.out.println("3. Listado de Todos los Cantantes");
			System.out.println("0. Salir");
			System.out.print("Selecciona una opción: ");
			opcion = scanner.nextInt();

			switch (opcion)
			{
			case 1:
				altaCantante();
				break;
			case 2:
				consultaPorGenero();
				break;
			case 3:
				listarTodosLosCantantes();
				break;
			case 0:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción no válida");
			}
		}
		while (opcion != 0);

		sessionFactory.close();
		scanner.close();
	}

	private static void altaCantante()
	{
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		System.out.print("Nombre del cantante: ");
		String nombre = scanner.nextLine();

		System.out.print("ID del género musical: ");
		int generoId = scanner.nextInt();

		GeneroMusical genero = session.get(GeneroMusical.class, generoId);
		if (genero == null)
		{
			System.out.println("Género musical no encontrado.");
			tx.rollback();
			session.close();
			return;
		}

		Cantante cantante = new Cantante();
		cantante.setNombre(nombre);
		cantante.setGenero(genero);

		session.persist(cantante);
		tx.commit();
		session.close();

		System.out.println("Cantante añadido con éxito.");
	}

	private static void consultaPorGenero()
	{
		Scanner scanner = new Scanner(System.in);
		Session session = sessionFactory.openSession();

		System.out.print("ID del género musical: ");
		int generoId = scanner.nextInt();

		GeneroMusical genero = session.get(GeneroMusical.class, generoId);
		if (genero == null)
		{
			System.out.println("Género musical no encontrado.");
			session.close();
			return;
		}

		System.out.println("Cantantes del género " + genero.getNombre() + ":");
		for (Cantante cantante : genero.getCantantes())
		{
			System.out.println("- " + cantante.getNombre());
		}

		session.close();
	}

	private static void listarTodosLosCantantes()
	{
		Session session = sessionFactory.openSession();

		List<Cantante> cantantes = session.createQuery("FROM Cantante", Cantante.class).list();
		System.out.println("Listado de todos los cantantes:");
		for (Cantante cantante : cantantes)
		{
			System.out.println("ID: " + cantante.getId() + ", Nombre: " + cantante.getNombre() + ", Género: "
					+ cantante.getGenero().getNombre());
		}

		session.close();
	}
}
