import math
import random

def mostrar_menu():
    print("\nMENÚ DE OPCIONES")
    print("a) Mostrar un rombo.")
    print("b) Adivinar un número.")
    print("c) Resolver una ecuación de segundo grado.")
    print("d) Tabla de números.")
    print("e) Cálculo del número factorial de un número.")
    print("f) Cálculo de un número de la sucesión de Fibonacci.")
    print("g) Tabla de multiplicar.")
    print("h) Salir")

def leer_entero(prompt):
    while True:
        try:
            return int(input(prompt))
        except ValueError:
            print("Por favor, introduce un numero valido.")

def mostrar_rombo():
    while True:
        n = leer_entero("Introduce un numero impar para formar el rombo: ")
        if n % 2 == 0:
            print("El numero no es impar. Intentalo de nuevo.")
        else:
            break

    for i in range(n // 2 + 1):
        print(' ' * (n // 2 - i) + '*' * (2 * i + 1))
    for i in range(n // 2 - 1, -1, -1):
        print(' ' * (n // 2 - i) + '*' * (2 * i + 1))

def adivinar_numero():
    numero_secreto = random.randint(1, 100)
    intento = 0
    while intento != numero_secreto:
        intento = leer_entero("Adivina el número entre 1 y 100: ")
        if intento < numero_secreto:
            print("El número es mayor.")
        elif intento > numero_secreto:
            print("El número es menor.")
        else:
            print("¡Correcto! Has adivinado el número.")

def resolver_ecuacion_segundo_grado():
    print("Resolviendo ecuación de segundo grado ax^2 + bx + c = 0")
    a = leer_entero("Introduce el valor de a: ")
    b = leer_entero("Introduce el valor de b: ")
    c = leer_entero("Introduce el valor de c: ")

    discriminante = b ** 2 - 4 * a * c
    if discriminante < 0:
        print("La ecuación no tiene solución real.")
    else:
        x1 = (-b + math.sqrt(discriminante)) / (2 * a)
        x2 = (-b - math.sqrt(discriminante)) / (2 * a)
        print(f"Las soluciones son: x1 = {x1}, x2 = {x2}")

def tabla_numeros():
    filas = leer_entero("Introduce el número de filas: ")
    columnas = leer_entero("Introduce el número de columnas: ")
    for _ in range(filas):
        fila = [random.randint(1, 100) for _ in range(columnas)]
        print(fila)

def factorial(n):
    if n == 0 or n == 1:
        return 1
    else:
        return n * factorial(n - 1)

def calcular_factorial():
    n = leer_entero("Introduce un número para calcular su factorial: ")
    print(f"El factorial de {n} es {factorial(n)}")

def fibonacci(n):
    if n == 1 or n == 2:
        return 1
    else:
        return fibonacci(n - 1) + fibonacci(n - 2)

def calcular_fibonacci():
    n = leer_entero("Introduce la posición en la sucesión de Fibonacci: ")
    print(f"El número en la posición {n} de la sucesión de Fibonacci es {fibonacci(n)}")

def tabla_multiplicar():
    n = leer_entero("Introduce el número para calcular su tabla de multiplicar: ")
    for i in range(1, 11):
        print(f"{n} x {i} = {n * i}")

def main():
    while True:
        mostrar_menu()
        opcion = input("\nSelecciona una opción: ").lower()
        
        if opcion == 'a':
            mostrar_rombo()
        elif opcion == 'b':
            adivinar_numero()
        elif opcion == 'c':
            resolver_ecuacion_segundo_grado()
        elif opcion == 'd':
            tabla_numeros()
        elif opcion == 'e':
            calcular_factorial()
        elif opcion == 'f':
            calcular_fibonacci()
        elif opcion == 'g':
            tabla_multiplicar()
        elif opcion == 'h':
            print("Saliendo del programa...")
            break
        else:
            print("Opción incorrecta, por favor elige una opción válida.")

        input("\nPulsa Enter para continuar...")

if __name__ == "__main__":
    main()
