package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class Main
{

    public static void crearTablas()
   {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:wow.db"))
        {
            Statement stmt = con.createStatement();
            
            // Eliminar tablas si ya existen
            stmt.execute("DROP TABLE IF EXISTS articulos;");
            stmt.execute("DROP TABLE IF EXISTS pedidos;");
            stmt.execute("DROP TABLE IF EXISTS riders;");
            stmt.execute("DROP TABLE IF EXISTS envios;");
            
            // Crear tabla articulos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS articulos (" +
                               "codart INTEGER PRIMARY KEY, " +
                               "descripcion TEXT, " +
                               "existencias INTEGER CHECK (existencias >= 0), " +
                               "precio REAL)");

            // Crear tabla pedidos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS pedidos (" +
                               "codped INTEGER PRIMARY KEY, " +
                               "fecha DATE, " +
                               "codcli INTEGER, " +
                               "direccion TEXT, " +
                               "codart INTEGER, " +
                               "cantidad INTEGER, " +
                               "FOREIGN KEY (codart) REFERENCES articulos(codart))");

            // Crear tabla riders
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS riders (" +
                               "codigo INTEGER PRIMARY KEY, " +
                               "nombre TEXT, " +
                               "disponible CHAR(1) CHECK (disponible IN ('S', 'N')))");

            // Crear tabla envios
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS envios (" +
                               "codped INTEGER, " +
                               "codrider INTEGER, " +
                               "terminado CHAR(1) CHECK (terminado IN ('S', 'N')), " +
                               "PRIMARY KEY (codped, codrider), " +
                               "FOREIGN KEY (codped) REFERENCES pedidos(codped), " +
                               "FOREIGN KEY (codrider) REFERENCES riders(codigo))");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void insertarDatosPrueba()
    {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:wow.db"))
        {
            Statement stmt = con.createStatement();

            // insertar datos de prueba en tabla articulos
            stmt.executeUpdate("INSERT INTO articulos (codart, descripcion, existencias, precio) VALUES " +
                               "(1, 'Articulo 1', 50, 10.0), " +
                               "(2, 'Articulo 2', 30, 15.0)");

            // insertar datos de prueba en tabla riders
            stmt.executeUpdate("INSERT INTO riders (codigo, nombre, disponible) VALUES " +
                               "(1, 'Rider 1', 'S'), " +
                               "(4, 'Rider 4', 'S'), " +
                               "(5, 'Rider 5', 'S'), " +
                               "(2, 'Rider 2', 'S'), " +
                               "(3, 'Rider 3', 'N')"); // Este rider no está disponible
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     String sqlPedidos = "INSERT INTO pedidos (codped, fecha, codcli, direccion, codart, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement stmtPedidos = con.prepareStatement(sqlPedidos);
                        stmtPedidos.setInt(1, riderCodigo); // Codped igual al código del rider para asociar con él
                        stmtPedidos.setString(2, "2024-11-10");
                        stmtPedidos.setInt(3, 101);
                        stmtPedidos.setString(4, "Calle Falsa 123");
                        stmtPedidos.setInt(5, 1); // El artículo con codart = 1 debe existir en la tabla articulos
                        stmtPedidos.setInt(6, cantidad); // CAMBIAR CANTIDAD PARA QUE DE ERRORES DE RIDER EN VEZ DE EXISTENCIAS
                        stmtPedidos.executeUpdate();
     */
    
    private static String hacerPedido(Scanner input, Connection con)
    {
    	System.out.println("¿Cúal es su código de cliente y direccion (X;CALLE Y)?");
    	String[] infoCliente = input.nextLine().split(";");
    	System.out.println("¿Qué producto (código) quiere pedir y qué cantidad (PRODUCTO X; Y)?");
    	String[] infoProducto = input.nextLine().split(";");
    	if (infoCliente.length < 2 || infoProducto.length < 2)
    		return "Error en la entrada de datos.";
    	
    	try
    	{
    		insert(getCountPedido(con), LocalDate.now(), Integer.parseInt(infoCliente[0]), infoCliente[1], Integer.parseInt(infoProducto[0]), Integer.parseInt(infoProducto[1]), getRiderDisponible(con), con); // int codped, LocalDate fecha, int codcli, String direccion, int codart, int cantidad, int riderCodigo, Connection con
    	}
    	catch (Exception ex)
    	{
    		System.out.println("Error: " + ex.getMessage());
    	}
    	return "";
    }
    
    private static int getRiderDisponible(Connection con)
    {
    	int countRider = getCantidadRiders(con);
    	int codRider = 1;
    	
    	while (codRider <= countRider)
    	{
    		try
    		{
	    		String sqlRiderDisponible = "SELECT disponible FROM riders WHERE codigo = ?";
	            PreparedStatement stmtRider = con.prepareStatement(sqlRiderDisponible);
	            stmtRider.setInt(1, codRider); // Verificamos el estado de disponibilidad del rider
	            ResultSet rs = stmtRider.executeQuery();
	            
	            if (rs.next() && rs.getString("disponible").equals("S"))
                {
                    // El rider está disponible, continuar con el pedido y envío
                    System.out.println("Rider " + codRider + " está disponible, procesando transacción...");
                    return codRider;
                }
    		}
    		catch (Exception ex)
    		{
    			System.out.println(ex.getMessage());
    		}
    	}
    	
    	return 0;
    	
    }
    
    private static int getCountPedido(Connection con)
    {
    	try
    	{
	    	PreparedStatement sqlCount = con.prepareStatement("SELECT count(*) FROM pedidos");
	        ResultSet result = sqlCount.executeQuery();
	
	        if (result.next())
	            return result.getInt(1); // Obtiene el valor de la primera columna en el resultado
	        
	        result.close();
	        sqlCount.close();
    	}
    	catch (Exception ex)
    	{
    		System.out.println("Error: " + ex.getMessage());
    	}
    	
        return 0;
    }
    
    private static int getCantidadRiders(Connection con)
    {
    	try
    	{
	    	PreparedStatement sqlCount = con.prepareStatement("SELECT count(*) FROM riders");
	        ResultSet result = sqlCount.executeQuery();
	
	        if (result.next())
	            return result.getInt(1); // Obtiene el valor de la primera columna en el resultado
	        
	        result.close();
	        sqlCount.close();
    	}
    	catch (Exception ex)
    	{
    		System.out.println("Error: " + ex.getMessage());
    	}
    	
        return 0;

    }
    
    public static void insert(int codped, LocalDate fecha, int codcli, String direccion, int codart, int cantidad, int riderCodigo, Connection con)
    {
    	try
    	{
	    	String sqlPedidos = "INSERT INTO pedidos (codped, fecha, codcli, direccion, codart, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmtPedidos = con.prepareStatement(sqlPedidos);
	        stmtPedidos.setInt(1, riderCodigo); // Codped igual al código del rider para asociar con él
	        stmtPedidos.setString(2, "2024-11-10");
	        stmtPedidos.setInt(3, 101);
	        stmtPedidos.setString(4, "Calle Falsa 123");
	        stmtPedidos.setInt(5, 1); // El artículo con codart = 1 debe existir en la tabla articulos
	        stmtPedidos.setInt(6, cantidad); // CAMBIAR CANTIDAD PARA QUE DE ERRORES DE RIDER EN VEZ DE EXISTENCIAS
	        stmtPedidos.executeUpdate();
    	}
    	catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    }

    public static void main(String[] args)
    {
    	Scanner input = new Scanner(System.in);
    	
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:wow.db"))
        {
        	int select = 0;
        	crearTablas();
            insertarDatosPrueba();
        	con.createStatement().execute("PRAGMA foreign_keys = ON");
            con.setAutoCommit(false);
        	
        	do
        	{
        		System.out.println("¿Qué quieres hacer?\n1. Hacer un pedido.\n2. Finalizar un pedido.");
            	select = input.nextInt();
            	input.nextLine();
            	
            	switch (select)
            	{
            	case 1:
            		System.out.println(hacerPedido(input, con));
            		break;
            	case 2:
            		//finalizarPedido(input);
            		break;
            	case 9:
            		System.out.println("Adios.");
            		break;
            	default:
            		System.out.println("Elección erronea, vuelva a intentarlo.");
            	}
        	} 
        	while (select != 9);
            
            int riderCodigo = 1; // Empezamos con el rider 1
            int riderCount = getCantidadRiders(con);

            
            while (riderCodigo <= riderCount)
            {
                try
                {
                    // Verificar si el rider está disponible antes de realizar la transacción
                    
                    
                    System.out.println("¿Cuantos articulo quiere pedir?");
                    int cantidad = input.nextInt();
                    input.nextLine();
                    
                    
                        
                        // Alta en tabla pedidos
                        String sqlPedidos = "INSERT INTO pedidos (codped, fecha, codcli, direccion, codart, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement stmtPedidos = con.prepareStatement(sqlPedidos);
                        stmtPedidos.setInt(1, riderCodigo); // Codped igual al código del rider para asociar con él
                        stmtPedidos.setString(2, "2024-11-10");
                        stmtPedidos.setInt(3, 101);
                        stmtPedidos.setString(4, "Calle Falsa 123");
                        stmtPedidos.setInt(5, 1); // El artículo con codart = 1 debe existir en la tabla articulos
                        stmtPedidos.setInt(6, cantidad); // CAMBIAR CANTIDAD PARA QUE DE ERRORES DE RIDER EN VEZ DE EXISTENCIAS
                        stmtPedidos.executeUpdate();
                        
                        // Actualizar existencias en tabla artículos
                        String sqlArticulos = "UPDATE articulos SET existencias = existencias - ? WHERE codart = ?";
                        PreparedStatement stmtArticulos = con.prepareStatement(sqlArticulos);
                        stmtArticulos.setInt(1, cantidad); // CAMBIAR CANTIDAD PARA QUE DE ERRORES DE RIDER EN VEZ DE EXISTENCIAS
                        stmtArticulos.setInt(2, 1);
                        stmtArticulos.executeUpdate();
                        
                        // Insertar en tabla envios
                        String sqlEnvios = "INSERT INTO envios (codped, codrider, terminado) VALUES (?, ?, ?)";
                        PreparedStatement stmtEnvios = con.prepareStatement(sqlEnvios);
                        stmtEnvios.setInt(1, riderCodigo); // El codped se asocia con el rider actual
                        stmtEnvios.setInt(2, riderCodigo); // El codrider se asocia con el rider actual
                        stmtEnvios.setString(3, "N");
                        stmtEnvios.executeUpdate();
                        
                        con.commit();
                        System.out.println("Transacción realizada exitosamente para el rider " + riderCodigo + ".");
                    }
                    else
                    {
                        System.out.println("El rider " + riderCodigo + " no está disponible, vuelva a intentarlo.");
                    }

                    riderCodigo++;
                    
                }
                catch (SQLException e)
                {
                    con.rollback();
                    System.out.println("Transacción fallida para el rider " + riderCodigo + ", rollback realizado. Error: " + e.getMessage());
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
