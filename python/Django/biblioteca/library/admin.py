from django.contrib import admin
from .models import Author, Editorial, Book, Loan

admin.site.register(Author)
admin.site.register(Editorial)  # <-- Ahora con el nombre correcto
admin.site.register(Book)
admin.site.register(Loan)
