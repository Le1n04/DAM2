# no me he dado cuenta de que en una parte del codigo he llamado a una variable "len" y he estado liado 45 minutos porque no entendia porque
# no me funcionaba la funcion len entonces esta hecho un poco a las prisas

def printMenu():
    print("a. Reemplazar vocales de una frase.")
    print("b. Mensaje cuando un numero no sea mayor que el primero.")
    print("c. Encontrar la primera palabra más larga")
    print("d. Mostrar rectángulo con números impares entre 0 y 100.")
    print("e. Contar la aparición de cada caracter en una palabra.")
    print("f. Salir.")
    return input("Introduce tu opción: ")

def isVocal(char):
    if (char == 'A' or 'E' or 'I' or ' O' or 'U'):
        return True
    return False

def palabraMayor():
    str, frase = input("Introduce una frase: "), []
    frase = str.split(" ")
    i = int(0)
    j = int(0)
    lengthFrase = int(len(frase))
    cant = int(0)

    while (i < lengthFrase):
        if len(frase[i]) > cant:
                cant = len(frase[i])
                j = i
        i = i+1
        
    print(f"La palabra mas larga es {frase[j]}")

def imprimirImpares():
    lista = []
    i = 1
    j = 0
    while (i < 101):
        if i % 2 != 0:
            print(f"{i} ", end="")
            j = j + 1
            if (j == 10):
                print("\n")
                j = 0
        i = i + 1
        
def reemplazar():
    frase= input("Introduce tu frase: ")
    frase2 = frase.replace('a', '*')
    frase2 = frase2.replace('e', '*')
    frase2 = frase2.replace('i', '*')
    frase2 = frase2.replace('o', '*')
    frase2 = frase2.replace('u', '*')

    print((frase2))

def main():
    while (True):
        opcion = printMenu()
        
        if opcion == 'A' or opcion == 'a':
            reemplazar()
        elif opcion == 'B' or opcion == 'b':
            mensajeMayor()
        elif opcion == 'C' or opcion == 'c':
            palabraMayor()
        elif opcion == 'D' or opcion == 'd':
            imprimirImpares()
        elif opcion == 'E' or opcion == 'e':
            cantidadLetras()
        elif opcion == 'F' or opcion == 'f':
            print("Adios.")
            return
        else:
            print("Error, opción incorrecta.")

def cantidadLetras():
    palabra = sorted(input("Introduce una palabra: "))
    i = 0
    j = 0
    tmp = ''

    while i < len(palabra):
        if (palabra[i] != tmp):
            if i > 0:
                print(j)
            print(palabra[i], end=" tiene ")
            tmp = palabra[i]
            j = 0
        j = j + 1
        i = i + 1
    print(j)

def mensajeMayor():
    cant = input("Cuantos numeros va a meter: ")
    i = 0
    tmp = int(0)

    while (i < int(cant)):
        num = int(input("Introduce el numero:"))
        if (i > 0):
            if num < tmp:
                print("Menor.")
        tmp = int(num)
        i = i + 1

main()