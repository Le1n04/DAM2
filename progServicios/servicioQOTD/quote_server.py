import socket
import random

quotes = [
    "La vida es lo que pasa mientras estás ocupado haciendo otros planes. - John Lennon",
    "El único modo de hacer un gran trabajo es amar lo que haces. - Steve Jobs",
    "El éxito es aprender a ir de fracaso en fracaso sin desesperarse. - Winston Churchill",
    "No cuentes los días, haz que los días cuenten. - Muhammad Ali",
    "La mente es todo. En lo que piensas te conviertes. - Buda"
]

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('0.0.0.0', 2017)
server_socket.bind(server_address)

server_socket.listen(5)

print(f"Servidor de 'Cita del Día' escuchando en {server_address}")

while True:
    client_socket, client_address = server_socket.accept()
    print(f"Conexión aceptada de {client_address}")

    quote = random.choice(quotes)
    client_socket.sendall(quote.encode('utf-8'))

    client_socket.close()
