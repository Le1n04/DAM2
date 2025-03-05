from django.db import models

class Curso(models.Model):
    nombre = models.CharField(max_length=150) # nombre del curso
    descripcion = models.TextField() #descripcion del curso

    def __str__(self):
        return self.nombre

class Estudiante(models.Model):
    nombre = models.CharField(max_length=100) #nombre del estudiante 
    correo = models.EmailField(unique=True) # email del estudiante, unico 

    def __str__(self):
        return self.nombre

class Inscripcion(models.Model):
    estudiante = models.ForeignKey(Estudiante, on_delete=models.CASCADE) # estudiante de la inscripciono
    curso = models.ForeignKey(Curso, on_delete=models.CASCADE) # curso en el que esta inscrito
    fecha_inscripcion = models.DateField(auto_now_add=True) # y la fecha en la que se ha inscrito
    
    def __str__(self):
        return f"{self.estudiante} - {self.curso}"