from persona import Persona
from cuenta import Cuenta
from cuenta_joven import CuentaJoven

def main():
    # Crear una persona mayor de edad
    persona1 = Persona("Juan", "García López", "12345678A", 30)
    print("Persona 1:")
    print(persona1.mostrar())
    print(f"¿Es mayor de edad? {'Sí' if persona1.mayorDeEdad() else 'No'}\n")
    
    # Crear una persona joven
    persona2 = Persona("Ana", "Martínez Ruiz", "87654321B", 20)
    print("Persona 2:")
    print(persona2.mostrar())
    print(f"¿Es mayor de edad? {'Sí' if persona2.mayorDeEdad() else 'No'}\n")
    
    # Crear una cuenta normal
    cuenta1 = Cuenta(persona1, 1000)
    print("Cuenta normal:")
    print(cuenta1.mostrar())
    cuenta1.ingresar(500)
    cuenta1.retirar(200)
    print("\nDespués de operaciones:")
    print(cuenta1.mostrar())
    print()
    
    # Crear una cuenta joven
    cuenta_joven = CuentaJoven(persona2, 500, 10)
    print("Cuenta joven:")
    print(cuenta_joven.mostrar())
    cuenta_joven.ingresar(300)
    cuenta_joven.retirar(100)
    print("\nDespués de operaciones:")
    print(cuenta_joven.mostrar())

if __name__ == "__main__":
    main()
