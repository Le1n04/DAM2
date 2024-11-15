package ejercicio;

import java.time.LocalDateTime;
import java.util.ArrayList;

class Raton extends Thread {
    String nombre;
    int tiempocome;
    Thread otroRaton;

    public Raton(String nombre, int tiempocome)
    {
        this.nombre = nombre;
        this.tiempocome = tiempocome;
    }

    public Raton(String nombre, int tiempocome, Thread otroRaton)
    {
        this.nombre = nombre;
        this.tiempocome = tiempocome;
        this.otroRaton = otroRaton;
    }

    @Override
    public void run()
    {
        try {
            System.out.printf("%s empieza la merienda %n", nombre);
            Thread.sleep(500 * tiempocome); // Simular el tiempo de comer
            
            // Estado TIMED_WAITING: Esperar 2 segundos usando join con tiempo límite
            System.out.printf("%s está en estado TIMED_WAITING (esperando a otro ratón con límite de tiempo)%n", nombre);
            if (otroRaton != null) {
                otroRaton.join(2000); // Esperar hasta 2 segundos
            }

            // Estado WAITING: Esperar indefinidamente a que otro ratón termine (espera indefinida)
            System.out.printf("%s está en estado WAITING (esperando indefinidamente a otro ratón)%n", nombre);
            if (otroRaton != null) {
                otroRaton.join(); // Esperar indefinidamente a que otro ratón termine
            }

            System.out.printf("%s terminó de comer %n", nombre);
        } catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }
}

public class Ejercicio
{
    public static void main(String[] args)
    {
        LocalDateTime horaInicio = LocalDateTime.now();

        Raton tinky = new Raton("Tinky", 4);
        Raton winky = new Raton("Winky", 1, tinky); // Winky esperará a Tinky
        Raton poo = new Raton("Poo", 3, winky);    // Poo esperará a Winky
        Raton lala = new Raton("Lalala", 9, poo);  // Lalala esperará a Poo

        Thread.State[] estadoAnterior = {null, null, null, null};

        tinky.start();
        winky.start();
        poo.start();
        lala.start();

        while (true)
        {
            ArrayList<Thread.State> estadoActual = new ArrayList<>();
            estadoActual.add(tinky.getState());
            estadoActual.add(winky.getState());
            estadoActual.add(poo.getState());
            estadoActual.add(lala.getState());

            for (int i = 0; i < estadoActual.size(); i++)
            {
                if (estadoActual.get(i) != estadoAnterior[i])
                {
                    System.out.println("Estado del hilo " + obtenerNombre(i) + ": " + estadoActual.get(i));
                    estadoAnterior[i] = estadoActual.get(i);
                }
            }

            if (tinky.getState() == Thread.State.TERMINATED && winky.getState() == Thread.State.TERMINATED &&
                poo.getState() == Thread.State.TERMINATED && lala.getState() == Thread.State.TERMINATED)
                break;

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }

        LocalDateTime horaFin = LocalDateTime.now();
        System.out.println("Todos los ratones han terminado de comer.");
    }

    private static String obtenerNombre(int index)
    {
        switch (index)
        {
            case 0: return "Tinky";
            case 1: return "Winky";
            case 2: return "Poo";
            case 3: return "Lalala";
            default: return "Desconocido";
        }
    }
}
