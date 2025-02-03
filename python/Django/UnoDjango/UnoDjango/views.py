from django.http import HttpResponse #Necesario para poder responde al cliente
from django.shortcuts import render
from core.models import Author # Importamos el modelo Author para poder usarlo en la vista

def index (request): # El request captura las peticiones de los clientes
    return HttpResponse ("<h1>hola mundo</h1>")

def home (request): # Pinta una página con render, también hay que darlo de alta en urls.py
    author=Author.objects.all()
    return render(request,'unodjango/index.html',{'author':author})
