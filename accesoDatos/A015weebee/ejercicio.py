from peewee import *

# Conexión a la base de datos
mysql_db = MySQLDatabase(
    'inventario_maquinas',
    user='root',
    password='root',
    host='localhost',
    port=3306
)

mysql_db.connect()


# Definición de las clases que mapean las tablas
class BaseModel(Model):
    class Meta:
        database = mysql_db

class Ubicacion(BaseModel):
    cod = AutoField()
    lugar = CharField(max_length=255)

class Maquina(BaseModel):
    codigo = AutoField()
    modelo = CharField(max_length=255)
    descripcion = TextField(null=True)
    ram = CharField(max_length=50, null=True)
    hd = CharField(max_length=50, null=True)
    cpu = CharField(max_length=50, null=True)
    cod_ubicacion = ForeignKeyField(Ubicacion, backref='maquinas', null=True)

# Creación de las tablas
mysql_db.create_tables([Ubicacion, Maquina])

# Funciones CRUD

# 1. Alta de máquinas
def alta_maquina(modelo, descripcion, ram, hd, cpu, cod_ubicacion):
    try:
        ubicacion = Ubicacion.get_by_id(cod_ubicacion)
        Maquina.create(
            modelo=modelo,
            descripcion=descripcion,
            ram=ram,
            hd=hd,
            cpu=cpu,
            cod_ubicacion=ubicacion
        )
        print("Máquina añadida con éxito.")
    except Exception as e:
        print(f"Error al añadir la máquina: {e}")

# 2. Consulta de máquina por modelo
def consultar_maquina_por_modelo(modelo):
    try:
        maquinas = Maquina.select().where(Maquina.modelo == modelo)
        for maquina in maquinas:
            print(f"Código: {maquina.codigo}, Modelo: {maquina.modelo}, RAM: {maquina.ram}, HD: {maquina.hd}, CPU: {maquina.cpu}, Ubicación: {maquina.cod_ubicacion.lugar}")
    except Exception as e:
        print(f"Error en la consulta: {e}")

# 3. Actualización de RAM, HD o ubicación
def actualizar_maquina(codigo, ram=None, hd=None, cod_ubicacion=None):
    try:
        maquina = Maquina.get_by_id(codigo)
        if ram:
            maquina.ram = ram
        if hd:
            maquina.hd = hd
        if cod_ubicacion:
            maquina.cod_ubicacion = Ubicacion.get_by_id(cod_ubicacion)
        maquina.save()
        print("Máquina actualizada con éxito.")
    except Exception as e:
        print(f"Error al actualizar la máquina: {e}")

# 4. Listado de máquinas por ubicación
def listar_maquinas_por_ubicacion(cod_ubicacion):
    try:
        ubicacion = Ubicacion.get_by_id(cod_ubicacion)
        maquinas = Maquina.select().where(Maquina.cod_ubicacion == ubicacion)
        print(f"Listado de máquinas en {ubicacion.lugar}:")
        for maquina in maquinas:
            print(f"Código: {maquina.codigo}, Modelo: {maquina.modelo}, RAM: {maquina.ram}, HD: {maquina.hd}, CPU: {maquina.cpu}")
    except Exception as e:
        print(f"Error al listar las máquinas: {e}")

# Ejemplo de uso
def main():
    # Crear ubicaciones (si no existen)
    if not Ubicacion.select().exists():
        for lugar in ["Almacén 1", "Almacén 2", "Almacén 3"]:
            Ubicacion.create(lugar=lugar)

    # Añadir máquinas
    alta_maquina("ModeloX", "Descripción de prueba", "16GB", "1TB", "Intel i7", 1)

    # Consultar máquina
    consultar_maquina_por_modelo("ModeloX")

    # Actualizar máquina
    actualizar_maquina(1, ram="32GB", hd="2TB", cod_ubicacion=2)

    # Listar máquinas por ubicación
    listar_maquinas_por_ubicacion(2)

if __name__ == "__main__":
    main()