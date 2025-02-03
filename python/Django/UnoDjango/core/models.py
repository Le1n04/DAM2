from django.db import models

# Create your models here.

class Author (models.Model):
    name = models.CharField(verbose_name='Nombre',
    max_length=100,
    default=''
    )
    last_name=models.CharField(verbose_name='Apellido', max_length=150, default='')
    age= models.PositiveSmallIntegerField(verbose_name='Edad', default=0)

    def __str__(self):
        return f'{self.name} {self.last_name}'


class Book (models.Model):
    title= models.CharField(verbose_name='Titulo', max_length=255, unique=True)
    cod=models.CharField(verbose_name='Codigo', max_length=15, unique=True)
    author=models.ManyToManyField(Author)    
    
    class Meta:
        verbose_name='Libro'
        verbose_name_plural='Libros'

    def __str__(self):
        return self.title

    Author.objects.all()