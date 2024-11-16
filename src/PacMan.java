import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel {
    class Bloque {
        int x;
        int y;
        int ancho;
        int largo;
        Image imagen;

        int inicioX;
        int inicioY;
        Bloque(int x, int y, int ancho, int largo, Image imagen){
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.largo = largo;
            this.imagen = imagen;
            this.inicioX = x;
            this.inicioY = y;
        }
    }
    public int contFilas = 21;
    public int contColumnas = 19;
    public int marcoTamanio = 32;
    private int anchoBorde = contColumnas * marcoTamanio;
    private int largoBorde = contFilas * marcoTamanio;

    public Image paredImagen;
    public Image fantasmaAzul;
    public Image fantasmaRosa;
    public Image fantasmaNaranja;
    public Image fantasmaRojo;

    public Image pacmanArriba;
    public Image pacmanAbajo;
    public Image pacmanIzquierda;
    public Image pacmanDerecha;

    public String[] mapa = {
          //MAPA
    };

  
