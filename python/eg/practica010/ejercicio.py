import sqlite3

class Producto:
    def __init__(self, id, nombre, precio, stock):
        self.id = id
        self.nombre = nombre
        self.precio = precio
        self.stock = stock

    def __str__(self):
        return f"ID: {self.id}, Nombre: {self.nombre}, Precio: {self.precio}, Stock: {self.stock}"


class Tienda:
    def __init__(self):
        self.productos = {}
        self.conectar_db()

    def conectar_db(self):
        self.conn = sqlite3.connect('tienda.db')
        self.cursor = self.conn.cursor()
        self.cursor.execute('''CREATE TABLE IF NOT EXISTS productos
                               (id INTEGER PRIMARY KEY, nombre TEXT, precio REAL, stock INTEGER)''')
        self.conn.commit()

    def agregar_producto(self, producto):
        self.productos[producto.id] = producto
        self.cursor.execute("INSERT OR REPLACE INTO productos (id, nombre, precio, stock) VALUES (?, ?, ?, ?)",
                            (producto.id, producto.nombre, producto.precio, producto.stock))
        self.conn.commit()

    def eliminar_producto(self, id):
        if id in self.productos:
            del self.productos[id]
            self.cursor.execute("DELETE FROM productos WHERE id = ?", (id,))
            self.conn.commit()
            print(f"Producto con ID {id} eliminado.")
        else:
            print(f"No se encontró el producto con ID {id}.")

    def listar_productos(self):
        print("Lista de productos:")
        for producto in self.productos.values():
            print(producto)

    def cargar_productos_db(self):
        self.cursor.execute("SELECT * FROM productos")
        for row in self.cursor.fetchall():
            producto = Producto(id=row[0], nombre=row[1], precio=row[2], stock=row[3])
            self.productos[producto.id] = producto

    def cerrar_db(self):
        self.conn.close()


def mostrar_menu():
    print("\n--- Menú de Gestión de Tienda ---")
    print("1. Agregar producto")
    print("2. Eliminar producto")
    print("3. Listar productos")
    print("4. Salir")


def main():
    tienda = Tienda()
    tienda.cargar_productos_db()

    while True:
        mostrar_menu()
        opcion = input("Seleccione una opción: ")

        if opcion == '1':
            id = int(input("Ingrese ID del producto: "))
            nombre = input("Ingrese nombre del producto: ")
            precio = float(input("Ingrese precio del producto: "))
            stock = int(input("Ingrese stock del producto: "))
            producto = Producto(id, nombre, precio, stock)
            tienda.agregar_producto(producto)
            print("Producto agregado.")

        elif opcion == '2':
            id = int(input("Ingrese ID del producto a eliminar: "))
            tienda.eliminar_producto(id)

        elif opcion == '3':
            tienda.listar_productos()

        elif opcion == '4':
            tienda.cerrar_db()
            print("Saliendo del programa.")
            break

        else:
            print("Opción no válida. Intente de nuevo.")


if __name__ == "__main__":
    main()