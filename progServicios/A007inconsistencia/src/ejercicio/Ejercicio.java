/*package ejercicio;

public class Ejercicio
{

    private static int contador = 0; // Variable compartida

    public static void main(String[] args) throws InterruptedException
    {
        Thread hilo1 = new Thread(new Incrementador(1)); // Incrementa en 1
        Thread hilo2 = new Thread(new Incrementador(3)); // Incrementa en 3
        Thread hilo3 = new Thread(new Incrementador(5)); // Incrementa en 5

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try
        {
        	hilo1.join();
        	hilo2.join();
        	hilo3.join();
        }
        catch (Exception ex)
        {
        	System.out.println(ex.getMessage());
        }

        System.out.println("Valor final del contador: " + contador);
    }

    static class Incrementador implements Runnable
    {
        private final int incremento;

        public Incrementador(int incremento)
        {
            this.incremento = incremento;
        }
            
        @Override
        public void run()
        {
            for (int i = 0; i < 1000; i++)
            {
                int valorPrevio = contador;
            	contador += incremento;
            	System.out.println("Hilo que incrementa en " + incremento + ": Contador pasa de " + valorPrevio + " a " + contador);
            }
        }
    }
}
*/