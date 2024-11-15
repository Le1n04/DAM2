package ejercicio;

class Saludo
{
	private int empleadosEsperando = 0;
	private boolean jefeSaludo = false;
	
	public synchronized void llegadaEmpleado(Personal p)
	{
		System.out.println(p.getNombre() + " llegó.");
		empleadosEsperando++;
		
		if (empleadosEsperando == Personal.empleadosTotal)
			notifyAll();
		
		while (!jefeSaludo)
		{
            try
            {
                wait();
            }
            catch (InterruptedException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
		
		System.out.println(p.getNombre() + ": Buenos dias jefe.");
	}
	
	public synchronized void saludoJefe(Personal p)
	{
		while (empleadosEsperando < Personal.empleadosTotal)
		{
			try
			{
				wait();
			}
			catch (InterruptedException ie)
			{
				System.out.println(ie.getMessage());
			}
		}
		
		System.out.println(p.getNombre() + ": Buenos días empleados.");
		jefeSaludo = true;
		notifyAll();
	}
}

class Personal extends Thread
{
	public static int empleadosTotal = 0;
	private String nombre;
	private Saludo saludo;
	private boolean jefe;
	
	public Personal(String nombre, Saludo s, boolean jefe)
	{
		this.nombre = nombre;
		this.saludo = s;
		this.jefe = jefe;
		if (!jefe)
			empleadosTotal++;
	}

	public String getNombre()
	{
		return nombre;
	}
	
	public boolean isJefe()
	{
		return jefe;
	}
	
	@Override
	public void run()
	{
		if (jefe)
			saludo.saludoJefe(this);
		else
			saludo.llegadaEmpleado(this);
	}
}

public class Main
{
	
    public static void main(String[] args)
    {
        Saludo s = new Saludo();

        Personal Empleado1 = new Personal("Pepe", s, false);
        Personal Empleado2 = new Personal("José", s, false);
        Personal Empleado3 = new Personal("Pedro", s, false);
        Personal Jefe1 = new Personal("JEFE", s, true);

        Empleado1.start();
        Empleado2.start();
        Empleado3.start();
        Jefe1.start();
    }

}