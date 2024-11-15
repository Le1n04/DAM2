package ejercicio;

import java.sql.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main
{

	public static Connection conectarBaseDatos()
	{
	    Connection conn = null;
	    try {
	        String url = "jdbc:sqlite:db.db";
	        String user = "root";
	        String password = "root";

	        conn = DriverManager.getConnection(url, user, password);
	        System.out.println("Conexión a la base de datos establecida.");
	    }
	    catch (SQLException e)
	    {
	        System.out.println("Error al conectar a la base de datos.");
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	// Método para encriptar la contraseña usando MD5
    public static String encriptarMD5(String password)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest)
                sb.append(String.format("%02x", b));  // Convertir byte a hexadecimal
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }


    public static void altaUsuario(Connection conn)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Ingrese nombre de usuario: ");
        String nombrelogin = input.nextLine();

        try
        {
            String query = "SELECT * FROM usuarios WHERE nombrelogin = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombrelogin);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                System.out.println("El nombre de usuario ya está en uso.");
                return;
            }

            System.out.print("Ingrese la contraseña: ");
            String contrasena = input.nextLine();
            
            String contrasenaEncriptada = encriptarMD5(contrasena);

            System.out.print("Ingrese el nombre completo: ");
            String nombrecompleto = input.nextLine();

            query = "INSERT INTO usuarios (nombrelogin, contrasena, nombrecompleto) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, nombrelogin);
            ps.setString(2, contrasenaEncriptada);  // Guardar la contraseña encriptada
            ps.setString(3, nombrecompleto);
            ps.executeUpdate();

            System.out.println("Usuario registrado con éxito.");

        }
        catch (SQLException e)
        {
            System.out.println("Error al registrar usuario.");
            e.printStackTrace();
        }
    }


    public static void entradaUsuario(Connection conn)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Ingrese nombre de usuario: ");
        String nombrelogin = input.nextLine();

        System.out.print("Ingrese la contraseña: ");
        String contrasena = input.nextLine();

        String contrasenaEncriptada = encriptarMD5(contrasena);

        try
        {
            String query = "SELECT * FROM usuarios WHERE nombrelogin = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombrelogin);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String contrasenaBD = rs.getString("contrasena");
                int coduser = rs.getInt("coduser");

                if (contrasenaEncriptada.equals(contrasenaBD))
                {
                    query = "INSERT INTO entradas (codusuario) VALUES (?)";
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, coduser);
                    ps.executeUpdate();

                    System.out.println("Entrada registrada con éxito.");
                }
                else
                {
                    System.out.println("Contraseña incorrecta.");
                    registrarError(nombrelogin);
                }
            }
            else
                System.out.println("El usuario no existe.");
        }
        catch (SQLException e)
        {
            System.out.println("Error durante el inicio de sesión.");
            e.printStackTrace();
        }
    }


    public static void registrarError(String nombrelogin)
    {
        try (FileWriter fw = new FileWriter("errores.txt", true))
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String now = LocalDateTime.now().format(dtf);

            fw.write("Error: " + now + " - Usuario: " + nombrelogin + "\n");
            System.out.println("Error registrado en errores.txt.");
        }
        catch (IOException e)
        {
            System.out.println("Error al registrar en archivo.");
            e.printStackTrace();
        }
    }

    public static void listarEntradas(Connection conn)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Ingrese nombre de usuario: ");
        String nombrelogin = input.nextLine();

        try
        {
            String query = "SELECT coduser FROM usuarios WHERE nombrelogin = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombrelogin);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                int coduser = rs.getInt("coduser");

                query = "SELECT * FROM entradas WHERE codusuario = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, coduser);
                rs = ps.executeQuery();

                System.out.println("Entradas para el usuario " + nombrelogin + ":");
                while (rs.next())
                    System.out.println("Entrada #" + rs.getInt("numentrada") + " - Hora: " + rs.getTimestamp("hora_ultima_entrada"));
            }
            else
                System.out.println("El usuario no existe.");
        }
        catch (SQLException e)
        {
            System.out.println("Error al listar entradas.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Connection conn = conectarBaseDatos();
        if (conn == null)
            return;

        Scanner input = new Scanner(System.in);
        int opcion;

        do
        {
            System.out.println("\nMenú:");
            System.out.println("1. Alta de usuario");
            System.out.println("2. Entrada de usuario");
            System.out.println("3. Listado de entradas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            while (!input.hasNextInt())
            {
                System.out.println("Por favor, ingrese un número válido.");
                input.next();
            }
            
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion)
            {
                case 1:
                    altaUsuario(conn);
                    break;
                case 2:
                    entradaUsuario(conn);
                    break;
                case 3:
                    listarEntradas(conn);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);


        try
        {
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
