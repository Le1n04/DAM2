package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Cantante.class);
        sessionFactory = config.buildSessionFactory();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Alta de Cantantes");
            System.out.println("2. Consulta de Cantantes");
            System.out.println("3. Actualización de Cantantes");
            System.out.println("4. Borrado de Cantantes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    altaCantante(scanner);
                    break;
                case 2:
                    consultaCantantes();
                    break;
                case 3:
                    actualizarCantante(scanner);
                    break;
                case 4:
                    borrarCantante(scanner);
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 5);

        sessionFactory.close();
    }

    private static void altaCantante(Scanner scanner) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\nALTA DE CANTANTE");
        Cantante cantante = new Cantante();

        System.out.print("Ingrese el nombre del cantante: ");
        cantante.setNombre(scanner.nextLine());

        System.out.print("Ingrese el año: ");
        cantante.setAnio(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Ingrese el nombre del álbum: ");
        cantante.setAlbum(scanner.nextLine());

        session.save(cantante);
        transaction.commit();
        session.close();
        System.out.println("Cantante añadido con éxito.");
    }

    private static void consultaCantantes() {
        Session session = sessionFactory.openSession();

        System.out.println("\nLISTA DE CANTANTES");
        List<Cantante> cantantes = session.createQuery("from Cantante", Cantante.class).list();

        for (Cantante cantante : cantantes) {
            System.out.println("Código: " + cantante.getCodigo() +
                               ", Nombre: " + cantante.getNombre() +
                               ", Año: " + cantante.getAnio() +
                               ", Álbum: " + cantante.getAlbum());
        }

        session.close();
    }

    private static void actualizarCantante(Scanner scanner) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\nACTUALIZACIÓN DE CANTANTE");
        System.out.print("Ingrese el código del cantante a actualizar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Cantante cantante = session.get(Cantante.class, codigo);
        if (cantante == null) {
            System.out.println("Cantante no encontrado.");
        } else {
            System.out.print("Ingrese el nuevo nombre del cantante (actual: " + cantante.getNombre() + "): ");
            cantante.setNombre(scanner.nextLine());

            System.out.print("Ingrese el nuevo año (actual: " + cantante.getAnio() + "): ");
            cantante.setAnio(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Ingrese el nuevo álbum (actual: " + cantante.getAlbum() + "): ");
            cantante.setAlbum(scanner.nextLine());
            
            session.update(cantante);
            transaction.commit();
            System.out.println("Cantante actualizado con éxito.");
        }

        session.close();
    }

    private static void borrarCantante(Scanner scanner) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\n=== BORRADO DE CANTANTE ===");
        System.out.print("Ingrese el código del cantante a borrar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        Cantante cantante = session.get(Cantante.class, codigo);
        if (cantante == null) {
            System.out.println("Cantante no encontrado.");
        } else {
            session.delete(cantante);
            transaction.commit();
            System.out.println("Cantante eliminado con éxito.");
        }

        session.close();
    }
}
