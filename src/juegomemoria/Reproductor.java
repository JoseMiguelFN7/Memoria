package juegomemoria;

import java.io.File;
import javax.sound.sampled.*;

public class Reproductor { //Clase que contiene los metodos para reproducir los sonidos.
    
    public static void flip1() {
        try{
            File archivo = new File("Flip1.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static void flip2() {
        try{
            File archivo = new File("Flip2.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static void correct() {
        try{
            File archivo = new File("Correct.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static void end() {
        try{
            File archivo = new File("End.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static void endMusic() {
        try{
            File archivo = new File("EndMusic.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static void ohhh() {
        try{
            File archivo = new File("Ohhh.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static void cheers() {
        try{
            File archivo = new File("Cheers.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static void welcome() {
        try{
            File archivo = new File("Welcome.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
        } catch(Exception q){}
    }
    
    public static Clip playingMusic() {
        Clip s = null;
        try{
            File archivo = new File("PlayMusic.wav");
            AudioInputStream AUI = AudioSystem.getAudioInputStream(archivo);
            Clip sonido = AudioSystem.getClip();
            sonido.open(AUI);
            sonido.start();
            s = sonido;
        } catch(Exception q){}
        return s;
    }
    
    public static void stopPlayingMusic(Clip sonido) {
        try{
            sonido.stop();
        } catch(Exception q){}
    }
}
