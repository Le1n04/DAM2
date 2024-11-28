from abc import ABC, abstractmethod

class Producto:
    def __init__(self, nombre, precio, descuento=0):
        self.nombre = nombre
        self.precio = precio
        self._descuento = descuento

    @property
    def descuento(self):
        return self._descuento

    @descuento.setter
    def descuento(self, valor):
        self._descuento = valor

    def precio_final(self):
        return self.precio * (1 - self.descuento)

class Electronico(Producto):
    @property
    def descuento(self):
        return min(self._descuento, 0.10)

class Ropa(Producto):
    @property
    def descuento(self):
        return min(self._descuento, 0.20)

class Comida(Producto):
    @property
    def descuento(self):
        return 0

# Ejemplo de uso
if __name__ == "__main__":
    productos = [
        Electronico("Laptop", 1000, 0.15),  # Intentará aplicar 15% pero máximo es 10%
        Ropa("Camisa", 50, 0.25),          # Intentará aplicar 25% pero máximo es 20%
        Comida("Manzanas", 10, 0.05)       # No aplicará ningún descuento
    ]

    for producto in productos:
        print(f"{producto.__class__.__name__}: {producto.nombre}")
        print(f"Precio original: {producto.precio}€")
        print(f"Descuento aplicado: {producto.descuento * 100}%")
        print(f"Precio final: {producto.precio_final()}€")
        print("-" * 40)
