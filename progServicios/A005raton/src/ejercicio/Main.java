package ejercicio;

import java.time.Duration;
import java.time.LocalDateTime;

class Raton extends Thread
{
    String nombre;
    int tiempocome;

    public Raton(String nombre, int tiempocome)
    {
        this.nombre = nombre;
        this.tiempocome = tiempocome;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.printf("%s empieza la merienda %n", nombre);
            Thread.sleep(500 * tiempocome);
            System.out.printf("%s terminó de comer %n", nombre);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

public class Main
{
    public static void main(String[] args)
    {
    	LocalDateTime horaInicio = LocalDateTime.now();
    	
        Raton tinky = new Raton("Tinky", 4);
        Raton winky = new Raton("Winky", 1);
        Raton poo = new Raton("Poo", 3);
        Raton lala = new Raton("Lalala", 9);

        tinky.start();
        winky.start();
        poo.start();
        lala.start();
        
        try
        {
            tinky.join();
            winky.join();
            poo.join();
            lala.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        LocalDateTime horaFin = LocalDateTime.now();
        Duration duration = Duration.between(horaInicio, horaFin);

        
        System.out.println("Todos los ratones han terminado de comer.");
        System.out.println("Han tardado: " + duration.toString().substring(2));
//    }
}
