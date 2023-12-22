package juegomemoria;

import java.util.ArrayList;
import java.util.Random;

public class cardRandomizer {
    
    public static Pila randomize(Carta[] cartas){ //metodo que recibe un arreglo ordenado y retorna una pila con esos elementos en orden aleatorio.
        ArrayList<Integer> index = new ArrayList<>(); //ArrayList vacia que contendra los indices del arreglo ordenado.
        for (int i=0;i<cartas.length;i++){
            index.add(i); //Agrega los indices de uno en uno hasta completar los que posee el arreglo.
        }
        int k = cartas.length; //El valor inicial de k es la cantidad de elementos en el arreglo de cartas.
        Random num = new Random(); //Num sera un entero generado de manera aleatoria en el rango especificado.
        int a; //El valor de num sera almacenado aqui para usarlo en cada iteracion.
        Pila ranCards = new Pila(); //Pila donde se almacenaran las cartas desordenadas.
        do{
            a = num.nextInt(k); //numero entero aleatorio entre 0 y k-1.
            ranCards.agregarCartaEnLaPila(cartas[index.get(a)]); //agrega al tope de la pila el elemento del arreglo ordenado en la posicion dada por el indice seleccionado dentro de la ArrayList de manera aleatoria.
            index.remove(a); //Elimina el indice selecionado del ArrayList para evitar que se repita.
            k--; //El rango de valores disminuye en 1 para ajustarse al nuevo tamano del ArrayList.
        }while (k>0);
        return ranCards; //Devuelve la pila de cartas ya desorganizada.
    }
}