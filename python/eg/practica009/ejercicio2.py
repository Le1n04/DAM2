class Habitacion:
    def __init__(self, numero):
        self.numero = numero
        self.precio_base = 0

    def calcular_precio(self, noches):
        return self.precio_base * noches

class Individual(Habitacion):
    def __init__(self, numero):
        super().__init__(numero)
        self.precio_base = 50

class Doble(Habitacion):
    def __init__(self, numero, con_desayuno=False):
        super().__init__(numero)
        self.precio_base = 75
        self.con_desayuno = con_desayuno

    def calcular_precio(self, noches):
        precio = super().calcular_precio(noches)
        if self.con_desayuno:
            precio += 10 * noches
        return precio

class Suite(Habitacion):
    def __init__(self, numero):
        super().__init__(numero)
        self.precio_base = 150

    def calcular_precio(self, noches):
        precio = super().calcular_precio(noches)
        if noches > 3:
            precio *= 0.90  # 10% descuento
        return precio

if __name__ == "__main__":
    habitaciones = [
        Individual(101),
        Doble(201, con_desayuno=True),
        Suite(301)
    ]

    noches = 4
    for hab in habitaciones:
        precio = hab.calcular_precio(noches)
        print("-" * 40)
        print(f"| Habitación {hab.__class__.__name__} {hab.numero}", " " * 10           , "|")
        print(f"| Precio para {noches} noches: {precio}€", " " * 7, "|")
        print("-" * 40)