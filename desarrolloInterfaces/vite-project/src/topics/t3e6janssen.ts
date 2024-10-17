interface Alumno
{
    nombre: string,
    apellidos: string,
    year: number,
    nacionalidad : string,
    matricula : Matricula
}

interface Matricula
{
    modulos : string[],
    ciclo : string,
    centro : Centro
}

interface Centro
{
    centro : string,
    localidad : string,
    codigo : number,
    telefono : string
}

const daniel: Alumno = 
{
    nombre: "daniel",
    apellidos: "janssen",
    year: 2002,
    nacionalidad :"españa",
    matricula :
    {
        modulos: ["modulo1", "modulo2"],
        ciclo: "dam",
        centro: 
        {
            centro: "playamar",
            localidad: "torremolinos",
            codigo: 1,
            telefono: "+3412345678"
        },
    }
}

const josue: Alumno = 
{
    nombre: "josue",
    apellidos: "gil",
    year: 2001,
    nacionalidad :"españAA",
    matricula :
    {
        modulos: ["modulo3", "modulo4"],
        ciclo: "daw",
        centro: 
        {
            centro: "playario",
            localidad: "churriana",
            codigo: 2,
            telefono: "+9912345678"
        },
    }
}

console.table(daniel);
console.table(josue);

const { nombre, apellidos, matricula: { ciclo, centro: { centro } } } = daniel;

console.log(`Alumno: ${nombre} ${apellidos}`);
console.log(`Ciclo : ${ciclo}`);
console.log(`Centro: ${centro}`);

const { nombre: nombre2, apellidos: apellidos2, matricula: { ciclo: cicloFormativo2, centro: { centro: centro2 } } } = josue;

console.log(`Alumno: ${nombre2} ${apellidos2}`);
console.log(`Ciclo Formativo: ${cicloFormativo2}`);
console.log(`Centro: ${centro2}`);

export{};