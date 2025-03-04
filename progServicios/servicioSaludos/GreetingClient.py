import socket

server_address = ('damplaya.hopto.org', 5000)

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    client_socket.connect(server_address)
    print(f"Conectado a {server_address}")

    question = client_socket.recv(1024).decode('utf-8')
    print(f"Servidor: {question}")

    name = input("Introduce tu nombre: ")
    client_socket.sendall(name.encode('utf-8'))

    greeting = client_socket.recv(1024).decode('utf-8')
    print(f"Servidor: {greeting}")

finally:
    client_socket.close()
