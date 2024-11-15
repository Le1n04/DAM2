import { Alumno, Ensenanza, Instituto, Direccion, matricularAlumno, mostrarAlumnos, mostrarDireccionInstituto } from './t9modulo-instituto';

const ensenanza1: Ensenanza =
{
    nivel: "Bachillerato",
    obligatoria: false,
    familiaProfesional: "",
    modalidad: "Mañana",
};

const ensenanza2: Ensenanza =
{
    nivel: "Formación Profesional",
    obligatoria: false,
    familiaProfesional: "Informática y Comunicaciones",
    modalidad: "Tarde",
};

const ensenanza3: Ensenanza =
{
    nivel: "ESO",
    obligatoria: true,
    familiaProfesional: "",
    modalidad: "Mañana",
};

const direccion1: Direccion =
{
    calle: "Calle El Callado",
    cp: "12354",
    localidad: "Malaga",
    provincia: "Malaga",
};

const direccion2: Direccion =
{
    calle: "Avenida Avellana Avellaneda",
    cp: "76543",
    localidad: "Barcelona",
    provincia: "Barcelona",
};

const alumno1: Alumno =
{
    nombre: "Juan Pozo",
    edad: 16,
    sexo: "M",
    ensenanza: ensenanza1,
    direccion: direccion1,
};

const alumno2: Alumno =
{
    nombre: "Ana Mena",
    edad: 18,
    sexo: "F",
    ensenanza: ensenanza2,
    direccion: direccion2,
};

const instituto: Instituto =
{
    nombre: "Instituto Central",
    director: "Directorazo",
    telefono: "123456789",
    correo: "contacto@insti.com",
    direccion: {
        calle: "Gran Calle",
        cp: "12354",
        localidad: "Madrid",
        provincia: "Madrid",
    },
    alumnos: [],
};

matricularAlumno(instituto, alumno1);
matricularAlumno(instituto, alumno2);

mostrarAlumnos(instituto);

mostrarDireccionInstituto(instituto);

export{}
