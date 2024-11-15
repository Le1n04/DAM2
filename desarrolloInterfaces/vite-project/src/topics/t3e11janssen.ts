// definicion de la clase Persona
class Persona
{
    nombre: string;
    apellidos: string;
    edad: number;
    localidad: string;
    sexo: string;

    constructor(nombre: string, apellidos: string, edad: number, localidad: string, sexo: string)
    {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.localidad = localidad;
        this.sexo = sexo;
    }

}

// defnicion de la clase profesor que extiende de persona
class Profesor extends Persona
{
    centro: string;
    especialidad: string;
    departamento: string;

    constructor(nombre: string, apellidos: string, edad: number, localidad: string, sexo: string, centro: string, especialidad: string, departamento: string) {
        super(nombre, apellidos, edad, localidad, sexo);
        this.centro = centro;
        this.especialidad = especialidad;
        this.departamento = departamento;
    }

}

// definicino de la clase alumno que extiende de persona
class Alumno extends Persona
{
    centro: string;
    curso: string;
    asignaturas: string[];

    constructor(nombre: string, apellidos: string, edad: number, localidad: string, sexo: string, centro: string, curso: string, asignaturas: string[])
    {
        super(nombre, apellidos, edad, localidad, sexo);
        this.centro = centro;
        this.curso = curso;
        this.asignaturas = asignaturas;
    }

}

// crear un profesor
const profesor = new Profesor
(
    "Juan",
    "Pozo Merchan",
    45,
    "Mañaga",
    "Masculino",
    "Instituto de institutso",
    "SSII",
    "SUSPENSO"
);

// crear un alumno
const alumno = new Alumno
(
    "alumni",
    "alumnez alumnaz",
    16,
    "malaga",
    "f",
    "Instituto ededed",
    "4 ESO",
    ["Matematicas", "Lengua", "Ingles", "Nose"]
);

console.log(profesor);
console.log(alumno);

export{};