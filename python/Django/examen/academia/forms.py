from django import forms
from .models import Estudiante

class EstudianteForm(forms.ModelForm):
    class Meta:
        model = Estudiante
        fields = ['nombre', 'correo']
    
    def clean_correo(self):
        correo = self.cleaned_data.get('correo')
        if '@' not in correo or '.' not in correo: # el @ y el punto del mail
            raise forms.ValidationError("El correo no es válido.")
        return correo
