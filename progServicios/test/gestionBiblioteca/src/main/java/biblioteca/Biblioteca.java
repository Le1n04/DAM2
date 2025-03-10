package biblioteca;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class Biblioteca {
 private static final String DATABASE_NAME = "biblioteca";
 private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
 private static final MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

 private static final MongoCollection<Document> libros = database.getCollection("libros");
 private static final MongoCollection<Document> autores = database.getCollection("autores");
 private static final MongoCollection<Document> socios = database.getCollection("socios");


 

 public static void returnLibro(ObjectId libroId, ObjectId socioId) {
     libros.updateOne(new Document("_id", libroId), new Document("$set", new Document("disponible", true)));
     socios.updateOne(new Document("_id", socioId), new Document("$pull", new Document("libros_prestados", libroId)));
 }

 public static void anadirLibro(String titulo, String isbn, ObjectId autorId, String genero, boolean disponible) {
     Document libro = new Document("titulo", titulo)
             .append("isbn", isbn)
             .append("autor_id", autorId)
             .append("genero", genero)
             .append("disponible", disponible);
     libros.insertOne(libro);
 }



 public static void prestarLibroSocio(ObjectId libroId, ObjectId socioId) {
     Document libro = libros.find(new Document("_id", libroId)).first();
     if (libro != null && libro.getBoolean("disponible")) {
         libros.updateOne(new Document("_id", libroId), new Document("$set", new Document("disponible", false)));
         socios.updateOne(new Document("_id", socioId), new Document("$push", new Document("libros_prestados", libroId)));
     }
 }


 public static void mostrarLibrosDisponibles() {
     MongoCursor<Document> cursor = libros.find(new Document("disponible", true)).iterator();
     try {
         while (cursor.hasNext()) {
             System.out.println(cursor.next().toJson());
         }
     } finally {
         cursor.close();
     }
 }
 
 public static void anadirSocio(String nombre, String direccion) {
     Document socio = new Document("nombre", nombre)
             .append("direccion", direccion)
             .append("libros_prestados", Arrays.asList());
     socios.insertOne(socio);
 }
 
 public static void anyadirAutor(String nombre, String nacionalidad) {
     Document autor = new Document("nombre", nombre)
             .append("nacionalidad", nacionalidad);
     autores.insertOne(autor);
 }

 public static void main(String[] args) {

	 anyadirAutor("Juan Garcia", "Español");
     ObjectId autorId = autores.find(new Document("nombre", "Juan Garcia")).first().getObjectId("_id");
     anadirLibro("Ejemplo", "123456789", autorId, "Ejemplo", true);
     anadirSocio("Juan Gomez", "Calle Ejemplo 1");

     ObjectId libroId = libros.find(new Document("titulo", "Ejemplo")).first().getObjectId("_id");
     ObjectId socioId = socios.find(new Document("nombre", "Juan Pérez")).first().getObjectId("_id");

     returnLibro(libroId, socioId);
     mostrarLibrosDisponibles();
     returnLibro(libroId, socioId);
     mostrarLibrosDisponibles();
 }
} 
