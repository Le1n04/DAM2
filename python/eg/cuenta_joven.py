from cuenta import Cuenta
from persona import Persona

class CuentaJoven(Cuenta):
    def __init__(self, titular: Persona, cantidad=0.0, bonificacion=0):
        if titular.edad >= 25:
            raise ValueError("El titular debe ser menor de 25 años")
        super().__init__(titular, cantidad)
        self.bonificacion = bonificacion
        
    @property
    def bonificacion(self):
        return self._bonificacion
        
    @bonificacion.setter
    def bonificacion(self, valor):
        if not isinstance(valor, (int, float)) or valor < 0 or valor > 100:
            raise ValueError("La bonificación debe ser un número entre 0 y 100")
        self._bonificacion = valor
        
    def mostrar(self):
        return f"{super().mostrar()}\nBonificación: {self._bonificacion}%"
