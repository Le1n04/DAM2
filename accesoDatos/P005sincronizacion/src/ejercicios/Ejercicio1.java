package ejercicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio1
{
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Falta el comando a ejecutar.");
            return;
        }

        ProcessBuilder builder = new ProcessBuilder(args);

        try
        {
            Process proceso = builder.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null)
                System.out.println(linea);

            int exitCode = proceso.waitFor();
            System.out.println("El proceso terminó con el valor de salida: " + exitCode);
        }
        catch (IOException e)
        {
            System.out.println("Error de E/S: " + e.getMessage());
        }
        catch (InterruptedException e)
        {
            System.out.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}
