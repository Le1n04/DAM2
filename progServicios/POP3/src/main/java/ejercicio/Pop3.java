package ejercicio;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class Pop3 {

    public static void main(String[] args) {
    	
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", "damplaya.hopto.org");
        properties.put("mail.pop3.port", "110");
        properties.put("mail.pop3.auth", "true");

        String username = "danielj";
        String password = "usuario";

        try {
            Session session = Session.getDefaultInstance(properties);

            Store store = session.getStore("pop3");
            store.connect("damplaya.hopto.org", username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            System.out.println("Número de mensajes: " + messages.length);

            for (int i = 0; i < messages.length; i++) {
                System.out.println("\nMensaje " + (i + 1));
                System.out.println("Asunto: " + messages[i].getSubject());
                System.out.println("De: " + messages[i].getFrom()[0]);
                System.out.println("Contenido: " + messages[i].getContent().toString());
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
Documentación de métodos y propiedades configuradas:

1. Properties:
   - mail.store.protocol: Define el protocolo de acceso a correos (pop3 en este caso).
   - mail.pop3.host: Dirección del servidor de correo.
   - mail.pop3.port: Puerto utilizado para la conexión (110, sin cifrado TLS).
   - mail.pop3.auth: Especifica si se requiere autenticación.

2. Session:
   - getDefaultInstance(Properties): Crea una sesión con las propiedades especificadas.

3. Store:
   - getStore(String): Obtiene el almacén de correos para el protocolo especificado.
   - connect(String, String, String): Conecta al servidor usando las credenciales del usuario.

4. Folder:
   - getFolder(String): Obtiene la carpeta de correos (INBOX).
   - open(int): Abre la carpeta en modo solo lectura.
   - getMessages(): Obtiene todos los mensajes de la carpeta.

5. Message:
   - getSubject(): Obtiene el asunto del mensaje.
   - getFrom(): Obtiene el remitente del mensaje.
   - getContent(): Obtiene el contenido del mensaje.
*/
