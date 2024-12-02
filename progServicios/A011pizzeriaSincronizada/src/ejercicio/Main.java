package ejercicio;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Main
{

    private static final String[] PIZZAS = {"Margarita", "Pepperoni", "Hawaiana", "Cuatro Quesos", "Vegetariana"};
    private static final int CAPACIDAD_TABLERO = 3;
    private static final String[] tablero = new String[CAPACIDAD_TABLERO];
    private static int indiceProduccion = 0; // Posición para producir
    private static int indiceConsumo = 0;    // Posición para consumir
    private static int pizzasEnTablero = 0; // Contador de pizzas en el tablero
    private static final Semaphore mutex = new Semaphore(1);       // Controla el acceso al tablero
    private static final Semaphore hayEspacio = new Semaphore(CAPACIDAD_TABLERO); // Controla espacio libre en el tablero
    private static final Semaphore hayPizzas = new Semaphore(0);   // Controla pizzas disponibles en el tablero

    static class Horno extends Thread
    {
        private int pizzasProducidas = 0;

        @Override
        public void run()
        {
            try
            {
                while (pizzasProducidas < 20)
                {
                    String pizza = PIZZAS[ThreadLocalRandom.current().nextInt(PIZZAS.length)];
                    hayEspacio.acquire();
                    mutex.acquire();

                    tablero[indiceProduccion] = pizza;
                    indiceProduccion = (indiceProduccion + 1) % CAPACIDAD_TABLERO;
                    pizzasEnTablero++;
                    pizzasProducidas++;

                    System.out.println("El horno acaba de producir una pizza: " + pizza);

                    mutex.release();
                    hayPizzas.release();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    static class Repartidor extends Thread
    {
        private final int id;

        public Repartidor(int id)
        {
            this.id = id;
        }

        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    hayPizzas.acquire();
                    mutex.acquire();

                    String pizza = tablero[indiceConsumo];
                    tablero[indiceConsumo] = null; // Elimina la pizza del tablero
                    indiceConsumo = (indiceConsumo + 1) % CAPACIDAD_TABLERO;
                    pizzasEnTablero--;

                    System.out.println("Repartidor " + id + " retira una pizza: " + pizza);

                    mutex.release();
                    hayEspacio.release();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        Horno horno = new Horno();
        horno.start();

        for (int i = 1; i <= 3; i++)
            new Repartidor(i).start();
    }
}
