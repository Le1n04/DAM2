package Ejercicio;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Main
{

    static class Bano extends Thread
    {
        private static final Semaphore semaforo = new Semaphore(3, true);
        private final String nombre;

        public Bano(String nombre)
        {
            this.nombre = nombre;
        }

        @Override
        public void run()
        {
            try
            {
                System.out.println(nombre + " quiere ir al baño.");
                semaforo.acquire();
                System.out.println(nombre + " está usando el baño.");
                usarBano();
                System.out.println(nombre + " salió del baño.");
                semaforo.release();
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace(System.out);
            }
        }

        private void usarBano() throws InterruptedException
        {
            int tiempoUso = ThreadLocalRandom.current().nextInt(1000, 3000);
            Thread.sleep(tiempoUso);
        }
    }

    public static void main(String[] args)
    {
        String[] nombres = {"Nombre1", "Nombre2", "Nombre3", "Nombre4", "Nombre5", "Nombre6", "Nombre7", "Nombre8"};

        while (true)
        {
            String nombre = nombres[ThreadLocalRandom.current().nextInt(nombres.length)];
            Bano persona = new Bano(nombre);
            persona.start();

            try
            {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace(System.out);
            }
        }
    }
}
