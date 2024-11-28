class Persona:
    def __init__(self, nombre="", apellidos="", dni="", edad=0):
        self._nombre = ""
        self._apellidos = ""
        self._dni = ""
        self._edad = 0
        self.nombre = nombre
        self.apellidos = apellidos
        self.dni = dni
        self.edad = edad
        
    @property
    def nombre(self):
        return self._nombre
        
    @nombre.setter
    def nombre(self, valor):
        if not valor.strip():
            raise ValueError("El nombre no puede estar vacío")
        self._nombre = valor.upper()
        
    @property
    def apellidos(self):
        return self._apellidos
        
    @apellidos.setter
    def apellidos(self, valor):
        if not valor.strip():
            raise ValueError("Los apellidos no pueden estar vacíos")
        self._apellidos = valor.upper()
        
    @property
    def dni(self):
        return self._dni
        
    @dni.setter
    def dni(self, valor):
        if not valor.strip():
            raise ValueError("El DNI no puede estar vacío")
        self._dni = valor.upper()
        
    @property
    def edad(self):
        return self._edad
        
    @edad.setter
    def edad(self, valor):
        if not isinstance(valor, int) or valor < 0:
            raise ValueError("La edad debe ser un número entero positivo")
        self._edad = valor
        
    def mostrar(self):
        return f"Nombre: {self._nombre}\nApellidos: {self._apellidos}\nDNI: {self._dni}\nEdad: {self._edad}"
        
    def mayorDeEdad(self):
        return self._edad >= 18
