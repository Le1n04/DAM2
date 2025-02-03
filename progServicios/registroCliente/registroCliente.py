import socket

# Configuración del servidor
server_address = ('143.47.52.177', 5000)  # Cambiar la IP si es necesario

# Crear el socket del cliente
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Conectar al servidor
    client_socket.connect(server_address)
    print(f"Conectado al servidor en {server_address[0]}:{server_address[1]}")

    # Recibir el mensaje de bienvenida
    mensaje = client_socket.recv(1024).decode('utf-8')
    print(f"Servidor: {mensaje}")

    # Enviar el mensaje de identificación (añadir '\n')
    nombre_usuario = input("Introduce tu nombre de usuario: ")
    client_socket.sendall(f"SOY {nombre_usuario}\n".encode('utf-8'))

    # Recibir la respuesta del servidor
    respuesta = client_socket.recv(1024).decode('utf-8')
    print(f"Servidor: {respuesta}")

finally:
    # Cerrar la conexión
    client_socket.close()
