package ejercicio;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.FileWriter;

public class Ejercicio1
{

    public static void main(String[] args) 
    {
        try 
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File("pelis.xml");
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList listaPeliculas = document.getElementsByTagName("pelicula");

            FileWriter writer = new FileWriter("peliculas.csv");

            for (int i = 0; i < listaPeliculas.getLength(); i++) 
            {
                Node nodo = listaPeliculas.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element pelicula = (Element) nodo;

                    String tituloOriginal = pelicula.getAttribute("titulo");
                    String estreno = pelicula.getElementsByTagName("estreno").item(0).getTextContent();
                    String titulo = pelicula.getElementsByTagName("titulo").item(0).getTextContent();
                    String director = pelicula.getElementsByTagName("director").item(0).getTextContent();
                    String pais = pelicula.getElementsByTagName("director").item(0).getAttributes().getNamedItem("pais").getTextContent();

                    System.out.println("Película: " + tituloOriginal);
                    System.out.println("Estreno: " + estreno);
                    System.out.println("Título en inglés: " + titulo);
                    System.out.println("Director: " + director);
                    System.out.println("País: " + pais);
                    System.out.println("-------------------------");

                    writer.write(tituloOriginal + "," + estreno + "," + titulo + "," + director + "," + pais + "\n");
                }
            }

            writer.close();
            System.out.println("Los datos han sido guardados en peliculas.csv");
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
}
