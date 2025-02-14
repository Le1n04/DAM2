
package Examen;

import org.bson.Document;

public class Articulo
{
	private String nombre;
	private String descripcion;
	private double precio;
	private String categoria;
	private int stock;

	public Articulo(String nombre, String descripcion, double precio, String categoria, int stock)
	{
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
		this.stock = stock;
	}

	public Document toDocument()
	{
		return new Document("nombre", nombre).append("descripcion", descripcion).append("precio", precio)
				.append("categoria", categoria).append("stock", stock);
	}

	public static Articulo fromDocument(Document doc)
	{
		return new Articulo(doc.getString("nombre"), doc.getString("descripcion"), doc.getDouble("precio"),
				doc.getString("categoria"), doc.getInteger("stock"));
	}
}