from django.http import HttpResponse #Necesario para poder responde al cliente
from django.shortcuts import render 
def index (request): # El request captura las peticiones de los clientes
    return HttpResponse ("<h1>hola mundo</h1>")

def home (request): # Pinta una página con render, también hay que darlo de alta en urls.py
    return render(request,'unodjango/index.html')
