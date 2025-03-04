package cardinalidad;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateMusica {
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Alta de cantante");
            System.out.println("2. Consultar por género musical");
            System.out.println("3. Listado de todos los cantantes");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    altaCantante(scanner);
                    break;
                case 2:
                    consultaPorGenero(scanner);
                    break;
                case 3:
                    listarTodos();
                    break;
                case 4:
                    sessionFactory.close();
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void altaCantante(Scanner scanner) {
        System.out.print("Introduce el nombre del cantante: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce el ID del género musical: ");
        int generoId = scanner.nextInt();
        scanner.nextLine();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        GeneroMusical genero = session.get(GeneroMusical.class, generoId);

        if (genero != null) {
            Cantante cantante = new Cantante(nombre, genero);
            session.persist(cantante);
            tx.commit();
            System.out.println("Cantante registrado exitosamente.");
        } else {
            System.out.println("Género musical no encontrado.");
            tx.rollback();
        }
        session.close();
    }

    private static void consultaPorGenero(Scanner scanner) {
        System.out.print("Introduce el ID del género musical: ");
        int generoId = scanner.nextInt();
        scanner.nextLine();
        
        Session session = sessionFactory.openSession();
        GeneroMusical genero = session.get(GeneroMusical.class, generoId);
        
        if (genero != null) {
            List<Cantante> cantantes = session.createQuery("FROM Cantante WHERE genero.id = :generoId", Cantante.class)
                    .setParameter("generoId", generoId)
                    .getResultList();
            
            System.out.println("Cantantes del género " + genero.getNombre() + ":");
            cantantes.forEach(c -> System.out.println("- " + c.getNombre()));
        } else {
            System.out.println("Género musical no encontrado.");
        }
        session.close();
    }

    private static void listarTodos() {
        Session session = sessionFactory.openSession();
        List<Cantante> cantantes = session.createQuery("FROM Cantante", Cantante.class).getResultList();
        
        System.out.println("Lista de cantantes:");
        for (Cantante c : cantantes) {
            System.out.println("ID: " + c.getId() + ", Nombre: " + c.getNombre() + ", Género: " + c.getGenero().getNombre());
        }
        session.close();
    }
} 

