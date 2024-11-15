package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Ejercicio
{
    private static final String URL = "jdbc:sqlite:bd.db";

    private static String encriptarContrasena(String contrasena)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(contrasena.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash)
                hexString.append(String.format("%02x", b));
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void insertarUsuario(String nombrelogin, String contrasena, String nombrecompleto)
    {
        String sql = "INSERT INTO usuarios (nombrelogin, contrasena, nombrecompleto) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nombrelogin);
            pstmt.setString(2, encriptarContrasena(contrasena));
            pstmt.setString(3, nombrecompleto);
            pstmt.executeUpdate();
            
            System.out.println("Usuario insertado exitosamente.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static int contarEntradasUsuario(String nombrelogin)
    {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nombrelogin = ?";
        int numeroEntradas = 0;
        
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nombrelogin);
            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                    numeroEntradas = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return numeroEntradas;
    }

    public static void main(String[] args)
    {
        insertarUsuario("juan321", "pswd", "Juan Pozo Merchan");

        int entradas = contarEntradasUsuario("juan321");
        System.out.println("Número de entradas para 'juan321': " + entradas);
    }
}
