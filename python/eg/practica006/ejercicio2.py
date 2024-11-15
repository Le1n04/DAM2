def contar_caracteres(cadena):
    contador = {}
    
    for caracter in cadena:
        if caracter in contador:
            contador[caracter] += 1
        else:
            contador[caracter] = 1
    
    return contador

cadena_usuario = input("Introduce una cadena: ")
resultado = contar_caracteres(cadena_usuario)
print("Cantidad de apariciones de cada carácter:", resultado)
