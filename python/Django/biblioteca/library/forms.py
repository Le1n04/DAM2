from django import forms
from .models import Author, Book, Editorial, Loan

class AuthorForm(forms.ModelForm):
    class Meta:
        model = Author
        fields = ['first_name', 'last_name', 'birth_date']

class BookForm(forms.ModelForm):
    editorial = forms.ModelChoiceField(queryset=Editorial.objects.all(), empty_label="Selecciona una editorial")

    class Meta:
        model = Book
        fields = ['title', 'publication_date', 'editorial', 'authors']

class LoanForm(forms.ModelForm):
    class Meta:
        model = Loan
        fields = ['book', 'borrower']  # Excluye loan_date porque se genera automáticamente

class EditorialForm(forms.ModelForm):
    class Meta:
        model = Editorial
        fields = ['name']

