// Interfaz de dirección con campos opcionales
interface Direccion {
    calle?: string;
    codigoPostal?: string;
    localidad?: string;
    provincia?: string;
}

// Interfaz de persona con dirección opcional
interface Persona {
    nombre: string;
    apellidos: string;
    genero: string;
    telefono: string;
    direccion?: Direccion;
}

// Clase Jugador que implementa Persona
class Jugador implements Persona {
    nombre: string;
    apellidos: string;
    genero: string;
    telefono: string;
    direccion?: Direccion;
    categoria: number;
    constructor(nombre: string, apellidos: string, genero: string, telefono: string, direccion: Direccion, categoria: number) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.telefono = telefono;
        this.direccion = direccion;
        this.categoria = categoria;
    }
}

// Clase Arbitro que implementa Persona
class Arbitro implements Persona {
    nombre: string;
    apellidos: string;
    genero: string;
    telefono: string;
    direccion?: Direccion;
    numeroColegiado: string;
    constructor(nombre: string, apellidos: string, genero: string, telefono: string, direccion: Direccion, numeroColegiado: string) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.telefono = telefono;
        this.direccion = direccion;
        this.numeroColegiado = numeroColegiado;
    }
}

// Clase Equipo con dos jugadores
class Equipo {
    jugador1: Jugador;
    jugador2: Jugador;
    constructor(jugador1: Jugador, jugador2: Jugador) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }
    getNombreEquipo(): string {
        return `${this.jugador1.apellidos}-${this.jugador2.apellidos}`;
    }
}

// Clase JuegoSet para los sets de un partido
class JuegoSet {
    juegosEquipo1: number;
    juegosEquipo2: number;
    constructor(juegosEquipo1: number, juegosEquipo2: number) {
        this.juegosEquipo1 = juegosEquipo1;
        this.juegosEquipo2 = juegosEquipo2;
    }
}

// Clase Partido que acepta partidos sin rival
class Partido {
    equipo1: Equipo;
    equipo2?: Equipo; // ahora equipo2 es opcional
    fecha: Date;
    hora: string;
    recinto: string;
    pista: string;
    arbitro: Arbitro;
    sets: JuegoSet[];
    constructor(equipo1: Equipo, equipo2: Equipo | undefined, fecha: Date, hora: string, recinto: string, pista: string, arbitro: Arbitro) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.fecha = fecha;
        this.hora = hora;
        this.recinto = recinto;
        this.pista = pista;
        this.arbitro = arbitro;
        this.sets = [];
    }
    // Método para añadir un set
    addSet(juegosEquipo1: number, juegosEquipo2: number) {
        this.sets.push(new JuegoSet(juegosEquipo1, juegosEquipo2));
    }
    // Método para obtener el resultado
    getResultado(): string {
        if (!this.equipo2) return `${this.equipo1.getNombreEquipo()} pasa sin jugar (bye).`;
        
        let setsEquipo1 = 0;
        let setsEquipo2 = 0;
        this.sets.forEach(set => {
            if (set.juegosEquipo1 > set.juegosEquipo2) {
                setsEquipo1++;
            } else if (set.juegosEquipo2 > set.juegosEquipo1) {
                setsEquipo2++;
            }
        });
        const ganador = setsEquipo1 > setsEquipo2 ? this.equipo1.getNombreEquipo() : this.equipo2.getNombreEquipo();
        return `${ganador} ha ganado por ${Math.max(setsEquipo1, setsEquipo2)} sets a ${Math.min(setsEquipo1, setsEquipo2)}.`;
    }
}

// Clase Calendario para almacenar y mostrar partidos
class Calendario {
    partidos: Partido[];
    constructor() {
        this.partidos = [];
    }
    addPartido(partido: Partido) {
        this.partidos.push(partido);
    }
    mostrarCalendario() {
        this.partidos.forEach(partido => {
            console.log(`Partido entre ${partido.equipo1.getNombreEquipo()} y ${partido.equipo2?.getNombreEquipo() ?? "bye"} el ${partido.fecha.toLocaleDateString()} a las ${partido.hora} en ${partido.recinto}, pista ${partido.pista}`);
            console.log(`Árbitro: ${partido.arbitro.nombre} ${partido.arbitro.apellidos}`);
            console.log(`Dirección del árbitro: ${partido.arbitro.direccion?.calle ?? "No disponible"}, ${partido.arbitro.direccion?.localidad ?? "No disponible"}, ${partido.arbitro.direccion?.provincia ?? "No disponible"}\n`);
            console.log(partido.getResultado());
        });
    }
}

// Crear instancias y probar el nuevo código

// Crear direcciones opcionales
const direccion1: Direccion = { calle: "Calle 1", codigoPostal: "28001", localidad: "Madrid", provincia: "Madrid" };
const direccion2: Direccion = { calle: "Calle 2", codigoPostal: "28002", localidad: "Madrid", provincia: "Madrid" };

// Crear jugadores
const jugador1 = new Jugador("Juan", "López", "M", "123456789", direccion1, 3);
const jugador2 = new Jugador("Pedro", "Martín", "M", "987654321", direccion2, 3);
const jugador3 = new Jugador("Carlos", "Carrasco", "M", "111222333", direccion1, 4);
const jugador4 = new Jugador("Miguel", "Beltrán", "M", "444555666", direccion2, 4);

// Crear equipos
const equipo1 = new Equipo(jugador1, jugador2);
const equipo2 = new Equipo(jugador3, jugador4);

// Crear árbitros
const arbitro1 = new Arbitro("Laura", "Gómez", "F", "555444333", direccion1, "A123");
const arbitro2 = new Arbitro("Ana", "Pérez", "F", "666555444", direccion2, "B456");

// Crear partidos, uno sin rival
const partido1 = new Partido(equipo1, equipo2, new Date("2023-06-15"), "10:00", "Recinto 1", "Pista 1", arbitro1);
partido1.addSet(6, 4);
partido1.addSet(3, 6);
partido1.addSet(7, 5);

const partido2 = new Partido(equipo1, undefined, new Date("2023-06-20"), "12:00", "Recinto 2", "Pista 2", arbitro2); // Partido con "bye"

// Crear calendario y mostrar partidos
const calendario = new Calendario();
calendario.addPartido(partido1);
calendario.addPartido(partido2);
calendario.mostrarCalendario();

export{};