#clase base material de la cual saldran el resto
class Material:
    def __init__(self, id, titulo, autor, anio_publicacion):
        self.id = id
        self.titulo = titulo
        self.autor = autor
        self.anio_publicacion = anio_publicacion

#clase libro que deriva de material
class Libro(Material):
    generos_validos = ["Ficción", "No Ficción", "Terror", "Ciencia"]

    def __init__(self, id, titulo, autor, anio_publicacion, genero, num_paginas):
        super().__init__(id, titulo, autor, anio_publicacion)
        if genero not in Libro.generos_validos:
            raise ValueError(f"el genero no es valido. debe ser: {', '.join(Libro.generos_validos)}")
        if num_paginas <= 0:
            raise ValueError("el numero de paginas es invalido, debe ser mayor que 0.")
        self.genero = genero
        self.num_paginas = num_paginas

# clase revista que deriva de material
class Revista(Material):
    meses_validos = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]

    def __init__(self, id, titulo, autor, anio_publicacion, num_edicion, mes_publicacion):
        super().__init__(id, titulo, autor, anio_publicacion)
        if mes_publicacion not in Revista.meses_validos:
            raise ValueError(f"el mes no es valido, debe ser: {', '.join(Revista.meses_validos)}")
        self.num_edicion = num_edicion
        self.mes_publicacion = mes_publicacion

#diccionario para guardar los materiales
materiales = {}

# para que no tengamos duplicados
ids_existentes = []

# para generar estadisticas

def agregar_material():
    tipo= input("que tipo de material quieres añadir? libro o revista? ").strip().lower() # en cada input se hace un strip() por si el usuario mete un espacio por error
    if tipo == "libro" or tipo == "revista": # miro que sea el tipo para no pedir cosas sin necesidad
        id = input("ingresa el id del material: ").strip()                                    # lower() para que no sea case sensitive

        if id in ids_existentes:
            print("error, ese material ya existe...")
            return
        
        titulo = input("ingresa el titulo: ").strip()
        autor = input("ingresa el autor: ").strip()
        anio_publicacion = int(input("ingresa el año de publicacion: ").strip())

        if tipo == "libro":
            genero = input(f"selecciona uno de los generos siguientes: {', '.join(Libro.generos_validos)}: ").strip() # muestro todos los generos separados por ,
            num_paginas = int(input("dime el numero de paginas: ").strip())
            try:
                material = Libro(id, titulo, autor, anio_publicacion, genero, num_paginas) # el genero tiene que estar bien escrito
            except ValueError as ve:
                print(f"error, {ve}")
                return
        elif tipo == "revista":
            num_edicion = int(input("cual es el numero de edicion: ").strip())
            mes_publicacion = input(f"Selecciona un mes de publicacion: {', '.join(Revista.meses_validos)}: ").strip() # muestro todos los meses posibles y tienen que estar bien escritos
            try:
                material = Revista(id, titulo, autor, anio_publicacion, num_edicion, mes_publicacion)
            except ValueError as e:
                print(f"Error: {e}")
                return
        else:
            print("error, tipo invalido...")
            return
        
        materiales[id] = material
        ids_existentes.append(id)
        print("material agregado con exito.")
    else:
        print("ese tipo no existe...")

def listar_materiales():
    if not materiales:
        print("no hay materiales en la lista.")
        return
    for material in materiales.values():
        print(f"id: {material.id}, titulo: {material.titulo}, autor: {material.autor}, año: {material.anio_publicacion}")
        if isinstance(material, Libro): # si es libro
            print(f"|\no===> genero: {material.genero}, {material.num_paginas} paginas")
        elif isinstance(material, Revista): # si es revista
            print(f"|\no===> edicion: {material.num_edicion}, mes: {material.mes_publicacion}")

def buscar_material():
    id = input("dime el id del material: ").strip()
    material = materiales.get(id)
    if material:
        print(f"id: {material.id}, titulo: {material.titulo}, autor: {material.autor}, año: {material.anio_publicacion}, ")
        if isinstance(material, Libro): # si es libro
            print(f"|\no===> genero: {material.genero}, {material.num_paginas} paginas\n")
        elif isinstance(material, Revista): # si es revista
            print(f"|\no===> edicion: {material.num_edicion}, mes: {material.mes_publicacion}\n")
    else:
        print(f"no se ha encontrado ningun material con la id {id}")

def eliminar_material():
    id = input("ingresa la id que quieres eliminar: ").strip()
    if id in materiales:
        del materiales[id]
        ids_existentes.remove(id)
        print(f"el material con ID {id} ha sido eliminado")
    else:
        print(f"no se ha encontado ningun material con la ID {id}")    

def generar_estadisticas():
    total_materiales = len(materiales) # un len para saber cuantos materiales hay
    num_libros = sum(1 for m in materiales.values() if isinstance(m, Libro)) # hago una suma de lo que se encuentre en materiales y sea libros
    num_revistas = total_materiales - num_libros # restando los libros a las revistas nos da el otro tipo
    promedio_paginas = sum(m.num_paginas for m in materiales.values() if isinstance(m, Libro)) / num_libros if num_libros > 0 else 0 # hago una media teniendo cuidado en la division entre 0

    print(f"total de materiales registrados: {total_materiales}")
    print(f"numero de libros: {num_libros}")
    print(f"numero de revistas: {num_revistas}")
    print(f"prmomedio de paginas de los libros: {promedio_paginas:.2f}") # .2f para que solo nos cuente dos decimales float de la media

def menu():

    while True:
        print(f"\n-=- MENU -=-\n1. Agregar materiales.\n2. Listar materiales\n3. Buscar material por ID")
        print(f"4. Eliminar material.\n5. Generar estadisticas\n9. Salir")

        opcion = input("\nSeleccione una opcion: ").strip()
        if (opcion == "1"):
            agregar_material()
        elif (opcion =="2"):
            listar_materiales()
        elif (opcion =="3"):
            buscar_material()
        elif (opcion == "4"):
            eliminar_material()
        elif (opcion == "5"):
            generar_estadisticas()
        elif (opcion == "9"):
            print("adios...")
            break
        else:
            print(f"la opcion {opcion} no existe...")

if __name__ == "__main__":
    menu()