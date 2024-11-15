interface Expediente
{
    curso: string;
    notaMedia: number;
}

export class Estudiante
{


    constructor
    (
        public nombre: string,
        public apellidos: string,
        public localidad: string,
        public expediente: Expediente,
        private edad: number,
        public sexo?: string
    )
    {
        this.nombre = nombre;
        this.apellidos = apellidos
        this.localidad = localidad;
        this.expediente = expediente;
        this.edad = edad;
        this.sexo = sexo;
    };

}

class Instituto {
    centro: string;
    localidad: string;
    estudiantes: Estudiante[];
  
    constructor(centro: string, localidad: string, estudiantes: Estudiante[]) {
      this.centro = centro;
      this.localidad = localidad;
      this.estudiantes = estudiantes;
    }
}

const pessi = new Estudiante("Pessi", "pesi", "pesi town", {curso: "Primero", notaMedia: 5}, 36, "si");
const penaldo = new Estudiante("penaldo", "penalti", "porchugal", {curso: "Segundo", notaMedia: 3}, 39, "no");
const instituto = new Instituto("El templo del futbol", "Nose", [pessi, penaldo]);

console.table(pessi);
console.table(penaldo);
console.table(instituto);

export {};