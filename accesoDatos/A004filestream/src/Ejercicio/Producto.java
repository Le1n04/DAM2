package Ejercicio;

public class Producto implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2161525814846099729L;
	private String articulo;
    private double precio;
    private int existencias;

    public Producto(String articulo, double precio, int existencias)
    {
        this.articulo = articulo;
        this.precio = precio;
        this.existencias = existencias;
    }

    public String getArticulo()
    {
        return articulo;
    }

    public double getPrecio()
    {
        return precio;
    }

    public int getExistencias()
    {
        return existencias;
    }

    @Override
    public String toString()
    {
        return "Producto: " + articulo + ", Precio: " + precio + ", Existencias: " + existencias;
    }
}
