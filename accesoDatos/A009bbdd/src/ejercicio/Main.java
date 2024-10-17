package ejercicio;

import java.sql.*;
import java.util.Scanner;

public class Main
{
    private static final String DB_URL = "jdbc:sqlite:bbdd.db";

    public static Connection connect()
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Conexión correcta.");
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return conn;
    }

    public static void altaContacto(Connection conn, String tabla)
    {
        Scanner input = new Scanner(System.in);

        System.out.print("Introduce el codigo: ");
        String codigo = input.nextLine();

        System.out.print("Introduce el nombre: ");
        String nombre = input.nextLine();

        String sql = "INSERT INTO " + tabla + "(codigo, nombre) VALUES(?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, codigo);
            pstmt.setString(2, nombre);
            pstmt.executeUpdate();
            System.out.println("Registro agregado en la tabla " + tabla);
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static void consultaContacto(Connection conn, String tabla)
    {
        Scanner input = new Scanner(System.in);

        System.out.print("Introduce el nombre a buscar: ");
        String nombre = input.nextLine();

        String sql = "SELECT * FROM " + tabla + " WHERE nombre = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                System.out.println("Código: " + rs.getString("codigo"));
                System.out.println("Nombre: " + rs.getString("nombre"));
            } else
                System.out.println("No se ha encontrado ningun registro con el nombre " + nombre + " en la tabla " + tabla);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static String seleccionarTabla()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Seleccione la tabla:");
        System.out.println("1. Socio");
        System.out.println("2. Cliente");
        System.out.println("3. Alumno");
        System.out.print("Elige una opción: ");
        int opcion = input.nextInt();
        input.nextLine();

        switch (opcion)
        {
            case 1:
                return "socio";
            case 2:
                return "cliente";
            case 3:
                return "alumno";
            default:
                System.out.println("Opcion invalida. Se usara la tabla 'socio'...");
                return "socio";
        }
    }

    public static void main(String[] args)
    {
        Connection conn = connect();
        if (conn == null)
        {
            System.out.println("No se pudo establecer la conexión. Fin del programa.");
            return;
        }

        Scanner input = new Scanner(System.in);
        boolean continuar = true;

        while (continuar)
        {
            System.out.println("\n--- Menú de la Agenda ---");
            System.out.println("1. Alta de contacto");
            System.out.println("2. Consulta de contacto");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            int opcion = input.nextInt();
            input.nextLine();

            switch (opcion)
            {
                case 1:
                    String tablaAlta = seleccionarTabla();
                    altaContacto(conn, tablaAlta);
                    break;
                case 2:
                    String tablaConsulta = seleccionarTabla();
                    consultaContacto(conn, tablaConsulta);
                    break;
                case 3:
                    continuar = false;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida, por favor elige entre 1, 2 o 3.");
            }
        }

        try
        {
            conn.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
