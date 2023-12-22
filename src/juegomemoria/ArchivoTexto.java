package juegomemoria;

import java.io.PrintWriter;

public class ArchivoTexto {
    static void crear(String texto) { //Metodo para crear el archivo de texto con la informacion de jugador que gano el juego.
        
        try{
            PrintWriter writer = new PrintWriter("Ganador.txt", "UTF-8"); //Se crea el objeto de la clase PrintWriter que agregara el texto introducido al archivo.
            writer.println(texto);
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
