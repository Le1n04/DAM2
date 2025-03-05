from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name='index'),  # pagina de inicio para que no salte un 404
    path('estudiantes/', views.lista_estudiantes, name='lista_estudiantes'),
    path('inscripcion/', views.lista_inscripciones, name='lista_inscripciones'),
    path('estudiantes/nuevo/', views.nuevo_estudiante, name='nuevo_estudiante'),
    path('estudiantes/<int:id>/editar/', views.editar_estudiante, name='editar_estudiante'),
    path('estudiantes/<int:id>/borrar/', views.borrar_estudiante, name='borrar_estudiante'),
]
