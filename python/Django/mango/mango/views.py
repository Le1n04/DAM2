from django.http import HttpResponse

def index(request):
    # Devuelve una respuesta HTML con un mensaje "Hola Mundo"
    return HttpResponse("<h1>Hola Mundo</h1>")
