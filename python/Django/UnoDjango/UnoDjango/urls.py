from django.contrib import admin
from django.urls import path
from . import views # Importo nuestro módulo views

urlpatterns = [
    path('', views.index), #indicamos que la vista la queremos mostrar en esa ruta.
    path('otramas/', views.home), #nueva vista
    path('admin/', admin.site.urls),
]