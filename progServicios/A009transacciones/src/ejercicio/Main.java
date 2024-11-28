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

    private static void crearTablas()
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
                               "fecha_entrega DATE, " +
                               "codcli INTEGER, " +
                               "direccion TEXT, " +
                               "codart INTEGER, " +
                               "cantidad INTEGER, " +
                               "codrider INTEGER, " +
                               "completado CHAR(1) CHECK (completado IN ('S', 'N')), " +
                               "FOREIGN KEY (codart) REFERENCES articulos(codart), " +
                               "FOREIGN KEY (codrider) REFERENCES riders(codigo))");

            // Crear tabla riders
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS riders (" +
                               "codigo INTEGER PRIMARY KEY, " +
                               "nombre TEXT, " +
                               "disponible CHAR(1) CHECK (disponible IN ('S', 'N')))");

            // Crear tabla envios (opcional si no necesitas envíos separados de pedidos)
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

    private static void insertarDatosPrueba()
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
                               "(1, 'Rider 1', 'N'), " +
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

    private static boolean verificarExistenciaArticulo(int codArt, Connection con)
    {
        try
        {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM articulos WHERE codart = ?");
            stmt.setInt(1, codArt);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt(1) > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    private static boolean verificarExistenciaPedido(int codPed, Connection con)
    {
        try
        {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM pedidos WHERE codped = ?");
            stmt.setInt(1, codPed);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt(1) > 0; 
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean verificarExistenciaRider(int codRider, Connection con)
    {
        try
        {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM riders WHERE codigo = ?");
            stmt.setInt(1, codRider);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return rs.getInt(1) > 0; // Si el conteo es mayor que 0, el rider existe
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false; // Si hay algún problema o el rider no existe, devuelve false
    }

    private static String hacerPedido(Scanner input, Connection con)
    {
        try
        {
            System.out.println("¿Cuál es su código de cliente y direccion (X;CALLE Y)?");
            String[] infoCliente = input.nextLine().split(";");
            System.out.println("¿Qué producto (código) quiere pedir y qué cantidad (PRODUCTO X; Y)?");
            String[] infoProducto = input.nextLine().split(";");

            if (infoCliente.length < 2 || infoProducto.length < 2)
                return "Error en la entrada de datos.";

            int codArt = Integer.parseInt(infoProducto[0]);
            int cantidad = Integer.parseInt(infoProducto[1]);

            // Verificar si el artículo existe
            if (!verificarExistenciaArticulo(codArt, con))
                return "El código de artículo no existe.";

            int codRider = getRiderDisponible(con);
            if (codRider == 0)
                return "No hay riders disponibles.";

            // Verificar si el rider existe y está disponible
            if (!verificarExistenciaRider(codRider, con))
                return "El rider no existe o no está disponible.";
            
            insert(getCountPedido(con) + 1, LocalDate.now(), Integer.parseInt(infoCliente[0]), infoCliente[1], codArt, cantidad, codRider, 'N', con);
            actualizarDatos(con, cantidad, codRider, codArt);

            con.commit();
            System.out.println("Transacción realizada exitosamente.");
            
            // Crear el hilo para simular la entrega del pedido
            Thread hiloEntrega = new Thread(() -> {
                try
                {
                    // Esperar un tiempo aleatorio entre 1 y 5 segundos (1000-5000 milisegundos)
                    int tiempoEspera = (int) (Math.random() * 4000) + 1000; // 1000 a 5000 ms
                    Thread.sleep(tiempoEspera);

                    // Actualizar las tablas de pedido y rider para simular la entrega
                    int codped = getCountPedido(con); // Obtener el último pedido realizado

                    // Actualizar la tabla pedidos, marcando como entregado
                    String updatePedido = "UPDATE pedidos SET completado = 'S', fecha_entrega = ? WHERE codped = ?";
                    PreparedStatement stmtPedido = con.prepareStatement(updatePedido);
                    stmtPedido.setString(1, LocalDate.now().toString()); // Fecha y hora de la entrega
                    stmtPedido.setInt(2, codped);
                    stmtPedido.executeUpdate();

                    // Actualizar la tabla riders, marcando al rider como disponible
                    String updateRider = "UPDATE riders SET disponible = 'S' WHERE codigo = ?";
                    PreparedStatement stmtRider = con.prepareStatement(updateRider);
                    stmtRider.setInt(1, codRider);
                    stmtRider.executeUpdate();

                    System.out.println("Entrega realizada con éxito. El pedido ha sido entregado y el rider está disponible nuevamente.");

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            });

            // Iniciar el hilo de entrega
            hiloEntrega.start();

            return "Pedido realizado con éxito.";
        }
        catch (Exception ex)
        {
            try
            {
                con.rollback();
            }
            catch (SQLException rollbackEx)
            {
                rollbackEx.printStackTrace();
            }
            System.out.println("Error: " + ex.getMessage());
            return "No se pudo realizar el pedido.";
        }
    }

    private static int getRiderDisponible(Connection con)
    {
        int countRider = getCantidadRiders(con);
        int codRider = 1; 

        while (codRider <= countRider)
        {
            try
            {
                PreparedStatement stmtRider = con.prepareStatement("SELECT disponible FROM riders WHERE codigo = ?");
                stmtRider.setInt(1, codRider);
                ResultSet rs = stmtRider.executeQuery();

                if (rs.next())
                    if (rs.getString("disponible").equals("S"))
                        return codRider;
                codRider++;
            }
            catch (SQLException ex)
            {
                System.out.println("Error al consultar disponibilidad del rider: " + ex.getMessage());
                codRider++; // Avanzar al siguiente rider incluso en caso de error
            }
        }

        System.out.println("No hay riders disponibles.");
        return 0; // Devolver 0 si no hay riders disponibles
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
    
    private static void insert(int codped, LocalDate fecha, int codCli, String direccion, int codArt, int cantidad, int riderCodigo, char completado, Connection con)
    {
        try
        {
            String sqlPedidos = "INSERT INTO pedidos (codped, fecha, codcli, direccion, codart, cantidad, codrider, completado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtPedidos = con.prepareStatement(sqlPedidos);

            // Asignar valores a los parámetros
            stmtPedidos.setInt(1, codped); // Código del pedido
            stmtPedidos.setString(2, fecha.toString()); // Fecha del pedido
            stmtPedidos.setInt(3, codCli); // Código del cliente
            stmtPedidos.setString(4, direccion); // Dirección del cliente
            stmtPedidos.setInt(5, codArt); // Código del artículo
            stmtPedidos.setInt(6, cantidad); // Cantidad solicitada
            stmtPedidos.setInt(7, riderCodigo); // Código del rider
            stmtPedidos.setString(8, String.valueOf(completado)); // Estado del pedido ('S' o 'N')

            stmtPedidos.executeUpdate();
            
            System.out.println("Pedido registrado exitosamente.");
        }
        catch (Exception ex)
        {
            System.out.println("Error al insertar pedido: " + ex.getMessage());
        }
    }

    private static void mostrarPedidos(Connection con)
    {
    	try
    	{
    		ResultSet result = con.createStatement().executeQuery("SELECT * FROM pedidos");
    		System.out.println();
    		System.out.printf("%-10s %-15s %-15s %-10s %-20s %-10s %-10s %-10s %-10s\n", "CodPed", "Fecha", "FechaEntrega" , "CodCli", "Direccion", "CodArt", "Cantidad", "codRider", "Completado");
    		System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
    		
    		while (result.next())
    		{
                int codPed = result.getInt("codped");
                String fecha = result.getString("fecha");
                int codCli = result.getInt("codcli");
                String fecha_entrega = result.getString("fecha_entrega");
                String direccion = result.getString("direccion");
                int codArt = result.getInt("codart");
                int cantidad = result.getInt("cantidad");
                int codRider = result.getInt("codrider");
                String completado = result.getString("completado");

                System.out.printf("%-10d %-15s %-15s %-10d %-20s %-10d %-10d %-10d %-15s\n", codPed, fecha, fecha_entrega, codCli, direccion, codArt, cantidad, codRider, completado);
    		}
    	}
    	catch (SQLException e)
    	{
			e.printStackTrace();
		}
    }
    
    private static void actualizarDatos(Connection con, int cantidad, int rider, int codped)
    {
        try
        {
            // Actualizar existencias de artículos
            PreparedStatement stmtArticulos = con.prepareStatement("UPDATE articulos SET existencias = existencias - ? WHERE codart = ?");
            stmtArticulos.setInt(1, cantidad);
            stmtArticulos.setInt(2, codped); // Aquí usaremos el codart correspondiente
            stmtArticulos.executeUpdate();

            // Marcar rider como no disponible
            PreparedStatement stmtRider = con.prepareStatement("UPDATE riders SET disponible = ? WHERE codigo = ?");
            stmtRider.setString(1, "N");
            stmtRider.setInt(2, rider);
            stmtRider.executeUpdate();

            // Insertar en la tabla envíos
            String sqlEnvios = "INSERT INTO envios (codped, codrider, terminado) VALUES (?, ?, ?)";
            PreparedStatement stmtEnvios = con.prepareStatement(sqlEnvios);
            stmtEnvios.setInt(1, codped); // El código correcto del pedido
            stmtEnvios.setInt(2, rider); // Rider actual
            stmtEnvios.setString(3, "N"); // Pedido aún no terminado
            stmtEnvios.executeUpdate();

            System.out.println("Datos actualizados correctamente: artículo, rider y envío.");
        }
        catch (Exception ex)
        {
            System.out.println("Error al actualizar datos: " + ex.getMessage());
        }
    }


    private static void finalizarPedido(Scanner input, Connection con)
    {
    	System.out.printf("Introduce el numero de pedido que quieres completar: ");
    	int codped = input.nextInt();
    	
    	// mirar si existe el pedido, cambiarlo por completado y actualizar el rider que estaba a cargo
    	if (verificarExistenciaPedido(codped, con))
    	{
    		try
    		{
    			PreparedStatement stmtPedidos = con.prepareStatement("UPDATE pedidos SET completado = 'S' WHERE codped = ?");
                stmtPedidos.setInt(1, codped);
                stmtPedidos.executeUpdate();
                
                PreparedStatement stmtRider = con.prepareStatement("UPDATE riders SET disponible = 'S' WHERE codigo = ?");
                
                PreparedStatement stmtCodRider = con.prepareStatement("SELECT codrider FROM pedidos WHERE codped = ?");
                stmtCodRider.setInt(1, codped);
                ResultSet rsCodRider = stmtCodRider.executeQuery();
                int codRider = 0;
                if (rsCodRider.next())
                    codRider = rsCodRider.getInt(1);
                
                stmtRider.setInt(1, codRider);
                stmtRider.executeUpdate();
    		}
    		catch (Exception ex)
    		{
    			System.out.println(ex.getMessage());
    		}
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
        		System.out.println("¿Qué quieres hacer?\n1. Hacer un pedido.\n2. Finalizar un pedido.\n3. Mostrar pedidos.\n9. Salir.");
            	select = input.nextInt();
            	input.nextLine();
            	
            	switch (select)
            	{
            	case 1:
            		System.out.println(hacerPedido(input, con));
            		break;
            	case 2:
            		finalizarPedido(input, con); // esta opcion la tenia para cuando no se actualizaba el resultado solo con el hilo
            		break;
            	case 3:
            		mostrarPedidos(con);
            		break;
            	case 9:
            		System.out.println("Conexión finalizada.");
            		break;
            	default:
            		System.out.println("Elección erronea, vuelva a intentarlo.");
            	}
        	} 
        	while (select != 9);
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
