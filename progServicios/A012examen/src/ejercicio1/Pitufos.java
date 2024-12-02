package ejercicio1;

//EJERCICIO 1
import java.util.concurrent.Semaphore;

public class Pitufos
{

	public static void main(String[] args) throws InterruptedException
	{ // creacion del comedor y los pitufos
		String[] pitufos = { "Papa Pitufo", "Pitufina", "Filosofo", "Pintor", "Gruñon", "Bromista", "Dormilón",
				"Tímido", "Bonachón", "Romántico" };
		Semaphore comedor = new Semaphore(3);
		int[] platosConsumidos = { 0 };

		for (String pitufo : pitufos) // voy pasando entre los pitufos que tenemos
		{
			new Thread(() -> {
				try
				{
					comedor.acquire();
					System.out.println(pitufo + " entra al comedor.");
					for (int i = 1; i <= 2; i++) // se tiene que comer sus platos
					{
						System.out.println(pitufo + " se zampa el plato " + i);
						Thread.sleep(1000); // Simula el tiempo de comer
					}
					System.out.println(pitufo + " sale del comedor."); // y se va
					platosConsumidos[0]++;
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
				finally
				{
					comedor.release(); // se le saca
				}
			}).start();
		}

		while (platosConsumidos[0] < pitufos.length)
			Thread.sleep(100); // algo de tiempo para que sea mas legible a la hora de correr el programa
		System.out.println("todos los pitufos han comido.");
	}
}
