package juegomemoria;

public class Cola {
    private Nodo inicio;
    private Nodo fin;
    private int tamanio;
    
    public void Cola(){
        this.inicio=null;
        this.fin=null;
        this.tamanio=0;
    }
    
    public boolean esVacia(){ //Para determinar si la cola esta vacia.
        return inicio==null;
    }
    
    public void agregarEnCola(Jugador datoJugador){ //Para meter un nuevo elemento a la cola.
        Nodo nuevo=new Nodo();
        nuevo.setValorJugador(datoJugador);
        if (esVacia()){
            inicio = fin = nuevo;
        }else{
            fin.setSiguiente(nuevo);
            fin=nuevo;
        }
        tamanio++;
    }
    
    public Jugador sacarDeLaCola(){ //Para sacar el elemento que corresponde de la cola.
        Jugador valorJugador = null;
        if(!esVacia()){
            valorJugador=inicio.getValorJugador();
            inicio=inicio.getSiguiente();
            if(esVacia()){
                fin=null;
            }
            tamanio--;
        }
        return valorJugador;
    }
    
    public Cola ordenarPorPuntaje(){ //Metodo que genera una cola ordenada de jugadores en base al puntaje conseguido de mayor a menor. Esta basado en el metodo por seleccion.
        Cola aux = new Cola();
        int pMasAlto = 0;
        Jugador j;
        Jugador m;
        while(!esVacia()){
            for (int i=0;i<tamanio;i++){ //Hace una pasada por toda la cola para determinar el valor mas grande.
                j = sacarDeLaCola();
                if (j.getPuntaje()>=pMasAlto){
                    pMasAlto = j.getPuntaje(); //Al final de la pasada, el puntaje mas alto se almacena en pMasAlto.
                }
                agregarEnCola(j);
            }
            for (int i=0;i<tamanio;i++){ //Vuelve a hacer una pasada para meter el jugador que tiene el puntaje igual a pMasAlto en la nueva cola generada.
                m = sacarDeLaCola();
                if (m.getPuntaje()==pMasAlto){
                    aux.agregarEnCola(m);
                    pMasAlto = 0; //reinicia el valor de pMasAlto para la siquiente pasada.
                    break;
                } else{
                    agregarEnCola(m); //Agrega el jugador con el puntaje mas alto a la nueva cola, repite proceso para los jugadores faltantes.
                }
            }
        }
        return aux; //Cola con los jugadores ordenados por puntaje.
    }
}
