package juegomemoria;

public class Carta {
    private int id;
    private static int paresEncontrados = 0; //Se usara para determinar cuando debe terminar el juego.
    
    public Carta(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public static void sumarParEncontrado() {
        Carta.paresEncontrados++;
    }

    public static int getParesEncontrados() {
        return paresEncontrados;
    }
}
