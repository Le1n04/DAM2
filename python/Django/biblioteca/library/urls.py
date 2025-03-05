from django.urls import path
from . import views

urlpatterns = [
    path('', views.menu, name='menu'),

    path('authors/', views.author_list, name='author_list'),
    path('authors/<int:id>/', views.author_detail, name='author_detail'),
    path('authors/new/', views.author_create, name='author_create'),
    path('authors/edit/<int:id>/', views.author_edit, name='author_edit'),
    path('authors/delete/<int:id>/', views.author_delete, name='author_delete'),

    path('books/', views.book_list, name='book_list'),
    path('books/<int:id>/', views.book_detail, name='book_detail'),
    path('books/new/', views.book_create, name='book_create'),
    path('books/edit/<int:id>/', views.book_edit, name='book_edit'),
    path('books/delete/<int:id>/', views.book_delete, name='book_delete'),

    path('editoriales/', views.editorial_list, name='editorial_list'),
    path('editoriales/new/', views.editorial_create, name='editorial_create'),
    path('editoriales/edit/<int:id>/', views.editorial_edit, name='editorial_edit'),
    path('editoriales/delete/<int:id>/', views.editorial_delete, name='editorial_delete'),

    path('loans/', views.loan_list, name='loan_list'),
    path('loans/new/', views.loan_create, name='loan_create'),
    path('loans/return/<int:id>/', views.loan_return, name='loan_return'),

]
