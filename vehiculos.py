class Vehiculo:
    def __init__(self, modelo, año):
        self.modelo = modelo
        self.año = año

    def calcular_consumo(self, kilometros):
        pass

class Coche(Vehiculo):
    def calcular_consumo(self, kilometros):
        return (kilometros / 100) * 5

class Camion(Vehiculo):
    def __init__(self, modelo, año, toneladas_carga=0):
        super().__init__(modelo, año)
        self.toneladas_carga = toneladas_carga

    def calcular_consumo(self, kilometros):
        consumo_base = (kilometros / 100) * 20
        incremento = consumo_base * (self.toneladas_carga * 0.1)
        return consumo_base + incremento

class Moto(Vehiculo):
    def calcular_consumo(self, kilometros):
        return (kilometros / 100) * 3

# Ejemplo de uso
if __name__ == "__main__":
    vehiculos = [
        Coche("Toyota Corolla", 2020),
        Camion("Volvo FH", 2019, toneladas_carga=2),
        Moto("Honda CBR", 2021)
    ]

    kilometros = 200
    for vehiculo in vehiculos:
        consumo = vehiculo.calcular_consumo(kilometros)
        print(f"{vehiculo.__class__.__name__}: {vehiculo.modelo}")
        print(f"Consumo para {kilometros}km: {consumo:.2f} litros")
        print("-" * 40)
