package juegomemoria;

public class Jugador {
    private String Nombre;
    private int Puntaje;
    private int Turno;
    private int ID = 0;
    private static int Cantidad = 0; //Cantidad de jugadores.
    
    public Jugador(String n, int id){
        this.Nombre = n;
        this.Puntaje = 0;
        this.Turno = 0;
        this.ID = id;
    }
    
    public static void setCantidad(int c){ //Valor que sera determinado al inicio del programa.
        Cantidad = c;
    }

    public static int getCantidad() {
        return Cantidad;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getPuntaje() {
        return Puntaje;
    }

    public int getTurno() {
        return Turno;
    }
    
    public void sumarPunto(){ //Para incrementar el contador de puntos de cada jugador.
        Puntaje++;
    }
    
    public void sumarTurno(){ //Para incrementar el contador de turnos de cada jugador.
        Turno++;
    }
}
