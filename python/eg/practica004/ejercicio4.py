def es_palindromo_de_otro(texto1, texto2, ignorar_mayusculas=True):
    if ignorar_mayusculas:
        texto1 = texto1.lower()
        texto2 = texto2.lower()

    return texto1.replace(" ", "") == texto2.replace(" ", "")[::-1]

def comprobar_palindromos():
    texto1 = input("Introduce el primer texto: ")
    texto2 = input("Introduce el segundo texto: ")

    opcion = input("¿Deseas ignorar mayúsculas/minúsculas? (s/n): ").lower()
    ignorar_mayusculas = opcion == "s"

    if es_palindromo_de_otro(texto1, texto2, ignorar_mayusculas):
        print(f'"{texto1}" es un palíndromo de "{texto2}".')
    else:
        print(f'"{texto1}" no es un palíndromo de "{texto2}".')

comprobar_palindromos()
