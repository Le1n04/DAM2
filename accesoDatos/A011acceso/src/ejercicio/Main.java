package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static final String DB_URL = "jdbc:sqlite:db.db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static int obtenerTicketsDisponibles(Connection conn) throws SQLException {
        String query = "SELECT tickets_disponibles FROM tickets WHERE id = 1";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getInt("tickets_disponibles");
            throw new SQLException("No se encontraron tickets");
        }
    }

    private static boolean actualizarTickets(Connection conn, int ticketsVendidos) throws SQLException {
        int disponibles = obtenerTicketsDisponibles(conn);
        if (disponibles >= ticketsVendidos) {
            String updateQuery = "UPDATE tickets SET tickets_disponibles = tickets_disponibles - ? WHERE id = 1";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setInt(1, ticketsVendidos);
                stmt.executeUpdate();
                return true;
            }
        } else {
            System.out.println("No hay suficientes tickets para vender " + ticketsVendidos);
            return false;
        }
    }

    public static void main(String[] args) {
        // Número total de ventas
        int totalTicketsVendidos = 0;

        // Crear tres hilos que simulan la compra de tickets
        Runnable compraTicketTask = () -> {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                conn.setAutoCommit(false); // Iniciar transacción
                int ticketsAComprar = 3;

                boolean ventaExitosa = actualizarTickets(conn, ticketsAComprar);
                if (ventaExitosa) {
                    conn.commit(); // Confirmar transacción
                    System.out.println("Tickets vendidos: " + ticketsAComprar);
                } else
                    conn.rollback(); // Revertir transacción

            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        Thread h1 = new Thread(compraTicketTask);
        Thread h2 = new Thread(compraTicketTask);
        Thread h3 = new Thread(compraTicketTask);

        h1.start();
        h2.start();
        h3.start();

        try {
            h1.join();
            h2.join();
            h3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int ticketsRestantes = obtenerTicketsDisponibles(conn);
            int ticketsIniciales = 100;
            totalTicketsVendidos = ticketsIniciales - ticketsRestantes;

            System.out.println("Total de tickets vendidos: " + totalTicketsVendidos);
            System.out.println("Total de tickets disponibles: " + ticketsRestantes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
