
package Examen;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO
{
	private final MongoCollection<Document> collection;

	public ArticuloDAO()
	{
		MongoDatabase database = MongoConexion.getDatabase();
		collection = database.getCollection("articulos");
	}

	public void insertarArticulo(Articulo articulo)
	{
		collection.insertOne(articulo.toDocument());
	}

	public List<Articulo> buscarPorNombre(String nombre)
	{
		List<Articulo> articulos = new ArrayList<>();
		for (Document doc : collection.find(eq("nombre", nombre)))
		{
			articulos.add(Articulo.fromDocument(doc));
		}
		return articulos;
	}

	public List<Articulo> buscarPorCategoria(String categoria)
	{
		List<Articulo> articulos = new ArrayList<>();
		for (Document doc : collection.find(eq("categoria", categoria)))
		{
			articulos.add(Articulo.fromDocument(doc));
		}
		return articulos;
	}

	public List<Articulo> buscarPorStockInferior(int cantidad)
	{
		List<Articulo> articulos = new ArrayList<>();
		for (Document doc : collection.find(lt("stock", cantidad)))
		{
			articulos.add(Articulo.fromDocument(doc));
		}
		return articulos;
	}

	public void actualizarPreciosPorCategoria(String categoria, double porcentaje)
	{
		collection.updateMany(eq("categoria", categoria),
				new Document("$mul", new Document("precio", 1 + porcentaje / 100)));
	}

	public void añadirProveedor(String nombreArticulo, String proveedor)
	{
		collection.updateOne(eq("nombre", nombreArticulo),
				new Document("$push", new Document("proveedores", proveedor)));
	}
}
