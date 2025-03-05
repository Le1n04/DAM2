from django.shortcuts import render, get_object_or_404, redirect
from .models import Author, Book, Editorial, Loan
from .forms import AuthorForm, BookForm, EditorialForm, LoanForm

# 📌 Menú principal
def menu(request):
    return render(request, 'library/menu.html')

# 📌 Lista de autores
def author_list(request):
    authors = Author.objects.all()
    return render(request, 'library/author_list.html', {'authors': authors})

# 📌 Detalle de un autor
def author_detail(request, id):
    author = get_object_or_404(Author, id=id)
    books = author.book_set.all()
    return render(request, 'library/author_detail.html', {'author': author, 'books': books})

# 📌 Crear autor
def author_create(request):
    if request.method == 'POST':
        form = AuthorForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('author_list')
    else:
        form = AuthorForm()
    return render(request, 'library/author_form.html', {'form': form})

def author_edit(request, id):
    author = get_object_or_404(Author, id=id)
    if request.method == 'POST':
        form = AuthorForm(request.POST, instance=author)
        if form.is_valid():
            form.save()
            return redirect('author_list')
    else:
        form = AuthorForm(instance=author)
    return render(request, 'library/author_form.html', {'form': form})

def author_delete(request, id):
    author = get_object_or_404(Author, id=id)
    if request.method == 'POST':
        author.delete()
        return redirect('author_list')
    return render(request, 'library/author_confirm_delete.html', {'author': author})

# 📌 Crear libro
def book_create(request):
    if request.method == 'POST':
        form = BookForm(request.POST)
        if form.is_valid():
            book = form.save(commit=False)
            book.editorial = form.cleaned_data['editorial']  # Guardar editorial
            book.save()
            form.save_m2m()  # Guardar autores
            return redirect('book_list')
    else:
        form = BookForm()
    
    return render(request, 'library/book_form.html', {'form': form})

def book_edit(request, id):
    book = get_object_or_404(Book, id=id)
    if request.method == 'POST':
        form = BookForm(request.POST, instance=book)
        if form.is_valid():
            form.save()
            return redirect('book_list')
    else:
        form = BookForm(instance=book)
    return render(request, 'library/book_form.html', {'form': form})

def book_delete(request, id):
    book = get_object_or_404(Book, id=id)
    if request.method == 'POST':
        book.delete()
        return redirect('book_list')
    return render(request, 'library/book_confirm_delete.html', {'book': book})


# 📌 Lista de libros
def book_list(request):
    books = Book.objects.all()
    return render(request, 'library/book_list.html', {'books': books})

# 📌 Detalle de un libro
def book_detail(request, id):
    book = get_object_or_404(Book, id=id)
    return render(request, 'library/book_detail.html', {'book': book})

# 📌 Lista de préstamos
def loan_list(request):
    loans = Loan.objects.all()
    return render(request, 'library/loan_list.html', {'loans': loans})

def loan_create(request):
    if request.method == 'POST':
        form = LoanForm(request.POST)
        if form.is_valid():
            loan = form.save(commit=False)
            loan.loan_date = timezone.now()  # No es necesario, Django lo maneja automáticamente
            loan.save()
            return redirect('loan_list')
    else:
        form = LoanForm()
    return render(request, 'library/loan_form.html', {'form': form})


# 📌 Lista de editoriales
def editorial_list(request):
    editoriales = Editorial.objects.all()
    return render(request, 'library/editorial_list.html', {'editoriales': editoriales})

# 📌 Crear editorial
def editorial_create(request):
    if request.method == 'POST':
        form = EditorialForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('editorial_list')
    else:
        form = EditorialForm()
    return render(request, 'library/editorial_form.html', {'form': form})

# 📌 Editar editorial
def editorial_edit(request, id):
    editorial = get_object_or_404(Editorial, id=id)
    if request.method == 'POST':
        form = EditorialForm(request.POST, instance=editorial)
        if form.is_valid():
            form.save()
            return redirect('editorial_list')
    else:
        form = EditorialForm(instance=editorial)
    return render(request, 'library/editorial_form.html', {'form': form})

# 📌 Eliminar editorial
def editorial_delete(request, id):
    editorial = get_object_or_404(Editorial, id=id)
    if request.method == 'POST':
        editorial.delete()
        return redirect('editorial_list')
    return render(request, 'library/editorial_confirm_delete.html', {'editorial': editorial})

from django.utils import timezone

def loan_return(request, id):
    loan = get_object_or_404(Loan, id=id)
    if request.method == 'POST':
        loan.return_date = timezone.now()  # Registrar la fecha de devolución
        loan.save()
        return redirect('loan_list')
    return render(request, 'library/loan_confirm_return.html', {'loan': loan})
