from abc import ABC, abstractmethod

class Empleado(ABC):
    def __init__(self, nombre, id_empleado):
        self.nombre = nombre
        self.id_empleado = id_empleado

    @abstractmethod
    def calcular_salario(self):
        pass

class Asalariado(Empleado):
    def __init__(self, nombre, id_empleado, salario_mensual):
        super().__init__(nombre, id_empleado)
        self.salario_mensual = salario_mensual

    def calcular_salario(self):
        return self.salario_mensual

class PorHora(Empleado):
    def __init__(self, nombre, id_empleado, tarifa_hora, horas_trabajadas):
        super().__init__(nombre, id_empleado)
        self.tarifa_hora = tarifa_hora
        self.horas_trabajadas = min(horas_trabajadas, 160)  # Máximo 160 horas

    def calcular_salario(self):
        return self.tarifa_hora * self.horas_trabajadas

class Comisionista(Empleado):
    def __init__(self, nombre, id_empleado, salario_base, ventas, porcentaje_comision):
        super().__init__(nombre, id_empleado)
        self.salario_base = salario_base
        self.ventas = ventas
        self.porcentaje_comision = porcentaje_comision

    def calcular_salario(self):
        return self.salario_base + (self.ventas * self.porcentaje_comision)

# Ejemplo de uso
if __name__ == "__main__":
    empleados = [
        Asalariado("Juan Pérez", "001", 2000),
        PorHora("María García", "002", 15, 160),
        Comisionista("Carlos López", "003", 1000, 10000, 0.05)
    ]

    total_salarios = 0
    for empleado in empleados:
        salario = empleado.calcular_salario()
        total_salarios += salario
        print(f"{empleado.__class__.__name__}: {empleado.nombre}")
        print(f"ID: {empleado.id_empleado}")
        print(f"Salario: {salario}€")
        print("-" * 40)

    print(f"Total salarios de la empresa: {total_salarios}€")