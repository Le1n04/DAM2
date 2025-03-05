from django.shortcuts import render, redirect, get_object_or_404
from django.contrib import messages
from .models import Estudiante, Inscripcion
from .forms import EstudianteForm

def lista_estudiantes(request):
    estudiantes = Estudiante.objects.all()
    return render(request, 'academia/lista_estudiantes.html', {'estudiantes': estudiantes})

def lista_inscripciones(request):
    inscripciones = Inscripcion.objects.all()
    return render(request, 'academia/lista_inscripciones.html', {'inscripciones': inscripciones})

def nuevo_estudiante(request):
    if request.method == 'POST':
        form = EstudianteForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('lista_estudiantes')
    else:
        form = EstudianteForm()
    return render(request, 'academia/formulario_estudiante.html', {'form': form})

def editar_estudiante(request, id):
    estudiante = get_object_or_404(Estudiante, id=id)
    if request.method == 'POST':
        form = EstudianteForm(request.POST, instance=estudiante)
        if form.is_valid():
            form.save()
            return redirect('lista_estudiantes')
    else:
        form = EstudianteForm(instance=estudiante)
    return render(request, 'academia/formulario_estudiante.html', {'form': form})

def borrar_estudiante(request, id):
    estudiante = get_object_or_404(Estudiante, id=id)
    if request.method == 'POST':
        estudiante.delete()
        return redirect('lista_estudiantes')
    return render(request, 'academia/confirmar_borrado.html', {'estudiante': estudiante})

# la pongo para que al entrar en la url cruda no de error 404, para evitar confusiones
def index(request):
    return render(request, 'academia/index.html')
