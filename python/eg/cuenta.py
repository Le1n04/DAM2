from persona import Persona

class Cuenta:
    def __init__(self, titular: Persona, cantidad=0.0):
        if not isinstance(titular, Persona):
            raise ValueError("El titular debe ser una persona")
        self._titular = titular
        self._cantidad = float(cantidad)
        
    @property
    def titular(self):
        return self._titular
        
    @property
    def cantidad(self):
        return self._cantidad
        
    def ingresar(self, cantidad):
        if cantidad <= 0:
            raise ValueError("La cantidad a ingresar debe ser positiva")
        self._cantidad += cantidad
        
    def retirar(self, cantidad):
        if cantidad <= 0:
            raise ValueError("La cantidad a retirar debe ser positiva")
        self._cantidad -= cantidad
        
    def mostrar(self):
        return f"Titular:\n{self._titular.mostrar()}\nCantidad: {self._cantidad}€"
