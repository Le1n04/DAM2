package Ejercicio;

import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            ProcessBuilder pbNotepad = new ProcessBuilder("notepad.exe");
            Process notepadProcess = pbNotepad.start();
            System.out.println("Bloc de notas abierto. Esperando a que se cierre...");

            int notepadExitCode = notepadProcess.waitFor();
            System.out.println("El bloc de notas se ha cerrado. Código de salida: " + notepadExitCode);

        }
        catch (IOException e)
        {
            System.out.println("Error al iniciar el bloc de notas: " + e.getMessage());
        }
        catch (InterruptedException e)
        {
            System.out.println("Proceso del bloc de notas interrumpido: " + e.getMessage());
        }

        try
        {
            ProcessBuilder pbPaint = new ProcessBuilder("mspaint.exe");
            Process paintProcess = pbPaint.start();
            System.out.println("Paint abierto. Esperando que finalice...");

            int paintExitCode = paintProcess.waitFor();
            System.out.println("Paint se ha cerrado. Código de salida: " + paintExitCode);

        }
        catch (IOException e)
        {
            System.out.println("Error al abrir Paint: " + e.getMessage());
        }
        catch (InterruptedException e)
        {
            System.out.println("Proceso de Paint interrumpido: " + e.getMessage());
        }
    }
}
