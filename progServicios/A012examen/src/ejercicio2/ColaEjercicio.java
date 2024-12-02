package ejercicio2;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Cola
{	// hacemos una cola de los pedidos en vez de una lista comun porque las colas tienen metodos que quitan el primero de la cola
    private final Queue<String> cola = new LinkedList<>(); 
    private final Lock lock = new ReentrantLock(); // para controlar el acceso que tenemos a un recurso para los hilos que tenemos

    public void agregarPedido(String pedido)
    {
        lock.lock(); // lo bloqueamos mientras trabajamos
        try
        {
            cola.add(pedido); // añado el pedido a la cola
            System.out.println("Pedido agregado: " + pedido + ". Puesto en la cola: " + cola.size());
        }
        finally
        {
            lock.unlock(); // y lo desbloqueo ya que esta terminado
        }
    }

    public void procesarPedido()
    {
        lock.lock(); // lo bloqueo para el procesamiento del pedido
        try
        {
            if (!cola.isEmpty())
            {
                String pedido = cola.poll(); // quito el primero de la cola como pide el enunciado
                System.out.println("Pedido procesado: " + pedido);
            }
            else
            {
                System.out.println("No hay pedidos para procesar.");
            }
        }
        finally
        {
            lock.unlock(); // lo desbloqueo porque ya esta procesado
        }
    }
}

public class ColaEjercicio
{
    public static void main(String[] args)
    {
        Cola cola = new Cola(); // creo la cola

        Thread creador1 = new Thread(() ->{
            int contador = 1; // contador de 
            while (true)
            {
                cola.agregarPedido("ARTICULO " + contador++);
                try
                {
                    Thread.sleep(1500); // retraso para que tengamos el output mas legible
                }
                catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt(); // si tenemos una excepcion por interrumpir hacemos que se interrupta el hilo
                }
            }
        });

        Thread creador2 = new Thread(() -> {
            int contador = 1;
            while (true)
            {
                cola.agregarPedido("PRODUCTO " + contador++);
                try
                {
                    Thread.sleep(2000); // retraso de creaecion
                }
                catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt(); // lo mismo que antes
                }
            }
        });

        Thread procesador = new Thread(() -> {
            while (true)
            {
                cola.procesarPedido(); // llamamos al procesador
                try
                {
                    Thread.sleep(1000); // retraso de procesar
                }
                catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt(); // lo mismo
                }
            }
        });

        creador1.start(); // iniciamos los creadores y el procesador 
        creador2.start();
        procesador.start();
    }
}