package juegomemoria;

public class Pila {
    private Nodo tope;
    private int tamanio;
    
    public void Pila(){
        this.tope=null;
        this.tamanio=0;
    }
    public boolean esVacia(){ //Metodo para determinar si la pila esta vacia.
        return tope==null;
    }
    public int getTamanio(){
        return tamanio;
    }
    //Se crean metodos especificos para cada tipo de elemento que sera almacenado en una pila.
    public void agregarCartaEnLaPila(Carta valorCarta){ //Metodo para agregar cartas a la pila.
        Nodo nuevo=new Nodo();
        nuevo.setValorCarta(valorCarta);
        if (esVacia()){
            tope=nuevo;
        }else{
            nuevo.setSiguiente(tope);
            tope=nuevo;
        }
        tamanio++;
    }
    
    public void agregarIdEnLaPila(int valorId){ //Metodo para agregar IDs a la pila.
        Nodo nuevo=new Nodo();
        nuevo.setIdCarta(valorId);
        if (esVacia()){
            tope=nuevo;
        }else{
            nuevo.setSiguiente(tope);
            tope=nuevo;
        }
        tamanio++;
    }
    
    public Carta sacarCartaDePila(){ //Metodo para sacar una carta de la pila.
        Carta valorCarta=null;
        if (!esVacia()){
            valorCarta=tope.getValorCarta();
            tope=tope.getSiguiente();
            tamanio--;
        }
        return valorCarta;
    }
    
    public int sacarIdDePila(){ //Metodo para sacar una ID de la pila.
        Integer valorId=null;
        if (!esVacia()){
            valorId=tope.getIdCarta();
            tope=tope.getSiguiente();
            tamanio--;
        }
        return valorId;
    }
    
}