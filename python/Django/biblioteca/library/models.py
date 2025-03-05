from django.db import models

class Author(models.Model):
    first_name = models.CharField(max_length=100)
    last_name = models.CharField(max_length=100)
    birth_date = models.DateField()

    def __str__(self):
        return f"{self.first_name} {self.last_name}"

class Editorial(models.Model):
    name = models.CharField(max_length=200)

    def __str__(self):
        return self.name

class Book(models.Model):
    title = models.CharField(max_length=200)
    publication_date = models.DateField()
    editorial = models.ForeignKey(Editorial, on_delete=models.CASCADE)  # Asegurar que se llame "editorial"
    authors = models.ManyToManyField('Author')

    def __str__(self):
        return self.title


class Loan(models.Model):
    book = models.ForeignKey(Book, on_delete=models.CASCADE)
    borrower = models.CharField(max_length=255)
    loan_date = models.DateField(auto_now_add=True)  # Se genera automáticamente
    return_date = models.DateField(null=True, blank=True)
