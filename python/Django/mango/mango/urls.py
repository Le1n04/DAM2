from django.contrib import admin
from django.urls import path
from . import views  # Correcto, importa el archivo views.py de la misma carpeta

urlpatterns = [
    path('', views.index),  # Ahora 'index' se ejecutará cuando vayas a la URL raíz
    path('admin/', admin.site.urls),
]

