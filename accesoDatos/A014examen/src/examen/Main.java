package examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Main
{
	
	private static void altaViaje(Connection con, Scanner input)
	{
		System.out.println("Introduce los datos del vuelo con el siguiente formato: (DESTINO;NUMEROPLAZAS)");
		System.out.println("MALAGA;60");
		String[] datos = input.nextLine().split(";");
		
		try
		{
			PreparedStatement stmtAlta = con.prepareStatement("INSERT INTO viajes (idviajes, destino, totalplazas) VALUES (0, ?, ?)");
			stmtAlta.setString(1, datos[0]);
			stmtAlta.setInt(2, Integer.parseInt(datos[1]));
			stmtAlta.executeUpdate();
			System.out.println("Alta realizada con exito.");
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		
	}

	private static void crearReserva(Connection con, Scanner input)
	{
		Integer[] datos = new Integer[3];
		
		// PETICION DE DATOS
		System.out.printf("Introduce tu codigo de cliente: ");
		datos[0] = input.nextInt();
		input.nextLine();
		System.out.printf("Introduce el numero del viaje: ");
		datos[1] = input.nextInt();
		input.nextLine();
		System.out.printf("Introduce cuantas plazas quieres: ");
		datos[2] = input.nextInt();
		input.nextLine();
		// INSERT INTO reservas (idreservas, idcliente, fecha, plazas, estado, numviaje) VALUES ('3', '1', '2024-11-25', '12', 'A', '1');

		// EJECUCION DE RESERVA
		try
		{
		PreparedStatement stmtReserva = con.prepareStatement("INSERT INTO reservas (idreservas, idcliente, fecha, plazas, estado, numviaje) VALUES (0, ?, ?, ?, ?, ?)");
		stmtReserva.setInt(1, datos[0]);
		stmtReserva.setString(2, LocalDate.now().toString());
		stmtReserva.setInt(3, datos[2]);
		// MIRAR CUANTAS PLAZAS QUEDAN
		PreparedStatement stmtDestino = con.prepareStatement("SELECT destino FROM viajes WHERE idviajes = ?");
		stmtDestino.setInt(1, datos[1]);
		ResultSet rs = stmtDestino.executeQuery();
		String destino;
		if (rs.next())
		{
			destino = rs.getString("destino");
			if (datos[2] < Viajes.plazasLibres(destino, con)) // reviso si hay plazas disponibles
				stmtReserva.setString(4, "A");
			else
				stmtReserva.setString(4, "E");
		}
		stmtReserva.setInt(5, datos[1]);
		stmtReserva.executeUpdate();
		System.out.println("Reserva realizada con exito.");
		}
		catch (Exception ex)
		{
			System.out.println("Error: has introducido un valor que no existe en la base de datos.");
		}
	}
	
	private static void cancelarReserva(Connection con, Scanner input)
	{
		Integer[] datos = new Integer[2];
		System.out.printf("Introduce tu numero de reserva: ");
		datos[0] = input.nextInt();
		input.nextLine();
		System.out.printf("Introduce tu codigo de cliente: ");
		datos[1] = input.nextInt();
		input.nextLine();
		// EJECUCION
		try
		{
			PreparedStatement stmtCancel = con.prepareStatement("SELECT * FROM reservas WHERE idreservas = ? and idcliente = ?");
			stmtCancel.setInt(1, datos[0]);
			stmtCancel.setInt(2, datos[1]);
			ResultSet rs = stmtCancel.executeQuery();
			if (rs.next()) // si hay alguna reserva con esos datos la ejecuta
			{
				// UPDATE `examenjdbc`.`reservas` SET `estado` = 'C' WHERE (`idreservas` = '1');
				PreparedStatement stmtEstado = con.prepareStatement("SELECT estado FROM reservas WHERE (idreservas = ?)");
				stmtEstado.setInt(1, datos[0]);
				ResultSet rsEst = stmtEstado.executeQuery();
				if (rsEst.next())
				{
					if (rsEst.getString("estado").charAt(0) != 'C') // miro si ya esta cancelada
					{
						PreparedStatement stmtUpdate = con.prepareStatement("UPDATE reservas SET estado = 'C' WHERE (idreservas = ?)");
						stmtUpdate.setInt(1, datos[0]);
						stmtUpdate.executeUpdate();
						System.out.println("Reserva cancelada con exito.");
					}
					else
						System.out.println("Esa reserva ya está cancelada.");
				}
			}
			else
			{
				System.out.println("No hay ninguna reserva con esos datos.");
			}
		}
		catch (Exception ex)
		{
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public static void main(String[] args)
	{
		String db = "jdbc:mysql://localhost:3306/examenjdbc";
		String user = "root";
		String password = "root";
		Scanner input = new Scanner(System.in);
		
		try (Connection con = DriverManager.getConnection(db, user, password))
		{
			int eleccion = 0;
			Viajes.obtenerViajes(con); // obtener los viajes y descomentar el bucle de abajo para verificar la importacion
			/*for (Viajes viaje : Viajes.VIAJES)
				System.out.println(viaje.toString());*/
			
			while (eleccion != 9)
			{
				System.out.println("1. Revisar plazas disponibles de un vuelo.");
				System.out.println("2. Alta de viajes.");
				System.out.println("3. Crear reserva.");
				System.out.println("4. Cancelar reserva.");
				System.out.println("9. Salir");
				System.out.printf("\nIntroduce tu eleccion: ");
				eleccion = input.nextInt();
				input.nextLine();
				
				switch (eleccion)
				{
				case 1:
					System.out.println("Introduce el nombre del destino:");
					String nombre = input.nextLine();
					System.out.println("Ese destino tiene " + Viajes.plazasLibres(nombre, con) + " plazas libres.\n");
					break;
				case 2:
					altaViaje(con, input);
					break;
				case 3:
					crearReserva(con, input);
					break;
				case 4:
					cancelarReserva(con, input);
					break;
				case 9:
					System.out.println("Adios...");
					break;
				default:
					System.out.println("Elección incorrecta.");
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

}
