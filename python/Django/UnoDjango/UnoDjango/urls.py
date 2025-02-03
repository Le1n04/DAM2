from django.contrib import admin
from django.urls import path
from core import views  # Importa el módulo completo en lugar de funciones individuales

urlpatterns = [
    path('', views.index, name="index"),  # Usa nombres para evitar conflictos
    path('home/', views.home, name="home"),  # Cambia la URL a 'home/' en lugar de duplicar ''
    path('admin/', admin.site.urls),
]
