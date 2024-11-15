class Persona:
    def __init__(self, nombre, direccion, telefono):
        self.nombre = nombre
        self.direccion = direccion
        self.telefono = telefono

    def __str__(self):
        return f"nombre: {self.nombre} direccion: {self.direccion} telefono: {self.telefono}"


def mostrar_menu():
    print("\nmenu de opciones")
    print("a) listado de contactos por orden alfabetico")
    print("b) anadir un nuevo contacto")
    print("c) modificar un contacto")
    print("d) buscar un numero de telefono")
    print("e) eliminar un contacto")
    print("f) salir")


def listar_contactos(contactos):
    if not contactos:
        print("no hay contactos en la lista")
    else:
        for nombre in sorted(contactos.keys()):
            print(contactos[nombre])


def anadir_contacto(contactos):
    try:
        nombre = input("introduce el nombre del contacto: ").upper()
        direccion = input("introduce la direccion del contacto: ")
        telefono = input("introduce el numero de telefono del contacto: ")

        if nombre in contactos:
            print("el contacto ya existe")
            actualizar = input("deseas actualizar su telefono? (s/n): ").lower()
            if actualizar == 's':
                contactos[nombre].telefono = telefono
                print("telefono actualizado")
        else:
            contactos[nombre] = Persona(nombre, direccion, telefono)
            print("contacto anadido")
    except Exception as e:
        print(f"ocurrio un error al anadir el contacto: {e}")


def modificar_contacto(contactos):
    try:
        nombre = input("introduce el nombre del contacto a modificar: ").upper()

        if nombre in contactos:
            direccion = input("introduce la nueva direccion: ")
            telefono = input("introduce el nuevo numero de telefono: ")
            contactos[nombre].direccion = direccion
            contactos[nombre].telefono = telefono
            print("contacto modificado")
        else:
            print("el contacto no existe")
            insertar = input("deseas insertar el contacto? (s/n): ").lower()
            if insertar == 's':
                direccion = input("introduce la direccion: ")
                telefono = input("introduce el numero de telefono: ")
                contactos[nombre] = Persona(nombre, direccion, telefono)
                print("contacto anadido")
    except Exception as e:
        print(f"ocurrio un error al modificar el contacto: {e}")


def buscar_numero(contactos):
    try:
        telefono = input("introduce el numero de telefono a buscar: ")
        for contacto in contactos.values():
            if contacto.telefono == telefono:
                print(f"contacto encontrado: {contacto.nombre}")
                return
        print("numero de telefono no encontrado")
    except Exception as e:
        print(f"ocurrio un error al buscar el numero de telefono: {e}")


def eliminar_contacto(contactos):
    try:
        nombre = input("introduce el nombre del contacto a eliminar: ").upper()
        if nombre in contactos:
            del contactos[nombre]
            print("contacto eliminado")
        else:
            print("el contacto no existe")
    except Exception as e:
        print(f"ocurrio un error al eliminar el contacto: {e}")


def main():
    contactos = {}
    while True:
        mostrar_menu()
        opcion = input("selecciona una opcion: ").lower()

        try:
            if opcion == 'a':
                listar_contactos(contactos)
            elif opcion == 'b':
                anadir_contacto(contactos)
            elif opcion == 'c':
                modificar_contacto(contactos)
            elif opcion == 'd':
                buscar_numero(contactos)
            elif opcion == 'e':
                eliminar_contacto(contactos)
            elif opcion == 'f':
                print("saliendo del programa")
                break
            else:
                print("opcion no valida, por favor elige otra opcion")
        except Exception as e:
            print(f"ocurrio un error inesperado: {e}")

        input("presiona una tecla para continuar")


if __name__ == "__main__":
    main()
