package opcional;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPServer {
    public static void main(String[] args) {
        try {
            // Crear el socket en el puerto 9876
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];

            System.out.println("Servidor UDP en espera de mensajes...");

            while (true) {
                // Recibir el paquete
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Mensaje recibido: " + clientMessage);

                // Responder con saludo y hora actual
                String serverMessage = "¡Hola desde el servidor! Hora actual: " + new SimpleDateFormat("HH:mm:ss").format(new Date());
                byte[] sendData = serverMessage.getBytes();

                // Enviar la respuesta al cliente
                DatagramPacket sendPacket = new DatagramPacket(
                        sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
