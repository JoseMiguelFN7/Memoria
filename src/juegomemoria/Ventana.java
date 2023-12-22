package juegomemoria;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



public class Ventana extends javax.swing.JFrame {
///////////////////////////////////////////////////////////////////////////////////////////////////Imagenes de las cartas.
    ImageIcon Imagen1 = new ImageIcon(getClass().getResource("/Imagenes/Imagen1.png"));
    ImageIcon Imagen2 = new ImageIcon(getClass().getResource("/Imagenes/Imagen2.png"));
    ImageIcon Imagen3 = new ImageIcon(getClass().getResource("/Imagenes/Imagen3.png"));
    ImageIcon Imagen4 = new ImageIcon(getClass().getResource("/Imagenes/Imagen4.png"));
    ImageIcon Imagen5 = new ImageIcon(getClass().getResource("/Imagenes/Imagen5.png"));
    ImageIcon Imagen6 = new ImageIcon(getClass().getResource("/Imagenes/Imagen6.png"));
    ImageIcon Imagen7 = new ImageIcon(getClass().getResource("/Imagenes/Imagen7.png"));
    ImageIcon Imagen8 = new ImageIcon(getClass().getResource("/Imagenes/Imagen8.png"));
    ImageIcon Imagen9 = new ImageIcon(getClass().getResource("/Imagenes/Imagen9.png"));
    ImageIcon ImagenR = new ImageIcon(getClass().getResource("/Imagenes/Reverso.png"));
///////////////////////////////////////////////////////////////////////////////////////////////////
    
    Pila pareja = new Pila(); //Pila donde se comparan las ID's de las dos cartas seleccionadas.
    Cola jugadores = new Cola(); //Cola que contiene a los jugadores en orden de turnos.
    javax.swing.JLabel primera = null; //Variable usada para guardar la primera carta seleccionada en un turno.
    Jugador jugadorEnTurno = null; //Variable donde se almacena el jugador que esta jugando su turno.
    Cola ranking = new Cola(); //Cola donde seran almacenados los jugadores de mayor a menor en base a su puntaje.
    Cola primerPunto = new Cola(); //Cola donde seran almacenados los jugadores a medida que consiguen su primer punto. Se usa para determinar desempate.
    Jugador J1 = null; //Estas variables se usaran para comparar puntajes de los jugadores, saber si hay empate empate y determinar el ganador usando la cola primerPunto.
    Jugador J2 = null;
    Jugador J3 = null;
    Jugador J4 = null;
    Jugador w = null;
    boolean enableClick; //Booleano usado para desactivar los clicks en los momentos deseados.
    Clip playingMusic = null;
    
    /**
     * Creates new form Ventana
     */
    public Ventana() {
        
        System.out.print("\033[H\033[2J"); //Esto es un clear screen.
        System.out.flush();
        Reproductor.welcome();
        Carta[] cartas = new Carta[18]; //se declara un arreglo vacio de 18 elementos donde se almacenan las 18 cartas.
        int j=0;
        for (int i=0; i<cartas.length;i+=2){ //Un bucle for para crear los 18 objetos de la clase carta (22 cartas).
            cartas[i]= new Carta(j); //La idea de esto es que la carta en la posicion i e i+1 tengan el mismo ID j, de modo que cada par tenga ID's iguales.
            cartas[i+1]= new Carta(j);
            j++;
        }
        Pila ranCards = new Pila(); //crea la pila donde estaran las cartas barajeadas.
        ranCards = cardRandomizer.randomize(cartas); //almacena la pila barajeada creada por el metodo randomize.
        
        Integer[] op = {2, 3, 4}; //Opciones disponibles para la cantidad de jugadores.
        int numjug = JOptionPane.showOptionDialog(null, "Indique el numero de jugadores: ", "Bienvenido al juego de memoria!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, op, null); //Indica la cantidad de jugadores.
        switch (numjug){
            case 0:
                Jugador.setCantidad(2);
                break;
            case 1:
                Jugador.setCantidad(3);
                break;
            case 2:
                Jugador.setCantidad(4);
                break;
            default:
                System.exit(0); //Si no se selecciona nada, termina la ejecucion del programa.
        }
        
        for (int i=0; i<Jugador.getCantidad();i++){ //Un bucle for que se repite segun la cantidad de jugadores para crearlos.
            int a=i+1; //ID del jugador.
            String nombre; //Nombre del jugador.
            boolean con=false;
            do{
                nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador " + a + ": "); //Pide el nombre del jugador para que el constructor se lo asigne al atributo nombre.
                if (nombre == null){ //Si el usuario presiona la X o "Cancelar" el programa se cierra.
                System.exit(0);
                } else{
                    if(nombre.equals("")){ //Le pedira el nombre al usuario hasta que no deje el campo vacio.
                        con=true;
                    }else{
                        con=false;
                    }
                }
            } while(con);
            jugadores.agregarEnCola(new Jugador(nombre, a)); //Se crea el objeto de clase jugador con el nombre especificado y su ID y se almacena en la cola.
        }
        playingMusic = Reproductor.playingMusic();
        initComponents(); //Inicia los componentes de la ventana.
        setSize(1000, 930); //Tamano de la ventana.
        setLocationRelativeTo(null); //Para que la ventana se ubique en el centro de la pantalla.
        
/////////////////////////////////////////////////////Esto es para ocultar los componentes visuales que no deben verse al inicio del programa.
        marcoTP2.setVisible(false);
        marcoP3.setVisible(false);
        marcoTP3.setVisible(false);
        nombreP3.setVisible(false);
        imagenP3.setVisible(false);
        puntosP3.setVisible(false);
        turnosP3.setVisible(false);
        marcoP4.setVisible(false);
        marcoTP4.setVisible(false);
        nombreP4.setVisible(false);
        imagenP4.setVisible(false);
        puntosP4.setVisible(false);
        turnosP4.setVisible(false);
//////////////////////////////////////////////////////

        for (int i=0;i<Jugador.getCantidad();i++){ //Le da los valores iniciales a los jLabel que contienen el nombre, puntaje y turno de cada jugador. Ademas del marco que indica el turno del jugador.
            Jugador p = jugadores.sacarDeLaCola(); //Saca el jugador de la cola para asignarle los valores a sus jLabel.
            actualizar(p);
            jugadores.agregarEnCola(p); //Introduce al jugador nuevamente en la cola. Como se repite segun la cantidad de jugadores, cada uno termina en su posicion inicial.
        }
        
        switch (Jugador.getCantidad()){ //Muestra los componentes visuales que deben verse segun la cantidad de jugadores.
            case 3:                
                marcoP3.setVisible(true);
                nombreP3.setVisible(true);
                imagenP3.setVisible(true);
                puntosP3.setVisible(true);
                turnosP3.setVisible(true);
                break;
            case 4:
                marcoP3.setVisible(true);
                nombreP3.setVisible(true);
                imagenP3.setVisible(true);
                puntosP3.setVisible(true);
                turnosP3.setVisible(true);
                marcoP4.setVisible(true);
                nombreP4.setVisible(true);
                imagenP4.setVisible(true);
                puntosP4.setVisible(true);
                turnosP4.setVisible(true);
                break;
        }
////////////////////////////////////////////////////////////////////////////////////////Valores iniciales de cada carta. El orden que son asignados los ID es aleatorio y almacenados en el campo de texto del jLabel.
        Carta1.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta1.setIcon(ImagenR);
        Carta2.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta2.setIcon(ImagenR);
        Carta3.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta3.setIcon(ImagenR);
        Carta4.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta4.setIcon(ImagenR);
        Carta5.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta5.setIcon(ImagenR);
        Carta6.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta6.setIcon(ImagenR);
        Carta7.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta7.setIcon(ImagenR);
        Carta8.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta8.setIcon(ImagenR);
        Carta9.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta9.setIcon(ImagenR);
        Carta10.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta10.setIcon(ImagenR);
        Carta11.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta11.setIcon(ImagenR);
        Carta12.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta12.setIcon(ImagenR);
        Carta13.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta13.setIcon(ImagenR);
        Carta14.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta14.setIcon(ImagenR);
        Carta15.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta15.setIcon(ImagenR);
        Carta16.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta16.setIcon(ImagenR);
        Carta17.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta17.setIcon(ImagenR);
        Carta18.setText(Integer.toString(ranCards.sacarCartaDePila().getId()));
        Carta18.setIcon(ImagenR);
//////////////////////////////////////////////////////////////////////////////////////////
        enableClick = true; //Los clicks se activan al inicio del programa.
    }
    
    public void actualizar(Jugador p){ //Procedimiento usado para asignar el puntaje, turno y el marco del jugador en cada turno actualizandolos.
        
        switch(Jugador.getCantidad()){ //Para determinar cual jugador tendra el marco en cada turno. El orden en que se pasa depende de la cantidad de jugadores.
            case 2:
                switch (p.getID()){
                    case 1:
                        marcoTP1.setVisible(false);
                        marcoTP2.setVisible(true);
                        break;
                    case 2:
                        marcoTP1.setVisible(true);
                        marcoTP2.setVisible(false);
                        break;
                }
                break;
            case 3:
                switch (p.getID()){
                    case 1:
                        marcoTP1.setVisible(false);
                        marcoTP2.setVisible(true);
                        break;
                    case 2:
                        marcoTP2.setVisible(false);
                        marcoTP3.setVisible(true);
                        break;
                    case 3:
                        marcoTP3.setVisible(false);
                        marcoTP1.setVisible(true);
                        break;
                }
                break;
            case 4:
                switch (p.getID()){
                    case 1:
                        marcoTP1.setVisible(false);
                        marcoTP2.setVisible(true);
                        break;
                    case 2:
                        marcoTP2.setVisible(false);
                        marcoTP3.setVisible(true);
                        break;
                    case 3:
                        marcoTP3.setVisible(false);
                        marcoTP4.setVisible(true);
                        break;
                    case 4:
                        marcoTP4.setVisible(false);
                        marcoTP1.setVisible(true);
                        break;
                }
                break;
        }
        
        switch (p.getID()){ //Para asignar los valores de los jLabels de cada jugador en cada turno para actualizarlos.
            case 1:
                nombreP1.setText(p.getNombre());
                puntosP1.setText("Puntos: " + Integer.toString(p.getPuntaje()));
                turnosP1.setText("Turno: " + Integer.toString(p.getTurno()));
                break;
            case 2:
                nombreP2.setText(p.getNombre());
                puntosP2.setText("Puntos: " + Integer.toString(p.getPuntaje()));
                turnosP2.setText("Turno: " + Integer.toString(p.getTurno()));
                break;
            case 3:
                nombreP3.setText(p.getNombre());
                puntosP3.setText("Puntos: " + Integer.toString(p.getPuntaje()));
                turnosP3.setText("Turno: " + Integer.toString(p.getTurno()));
                break;
            case 4:
                nombreP4.setText(p.getNombre());
                puntosP4.setText("Puntos: " + Integer.toString(p.getPuntaje()));
                turnosP4.setText("Turno: " + Integer.toString(p.getTurno()));
                break;
            }
    }
    
    public void comparar(javax.swing.JLabel c){ //funcion usada para determinar si las dos cartas seleccionadas son iguales o no.
        if ((c.getIcon()==ImagenR) && (enableClick)){ //Solo se ejecuta cuando la carta esta boca abajo y los clicks estan activados.
            Reproductor.flip1();
            pareja.agregarIdEnLaPila(Integer.parseInt(c.getText())); //Se agrega el ID de la carta clickeada en la pila usada para las comparaciones.
            switch (c.getText()) { //La carta al voltearse, mostrara la imagen correspondiente a su ID.
                case "0":
                    c.setIcon(Imagen1);
                    break;
                case "1":
                    c.setIcon(Imagen2);
                    break;
                case "2":
                    c.setIcon(Imagen3);
                    break;
                case "3":
                    c.setIcon(Imagen4);
                    break;
                case "4":
                    c.setIcon(Imagen5);
                    break;
                case "5":
                    c.setIcon(Imagen6);
                    break;
                case "6":
                    c.setIcon(Imagen7);
                    break;
                case "7":
                    c.setIcon(Imagen8);
                    break;
                case "8":
                    c.setIcon(Imagen9);
                    break;
            }
        }
        
        switch(pareja.getTamanio()){
            case 1:
                jugadorEnTurno = jugadores.sacarDeLaCola(); //Al seleccionar la primera carta, el jugador es guardado dentro de una variable.
                primera=c; //La primera carta seleccionada es guardada en una variable.
                break;
            case 2:
                if (pareja.sacarIdDePila()==pareja.sacarIdDePila()){ //Saca ambas ID's de la pila y las compara.
                    Reproductor.correct();
                    jugadorEnTurno.sumarPunto(); //Si hay match, suma un punto al jugador.
                    Carta.sumarParEncontrado();//Si hay match, agrega un par encontrado al contador.
                    if (jugadorEnTurno.getPuntaje()==1){ //Si es el primer punto del jugador, lo agrega a la cola usada para determinar los desempates.
                        primerPunto.agregarEnCola(jugadorEnTurno);
                    }
                }else{
                    enableClick = false; //Si no hay match, las cartas quedaran levantadas por 1.5 segundos antes de voltearse. En ese tiempo los clicks estan desactivados.
                    new Thread(){
                        public void run(){
                            try{
                                Thread.sleep(1500);
                                c.setIcon(ImagenR);
                                primera.setIcon(ImagenR);
                                Reproductor.flip2();
                                primera=null;
                                enableClick = true; //Una vez terminada la espera, se reactivan los clicks.
                            }catch(Exception q){}
                        }
                    }.start();
                }
                jugadorEnTurno.sumarTurno(); //Cuando termina el turno del jugador, se adiciona a su contador de turnos.
                actualizar(jugadorEnTurno); //Se actualizan los valores del jugador.
                jugadores.agregarEnCola(jugadorEnTurno); //El jugador es agregado al final de la cola.
                break;
        }
        
    }
    
    public void finalizar(){ //Una vez se consiguen los 9 pares, se determina un ganador y se genera el archivo de texto para finalizar el juego.
        if (Carta.getParesEncontrados()==9){
            Reproductor.stopPlayingMusic(playingMusic);
            ranking = jugadores.ordenarPorPuntaje(); //se genera y almacena la cola por puntajes.
            J1 = ranking.sacarDeLaCola();
            J2 = ranking.sacarDeLaCola();
            switch (Jugador.getCantidad()){
                case 2: //Si hay 2 jugadores, el empate es imposible.
                    Reproductor.end();
                    Reproductor.endMusic();
                    Reproductor.cheers();
                    JOptionPane.showMessageDialog(null, "El ganador es " + J1.getNombre() + " con " + J1.getPuntaje() + " puntos en " + J1.getTurno() + " turnos.");
                    ArchivoTexto.crear("El ganador es " + J1.getNombre() + " con " + J1.getPuntaje() + " puntos en " + J1.getTurno() + " turnos.");
                    break;
                case 3: //Si hay 3 jugadores, el empate se puede dar en dos casos.
                    J3 = ranking.sacarDeLaCola();
                    if (J1.getPuntaje()==J2.getPuntaje()){
                        Reproductor.ohhh();
                        switch (J1.getPuntaje()){
                            case 3: //Cuando los 3 jugadores tienen 3 puntos, gana el que consiguio punto primero entre ellos.
                                w = primerPunto.sacarDeLaCola();
                                JOptionPane.showMessageDialog(null, "TRIPLE EMPATE! El primero en conseguir un punto fue...");
                                Reproductor.end();
                                Reproductor.endMusic();
                                Reproductor.cheers();
                                JOptionPane.showMessageDialog(null, "El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                ArchivoTexto.crear("El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                break;
                            case 4: //Dos jugadores tienen 2 puntos. Gana el primero que consiguio punto entre ellos dos.
                                JOptionPane.showMessageDialog(null, "EMPATE entre " + J1.getNombre() + " y " + J2.getNombre() + ". El primero en conseguir un punto fue...");
                                while(!primerPunto.esVacia()){
                                    w = primerPunto.sacarDeLaCola();
                                    if ((J1.getID()==w.getID()) || (J2.getID()==w.getID())){
                                        Reproductor.end();
                                        Reproductor.endMusic();
                                        Reproductor.cheers();
                                        JOptionPane.showMessageDialog(null, "El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                        ArchivoTexto.crear("El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                        break;
                                    }
                                }
                                break;
                        }
                    }else{ //Si no hay empate, gana el jugador en la primera posicion.
                        Reproductor.end();
                        Reproductor.endMusic();
                        Reproductor.cheers();
                        JOptionPane.showMessageDialog(null, "El ganador es " + J1.getNombre() + " con " + J1.getPuntaje() + " puntos en " + J1.getTurno() + " turnos.");
                        ArchivoTexto.crear("El ganador es " + J1.getNombre() + " con " + J1.getPuntaje() + " puntos en " + J1.getTurno() + " turnos.");
                    }
                    break;
                case 4:
                    J3 = ranking.sacarDeLaCola();
                    J4 = ranking.sacarDeLaCola();
                    if (J1.getPuntaje()==J2.getPuntaje()){ //Si hay 4 jugadores, el empate se da en 3 casos.
                        Reproductor.ohhh();
                        switch (J1.getPuntaje()){
                            case 3:
                                if (J3.getPuntaje()==J1.getPuntaje()){ //Triple empate con 3 puntos. Gana el primero que consiguio punto entre ellos 3.
                                    JOptionPane.showMessageDialog(null, "TRIPLE EMPATE entre " + J1.getNombre() + ", " + J2.getNombre() + " y " + J3.getNombre() + ". El primero en conseguir un punto fue...");
                                    while(!primerPunto.esVacia()){
                                        w = primerPunto.sacarDeLaCola();
                                        if ((J1.getID()==w.getID()) || (J2.getID()==w.getID()) || (J3.getID()==w.getID())){
                                            Reproductor.end();
                                            Reproductor.endMusic();
                                            Reproductor.cheers();
                                            JOptionPane.showMessageDialog(null, "El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                            ArchivoTexto.crear("El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                            break;
                                        }
                                    }
                                } else{ //Empate entre dos jugadores con 3 puntos. Gana el primero que consiguio punto entre ellos 2.
                                    JOptionPane.showMessageDialog(null, "EMPATE entre " + J1.getNombre() + " y " + J2.getNombre() + ". El primero en conseguir un punto fue...");
                                    while(!primerPunto.esVacia()){
                                        w = primerPunto.sacarDeLaCola();
                                        if ((J1.getID()==w.getID()) || (J2.getID()==w.getID())){
                                            Reproductor.end();
                                            Reproductor.endMusic();
                                            Reproductor.cheers();
                                            JOptionPane.showMessageDialog(null, "El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                            ArchivoTexto.crear("El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 4: ////Empate entre dos jugadores con 4 puntos. Gana el primero que consiguio punto entre ellos 2.
                                
                                JOptionPane.showMessageDialog(null, "EMPATE entre " + J1.getNombre() + " y " + J2.getNombre() + ". El primero en conseguir un punto fue...");
                                while(!primerPunto.esVacia()){
                                    w = primerPunto.sacarDeLaCola();
                                    if ((J1.getID()==w.getID()) || (J2.getID()==w.getID())){
                                        Reproductor.end();
                                        Reproductor.endMusic();
                                        Reproductor.cheers();
                                        JOptionPane.showMessageDialog(null, "El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                        ArchivoTexto.crear("El ganador es " + w.getNombre() + " con " + w.getPuntaje() + " puntos en " + w.getTurno() + " turnos.");
                                        break;
                                    }
                                }
                                break;
                        }
                    }else{ //Si no hay empate, gana el jugador en la primera posicion.
                        Reproductor.end();
                        Reproductor.endMusic();
                        Reproductor.cheers();
                        JOptionPane.showMessageDialog(null, "El ganador es " + J1.getNombre() + " con " + J1.getPuntaje() + " puntos en " + J1.getTurno() + " turnos.");
                        ArchivoTexto.crear("El ganador es " + J1.getNombre() + " con " + J1.getPuntaje() + " puntos en " + J1.getTurno() + " turnos.");
                    }
                    break;
            }
        System.exit(0); //Una vez determinado el ganador, se termina la ejecucion del programa.
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Carta1 = new javax.swing.JLabel();
        Carta2 = new javax.swing.JLabel();
        Carta3 = new javax.swing.JLabel();
        Carta4 = new javax.swing.JLabel();
        Carta5 = new javax.swing.JLabel();
        Carta6 = new javax.swing.JLabel();
        Carta7 = new javax.swing.JLabel();
        Carta8 = new javax.swing.JLabel();
        Carta9 = new javax.swing.JLabel();
        Carta10 = new javax.swing.JLabel();
        Carta11 = new javax.swing.JLabel();
        Carta12 = new javax.swing.JLabel();
        Carta13 = new javax.swing.JLabel();
        Carta14 = new javax.swing.JLabel();
        Carta15 = new javax.swing.JLabel();
        Carta16 = new javax.swing.JLabel();
        Carta17 = new javax.swing.JLabel();
        Carta18 = new javax.swing.JLabel();
        imagenP4 = new javax.swing.JLabel();
        imagenP3 = new javax.swing.JLabel();
        imagenP2 = new javax.swing.JLabel();
        imagenP1 = new javax.swing.JLabel();
        turnosP4 = new javax.swing.JLabel();
        turnosP3 = new javax.swing.JLabel();
        turnosP2 = new javax.swing.JLabel();
        turnosP1 = new javax.swing.JLabel();
        puntosP4 = new javax.swing.JLabel();
        puntosP3 = new javax.swing.JLabel();
        puntosP2 = new javax.swing.JLabel();
        puntosP1 = new javax.swing.JLabel();
        nombreP4 = new javax.swing.JLabel();
        nombreP3 = new javax.swing.JLabel();
        nombreP2 = new javax.swing.JLabel();
        nombreP1 = new javax.swing.JLabel();
        marcoTP4 = new javax.swing.JLabel();
        marcoTP3 = new javax.swing.JLabel();
        marcoTP2 = new javax.swing.JLabel();
        marcoTP1 = new javax.swing.JLabel();
        marcoP4 = new javax.swing.JLabel();
        marcoP3 = new javax.swing.JLabel();
        marcoP2 = new javax.swing.JLabel();
        marcoP1 = new javax.swing.JLabel();
        bInstrucciones = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JUEGO DE MEMORIA");
        setResizable(false);
        getContentPane().setLayout(null);

        Carta1.setText("carta1");
        Carta1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta1MouseClicked(evt);
            }
        });
        getContentPane().add(Carta1);
        Carta1.setBounds(300, 50, 120, 186);

        Carta2.setText("carta2");
        Carta2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta2MouseClicked(evt);
            }
        });
        getContentPane().add(Carta2);
        Carta2.setBounds(430, 50, 120, 186);

        Carta3.setText("carta3");
        Carta3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta3MouseClicked(evt);
            }
        });
        getContentPane().add(Carta3);
        Carta3.setBounds(570, 50, 120, 186);

        Carta4.setText("carta4");
        Carta4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta4MouseClicked(evt);
            }
        });
        getContentPane().add(Carta4);
        Carta4.setBounds(100, 250, 120, 186);

        Carta5.setText("carta5");
        Carta5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta5MouseClicked(evt);
            }
        });
        getContentPane().add(Carta5);
        Carta5.setBounds(230, 250, 120, 186);

        Carta6.setText("carta6");
        Carta6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta6MouseClicked(evt);
            }
        });
        getContentPane().add(Carta6);
        Carta6.setBounds(370, 250, 120, 186);

        Carta7.setText("carta7");
        Carta7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta7MouseClicked(evt);
            }
        });
        getContentPane().add(Carta7);
        Carta7.setBounds(510, 250, 120, 186);

        Carta8.setText("carta8");
        Carta8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta8MouseClicked(evt);
            }
        });
        getContentPane().add(Carta8);
        Carta8.setBounds(650, 250, 120, 186);

        Carta9.setText("carta9");
        Carta9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta9MouseClicked(evt);
            }
        });
        getContentPane().add(Carta9);
        Carta9.setBounds(790, 250, 120, 186);

        Carta10.setText("carta10");
        Carta10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta10MouseClicked(evt);
            }
        });
        getContentPane().add(Carta10);
        Carta10.setBounds(100, 450, 120, 186);

        Carta11.setText("carta11");
        Carta11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta11MouseClicked(evt);
            }
        });
        getContentPane().add(Carta11);
        Carta11.setBounds(230, 450, 120, 186);

        Carta12.setText("carta12");
        Carta12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta12MouseClicked(evt);
            }
        });
        getContentPane().add(Carta12);
        Carta12.setBounds(370, 450, 120, 186);

        Carta13.setText("carta13");
        Carta13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta13MouseClicked(evt);
            }
        });
        getContentPane().add(Carta13);
        Carta13.setBounds(510, 450, 120, 186);

        Carta14.setText("carta14");
        Carta14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta14MouseClicked(evt);
            }
        });
        getContentPane().add(Carta14);
        Carta14.setBounds(650, 450, 120, 186);

        Carta15.setText("carta15");
        Carta15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta15MouseClicked(evt);
            }
        });
        getContentPane().add(Carta15);
        Carta15.setBounds(790, 450, 120, 186);

        Carta16.setText("carta16");
        Carta16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta16MouseClicked(evt);
            }
        });
        getContentPane().add(Carta16);
        Carta16.setBounds(300, 650, 120, 186);

        Carta17.setText("carta17");
        Carta17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta17MouseClicked(evt);
            }
        });
        getContentPane().add(Carta17);
        Carta17.setBounds(440, 650, 120, 186);

        Carta18.setText("carta18");
        Carta18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Carta18MouseClicked(evt);
            }
        });
        getContentPane().add(Carta18);
        Carta18.setBounds(580, 650, 120, 186);

        imagenP4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ImagenP4.png"))); // NOI18N
        getContentPane().add(imagenP4);
        imagenP4.setBounds(850, 740, 80, 80);

        imagenP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ImagenP3.png"))); // NOI18N
        getContentPane().add(imagenP3);
        imagenP3.setBounds(70, 740, 80, 80);

        imagenP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ImagenP2.png"))); // NOI18N
        getContentPane().add(imagenP2);
        imagenP2.setBounds(850, 60, 80, 80);

        imagenP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ImagenP1.png"))); // NOI18N
        getContentPane().add(imagenP1);
        imagenP1.setBounds(70, 60, 80, 80);

        turnosP4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        turnosP4.setForeground(new java.awt.Color(255, 255, 255));
        turnosP4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnosP4.setText("turnos P4");
        getContentPane().add(turnosP4);
        turnosP4.setBounds(830, 840, 120, 30);

        turnosP3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        turnosP3.setForeground(new java.awt.Color(255, 255, 255));
        turnosP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnosP3.setText("turnos P3");
        getContentPane().add(turnosP3);
        turnosP3.setBounds(50, 840, 120, 30);

        turnosP2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        turnosP2.setForeground(new java.awt.Color(255, 255, 255));
        turnosP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnosP2.setText("turnos P2");
        getContentPane().add(turnosP2);
        turnosP2.setBounds(830, 160, 120, 30);

        turnosP1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        turnosP1.setForeground(new java.awt.Color(255, 255, 255));
        turnosP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnosP1.setText("turnos P1");
        getContentPane().add(turnosP1);
        turnosP1.setBounds(50, 160, 120, 30);

        puntosP4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        puntosP4.setForeground(new java.awt.Color(255, 255, 255));
        puntosP4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntosP4.setText("puntos P4");
        getContentPane().add(puntosP4);
        puntosP4.setBounds(830, 820, 120, 30);

        puntosP3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        puntosP3.setForeground(new java.awt.Color(255, 255, 255));
        puntosP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntosP3.setText("puntos P3");
        getContentPane().add(puntosP3);
        puntosP3.setBounds(50, 820, 120, 30);

        puntosP2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        puntosP2.setForeground(new java.awt.Color(255, 255, 255));
        puntosP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntosP2.setText("puntos P2");
        getContentPane().add(puntosP2);
        puntosP2.setBounds(830, 140, 120, 30);

        puntosP1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        puntosP1.setForeground(new java.awt.Color(255, 255, 255));
        puntosP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        puntosP1.setText("puntos P1");
        getContentPane().add(puntosP1);
        puntosP1.setBounds(50, 140, 120, 30);

        nombreP4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nombreP4.setForeground(new java.awt.Color(255, 255, 255));
        nombreP4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreP4.setText("nombre P4");
        getContentPane().add(nombreP4);
        nombreP4.setBounds(830, 710, 120, 30);

        nombreP3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nombreP3.setForeground(new java.awt.Color(255, 255, 255));
        nombreP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreP3.setText("nombre P3");
        getContentPane().add(nombreP3);
        nombreP3.setBounds(50, 710, 120, 30);

        nombreP2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nombreP2.setForeground(new java.awt.Color(255, 255, 255));
        nombreP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreP2.setText("nombre P2");
        getContentPane().add(nombreP2);
        nombreP2.setBounds(830, 30, 120, 30);

        nombreP1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        nombreP1.setForeground(new java.awt.Color(255, 255, 255));
        nombreP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombreP1.setText("nombre P1");
        getContentPane().add(nombreP1);
        nombreP1.setBounds(50, 30, 120, 30);

        marcoTP4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MarcoTurnoP4.png"))); // NOI18N
        getContentPane().add(marcoTP4);
        marcoTP4.setBounds(760, 660, 244, 244);

        marcoTP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MarcoTurnoP3.png"))); // NOI18N
        getContentPane().add(marcoTP3);
        marcoTP3.setBounds(0, 660, 244, 244);

        marcoTP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MarcoTurnoP2.png"))); // NOI18N
        getContentPane().add(marcoTP2);
        marcoTP2.setBounds(760, 0, 244, 244);

        marcoTP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MarcoTurnoP1.png"))); // NOI18N
        getContentPane().add(marcoTP1);
        marcoTP1.setBounds(-10, -10, 244, 244);

        marcoP4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Marco.png"))); // NOI18N
        getContentPane().add(marcoP4);
        marcoP4.setBounds(780, 680, 220, 220);

        marcoP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Marco.png"))); // NOI18N
        getContentPane().add(marcoP3);
        marcoP3.setBounds(0, 680, 220, 220);

        marcoP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Marco.png"))); // NOI18N
        getContentPane().add(marcoP2);
        marcoP2.setBounds(780, 0, 220, 220);

        marcoP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Marco.png"))); // NOI18N
        getContentPane().add(marcoP1);
        marcoP1.setBounds(0, 0, 220, 220);

        bInstrucciones.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        bInstrucciones.setText("Instrucciones");
        bInstrucciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInstruccionesActionPerformed(evt);
            }
        });
        getContentPane().add(bInstrucciones);
        bInstrucciones.setBounds(440, 850, 130, 40);

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo.png"))); // NOI18N
        getContentPane().add(fondo);
        fondo.setBounds(0, 0, 1000, 900);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Carta1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta1MouseClicked
        comparar(Carta1);
        finalizar();
    }//GEN-LAST:event_Carta1MouseClicked

    private void Carta2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta2MouseClicked
        comparar(Carta2);
        finalizar();
    }//GEN-LAST:event_Carta2MouseClicked

    private void Carta3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta3MouseClicked
        comparar(Carta3);
        finalizar();
    }//GEN-LAST:event_Carta3MouseClicked

    private void Carta4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta4MouseClicked
        comparar(Carta4);
        finalizar();
    }//GEN-LAST:event_Carta4MouseClicked

    private void Carta5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta5MouseClicked
        comparar(Carta5);
        finalizar();
    }//GEN-LAST:event_Carta5MouseClicked

    private void Carta6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta6MouseClicked
        comparar(Carta6);
        finalizar();
    }//GEN-LAST:event_Carta6MouseClicked

    private void Carta7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta7MouseClicked
        comparar(Carta7);
        finalizar();
    }//GEN-LAST:event_Carta7MouseClicked

    private void Carta8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta8MouseClicked
        comparar(Carta8);
        finalizar();
    }//GEN-LAST:event_Carta8MouseClicked

    private void Carta10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta10MouseClicked
        comparar(Carta10);
        finalizar();
    }//GEN-LAST:event_Carta10MouseClicked

    private void Carta11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta11MouseClicked
        comparar(Carta11);
        finalizar();
    }//GEN-LAST:event_Carta11MouseClicked

    private void Carta12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta12MouseClicked
        comparar(Carta12);
        finalizar();
    }//GEN-LAST:event_Carta12MouseClicked

    private void Carta13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta13MouseClicked
        comparar(Carta13);
        finalizar();
    }//GEN-LAST:event_Carta13MouseClicked

    private void Carta14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta14MouseClicked
        comparar(Carta14);
        finalizar();
    }//GEN-LAST:event_Carta14MouseClicked

    private void Carta15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta15MouseClicked
        comparar(Carta15);
        finalizar();
    }//GEN-LAST:event_Carta15MouseClicked

    private void Carta16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta16MouseClicked
        comparar(Carta16);
        finalizar();
    }//GEN-LAST:event_Carta16MouseClicked

    private void Carta17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta17MouseClicked
        comparar(Carta17);
        finalizar();
    }//GEN-LAST:event_Carta17MouseClicked

    private void Carta18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta18MouseClicked
        comparar(Carta18);
        finalizar();
    }//GEN-LAST:event_Carta18MouseClicked

    private void Carta9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Carta9MouseClicked
        comparar(Carta9);
        finalizar();
    }//GEN-LAST:event_Carta9MouseClicked

    private void bInstruccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInstruccionesActionPerformed
        try {
        File Instrucciones = new File("Manual de usuario.pdf");
        Desktop.getDesktop().open(Instrucciones);
    } catch (IOException q) {}
    }//GEN-LAST:event_bInstruccionesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Carta1;
    private javax.swing.JLabel Carta10;
    private javax.swing.JLabel Carta11;
    private javax.swing.JLabel Carta12;
    private javax.swing.JLabel Carta13;
    private javax.swing.JLabel Carta14;
    private javax.swing.JLabel Carta15;
    private javax.swing.JLabel Carta16;
    private javax.swing.JLabel Carta17;
    private javax.swing.JLabel Carta18;
    private javax.swing.JLabel Carta2;
    private javax.swing.JLabel Carta3;
    private javax.swing.JLabel Carta4;
    private javax.swing.JLabel Carta5;
    private javax.swing.JLabel Carta6;
    private javax.swing.JLabel Carta7;
    private javax.swing.JLabel Carta8;
    private javax.swing.JLabel Carta9;
    private javax.swing.JButton bInstrucciones;
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel imagenP1;
    private javax.swing.JLabel imagenP2;
    private javax.swing.JLabel imagenP3;
    private javax.swing.JLabel imagenP4;
    private javax.swing.JLabel marcoP1;
    private javax.swing.JLabel marcoP2;
    private javax.swing.JLabel marcoP3;
    private javax.swing.JLabel marcoP4;
    private javax.swing.JLabel marcoTP1;
    private javax.swing.JLabel marcoTP2;
    private javax.swing.JLabel marcoTP3;
    private javax.swing.JLabel marcoTP4;
    private javax.swing.JLabel nombreP1;
    private javax.swing.JLabel nombreP2;
    private javax.swing.JLabel nombreP3;
    private javax.swing.JLabel nombreP4;
    private javax.swing.JLabel puntosP1;
    private javax.swing.JLabel puntosP2;
    private javax.swing.JLabel puntosP3;
    private javax.swing.JLabel puntosP4;
    private javax.swing.JLabel turnosP1;
    private javax.swing.JLabel turnosP2;
    private javax.swing.JLabel turnosP3;
    private javax.swing.JLabel turnosP4;
    // End of variables declaration//GEN-END:variables
}
