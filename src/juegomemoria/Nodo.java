package juegomemoria;

public class Nodo { //Nodos que se usaran para las estructuras de datos usadas para los jugadores, cartas y los ID's (al momento de compararlas).
    private Carta valorCarta;
    private Jugador valorJugador;
    private int idCarta;
    private Nodo siguiente;
    
    public Nodo(){
        this.valorCarta=null;
        this.siguiente=null;
        this.valorJugador=null;
        this.idCarta=0;
    }
    //Se crean metodos para cada tipo de dato que pueden almacenar los nodos.
    public void setValorCarta(Carta datoCarta){
        this.valorCarta=datoCarta;
    }
    
    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }
    
    public void setValorJugador(Jugador datoJugador){
        this.valorJugador=datoJugador;
    }
    
    public void setSiguiente(Nodo siguiente){
        this.siguiente=siguiente;
    }
    
    public Carta getValorCarta(){
        return valorCarta;
    }
    
    public Jugador getValorJugador(){
        return valorJugador;
    }
    
    public int getIdCarta() {
        return idCarta;
    }
    
    public Nodo getSiguiente(){
        return siguiente;
    }
}
