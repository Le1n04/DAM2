package processBuilder;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            ProcessBuilder pbCalc = new ProcessBuilder("calc");
            Process calcProcess = pbCalc.start();
            System.out.println("Proceso de calculadora iniciado. PID: " + calcProcess.pid());

        }
        catch (IOException e)
        {
            System.out.println("Error al iniciar la calculadora: " + e.getMessage());
        }

        try
        {
            ProcessBuilder pbBrowser = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            Process browserProcess = pbBrowser.start();
            System.out.println("Proceso de navegador iniciado. PID: " + browserProcess.pid());

        }
        catch (IOException e)
        {
            System.out.println("Error al iniciar el navegador: " + e.getMessage());
        }

        try
        {
            ProcessBuilder pbCpuHogger = new ProcessBuilder("java", "-cp", ".", "CPULoad");
            Process cpuProcess = pbCpuHogger.start();
            System.out.println("Proceso que consume recursos iniciado. PID: " + cpuProcess.pid());

        }
        catch (IOException e)
        {
            System.out.println("Error al iniciar el proceso que consume recursos: " + e.getMessage());
        }
        
    }
    // LA FUNCION PARA TENER GRAN CONSUMO SERIA ESTA
           /* while (true) {
                double x = Math.random() * Math.random();
    }*/
}
