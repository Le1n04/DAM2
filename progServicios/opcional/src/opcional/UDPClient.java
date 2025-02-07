package opcional;

// Servidor UDP
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

// Cliente UDP
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost"); // Cambiar por la IP del servidor si está en red local

            // Enviar un saludo al servidor
            String message = "¡Hola servidor UDP!";
            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);

            // Recibir la respuesta del servidor
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Respuesta del servidor: " + serverResponse);

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
Diferencias principales entre UDP y TCP observadas:

1. **Sin Conexión Establecida (Connectionless):** UDP no requiere establecer una conexión previa como TCP.
2. **Velocidad:** UDP es más rápido porque no hay verificación de errores ni control de flujo.
3. **Fiabilidad:** No garantiza la entrega de paquetes ni el orden correcto, a diferencia de TCP.
4. **Ligereza:** UDP tiene menos sobrecarga, ideal para aplicaciones en tiempo real.
5. **Uso Común:** Juegos en línea, streaming de video/audio, DNS, VoIP.

Pruebas en red local pueden evidenciar pérdidas de paquetes o desorden, lo cual es normal en UDP.
*/
