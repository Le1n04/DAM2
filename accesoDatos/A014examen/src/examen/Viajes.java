package examen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Viajes
{
	
	public static ArrayList<Viajes> VIAJES = new ArrayList<>();
	
	private int idviajes;
	private int totalplazas;
	private String destino;
	
	public Viajes(int idviajes, String destino, int totalplazas)
	{
		this.idviajes = idviajes;
		this.destino = destino;
		this.totalplazas = totalplazas;
	}
	
	public static ArrayList<Viajes> obtenerViajes(Connection con)
	{
		ArrayList<Viajes> viajes = new ArrayList<>();
		try
		{
			PreparedStatement stmtViajes = con.prepareStatement("SELECT * FROM viajes");
			ResultSet rs = stmtViajes.executeQuery();
			
			while (rs.next())
			{
				
				Viajes tmp = new Viajes(rs.getInt("idviajes"), rs.getString("destino"), rs.getInt("totalplazas"));
				VIAJES.add(tmp);
				viajes = VIAJES;
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error: " + ex.getMessage());
		}
		return viajes;
	}
	
	public static int plazasLibres(String destino, Connection con)
	{
		int plazas = -1;
		
		try
		{
			PreparedStatement stmtPlazas = con.prepareStatement("SELECT totalplazas FROM viajes WHERE destino = ?");
			stmtPlazas.setString(1, destino);
			ResultSet rs = stmtPlazas.executeQuery();
			
			if(rs.next())
				plazas = rs.getInt("totalplazas");
			if (plazas > 0)
			{
				PreparedStatement stmtReservas = con.prepareStatement("SELECT plazas FROM reservas WHERE numviaje = ? and ESTADO != 'C' "); // que no sean reservas canceladas
				PreparedStatement stmtNum = con.prepareStatement("SELECT idviajes FROM viajes WHERE destino = ?"); // numero del vuelo
				stmtNum.setString(1, destino);
				int numVuelo = 0;
				ResultSet rsNum = stmtNum.executeQuery();
				if (rsNum.next())
					numVuelo = rsNum.getInt("idviajes"); // saca la id del viaje
				stmtReservas.setInt(1, numVuelo);
				ResultSet rsReservas = stmtReservas.executeQuery();
				while (rsReservas.next())
					plazas = plazas - rsReservas.getInt("plazas");
			}
		}
		catch (Exception ex)
		{
			System.out.println("Error: " + ex.getMessage());
		}
		
		return plazas;
	}

	@Override
	public String toString()
	{
		return "Viajes [idviajes=" + idviajes + ", totalplazas=" + totalplazas + ", destino=" + destino + "]";
	}
}
