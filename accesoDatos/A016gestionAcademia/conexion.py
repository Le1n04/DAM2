import mysql.connector
from datetime import datetime

db_config = {
    'host': '158.179.223.81',
    'user': 'ejercicio',
    'password': '123',
    'database': 'ejercicio'
}

def conectar_db():
    try:
        connection = mysql.connector.connect(**db_config)
        print("Conexión exitosa a la base de datos.")
        return connection
    except mysql.connector.Error as err:
        print(f"Error: {err}")
        return None

def matricular_estudiante():
    id_estudiante = int(input("Ingrese el ID del estudiante: "))
    id_curso = int(input("Ingrese el ID del curso: "))
    fecha_inicio = input("Ingrese la fecha de inicio (YYYY-MM-DD): ")
    fecha_fin = input("Ingrese la fecha de finalización (YYYY-MM-DD): ")
    calificacion = input("Ingrese la calificación (opcional, presione Enter si no aplica): ")
    calificacion = float(calificacion) if calificacion else None

    connection = conectar_db()
    if connection:
        try:
            cursor = connection.cursor()
            sql = """
                INSERT INTO matriculas (id_estudiante, id_curso, fecha_matricula, fecha_inicio, fecha_fin, calificacion)
                VALUES (%s, %s, %s, %s, %s, %s)
            """
            fecha_matricula = datetime.now().strftime('%Y-%m-%d')
            valores = (id_estudiante, id_curso, fecha_matricula, fecha_inicio, fecha_fin, calificacion)
            cursor.execute(sql, valores)
            connection.commit()
            print("Estudiante matriculado correctamente.")
        except mysql.connector.Error as err:
            print(f"Error: {err}")
        finally:
            cursor.close()
            connection.close()

def listar_estudiantes_curso():
    id_curso = int(input("Ingrese el ID del curso: "))
    connection = conectar_db()
    if connection:
        try:
            cursor = connection.cursor()
            sql = """
                SELECT E.id_estudiante, E.nombre, E.correo
                FROM estudiantes E
                INNER JOIN matriculas M ON E.id_estudiante = M.id_estudiante
                WHERE M.id_curso = %s
            """
            cursor.execute(sql, (id_curso,))
            resultados = cursor.fetchall()
            print(f"Estudiantes en el curso {id_curso}:")
            for row in resultados:
                print(f"ID: {row[0]}, Nombre: {row[1]}, Correo: {row[2]}")
        except mysql.connector.Error as err:
            print(f"Error: {err}")
        finally:
            cursor.close()
            connection.close()

def baja_estudiante_curso():
    id_estudiante = int(input("Ingrese el ID del estudiante: "))
    id_curso = int(input("Ingrese el ID del curso: "))
    connection = conectar_db()
    if connection:
        try:
            cursor = connection.cursor()
            sql = "DELETE FROM matriculas WHERE id_estudiante = %s AND id_curso = %s"
            cursor.execute(sql, (id_estudiante, id_curso))
            connection.commit()
            print("Estudiante dado de baja correctamente del curso.")
        except mysql.connector.Error as err:
            print(f"Error: {err}")
        finally:
            cursor.close()
            connection.close()

def actualizar_precio_curso():
    id_curso = int(input("Ingrese el ID del curso: "))
    nuevo_precio = float(input("Ingrese el nuevo precio: "))
    connection = conectar_db()
    if connection:
        try:
            cursor = connection.cursor()
            sql = "UPDATE cursos SET precio_mensual = %s WHERE id_curso = %s"
            cursor.execute(sql, (nuevo_precio, id_curso))
            connection.commit()
            print("Precio del curso actualizado correctamente.")
        except mysql.connector.Error as err:
            print(f"Error: {err}")
        finally:
            cursor.close()
            connection.close()

def emitir_recibo():
    id_estudiante = int(input("Ingrese el ID del estudiante: "))
    connection = conectar_db()
    if connection:
        try:
            cursor = connection.cursor()
            sql = """
                SELECT E.nombre, C.nombre, C.precio_mensual, M.fecha_matricula
                FROM matriculas M
                INNER JOIN estudiantes E ON M.id_estudiante = E.id_estudiante
                INNER JOIN cursos C ON M.id_curso = C.id_curso
                WHERE E.id_estudiante = %s
            """
            cursor.execute(sql, (id_estudiante,))
            recibos = cursor.fetchall()

            print(f"Recibo para el estudiante ID: {id_estudiante}")
            for recibo in recibos:
                print(f"Estudiante: {recibo[0]}, Curso: {recibo[1]}, Precio: {recibo[2]}€, Fecha Matrícula: {recibo[3]}")
        except mysql.connector.Error as err:
            print(f"Error: {err}")
        finally:
            cursor.close()
            connection.close()

def menu():
    while True:
        print("\n--- Menú de Gestión de Cursos ---")
        print("1. Matricular estudiante en un curso")
        print("2. Listar estudiantes en un curso")
        print("3. Dar de baja a un estudiante de un curso")
        print("4. Actualizar precio de un curso")
        print("5. Emitir recibo de un estudiante")
        print("6. Salir")

        opcion = input("Seleccione una opción (1-6): ")

        if opcion == '1':
            matricular_estudiante()
        elif opcion == '2':
            listar_estudiantes_curso()
        elif opcion == '3':
            baja_estudiante_curso()
        elif opcion == '4':
            actualizar_precio_curso()
        elif opcion == '5':
            emitir_recibo()
        elif opcion == '6':
            print("Saliendo del sistema. ¡Adiós!")
            break
        else:
            print("Opción inválida. Por favor, intente nuevamente.")

if __name__ == "__main__":
    menu()