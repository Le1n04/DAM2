package philosophers;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Main
{
	static int filosofos = 5;
	static Filosofo filosofosArray[] = new Filosofo[filosofos];
	static Palillo palillos[] = new Palillo[filosofos];

	static class Palillo
	{
		public Semaphore mutex = new Semaphore(1);

		void tomar()
		{
			try
			{
				mutex.acquire();
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		}

		void soltar()
		{
			mutex.release();
		}

		boolean estaLibre()
		{
			return mutex.availablePermits() > 0;
		}
	}

	static class Filosofo extends Thread
	{
		public int numero;
		public Palillo palilloIzquierdo;
		public Palillo palilloDerecho;

		Filosofo(int num, Palillo izquierdo, Palillo derecho)
		{
			numero = num;
			palilloIzquierdo = izquierdo;
			palilloDerecho = derecho;
		}

		public void run()
		{
			while (true)
			{
				if (numero % 2 == 0)
				{
					palilloDerecho.tomar();
					System.out.println("Filósofo " + (numero + 1) + " toma el palillo derecho.");
					palilloIzquierdo.tomar();
					System.out.println("Filósofo " + (numero + 1) + " toma el palillo izquierdo.");
				}
				else
				{
					palilloIzquierdo.tomar();
					System.out.println("Filósofo " + (numero + 1) + " toma el palillo izquierdo.");
					palilloDerecho.tomar();
					System.out.println("Filósofo " + (numero + 1) + " toma el palillo derecho.");
				}
				comer();
				palilloIzquierdo.soltar();
				System.out.println("Filósofo " + (numero + 1) + " suelta el palillo izquierdo.");
				palilloDerecho.soltar();
				System.out.println("Filósofo " + (numero + 1) + " suelta el palillo derecho.");
			}
		}

		void comer()
		{
			try
			{
				int tiempoDormir = ThreadLocalRandom.current().nextInt(0, 2000);
				System.out.println("Filósofo " + (numero + 1) + " come durante " + tiempoDormir + "ms");
				Thread.sleep(tiempoDormir);
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		}
	}

	public static void main(String args[])
	{
		for (int i = 0; i < filosofos; i++)
			palillos[i] = new Palillo();
		for (int i = 0; i < filosofos; i++)
		{
			filosofosArray[i] = new Filosofo(i, palillos[i], palillos[(i + 1) % filosofos]);
			filosofosArray[i].start();
		}
		while (true)
		{
			try
			{
				Thread.sleep(2000);
				boolean interbloqueo = true;

				for (Palillo palillo : palillos)
				{
					if (palillo.estaLibre())
					{
						interbloqueo = false;
						break;
					}
				}

				if (interbloqueo)
				{
					Thread.sleep(2000);
					System.out.println("Todos Comen");
					break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		}
		System.out.println("Saliendo del programa...");
		System.exit(0);
	}
}
