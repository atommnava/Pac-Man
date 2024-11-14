import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel {
    public int contFilas = 21;
    public int contColumnas = 19;
    private int marcoTamanio = 32;
    private int anchoBorde = contColumnas * marcoTamanio;
    private int largoBorde = contFilas * marcoTamanio;

    private Image ventanaImagen;
    private Image fantasmaAzul;
    private Image fantasmaRosa;
    private Image fantasmaNaranja;
    private Image fantasmaRojo;

    private Image pacmanArriba;
    private Image pacmanAbajo;
    private Image pacmanIzquierda;
    private Image pacmanDerecha;

    public String[] mapa = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    HashSet<Bloque> paredes;
    HashSet<Bloque> comidas;
    HashSet<Bloque> fantasmas;
    Bloque pacman;
    public PacMan(){
        setPreferredSize(new Dimension(anchoBorde,largoBorde));
        setBackground(new Color(0,0,0));

        // para cargar las imagenes
        ventanaImagen = new ImageIcon(getClass().getResource("./muro.png")).getImage();
        fantasmaAzul = new ImageIcon(getClass().getResource("./fantasmaAzul.png")).getImage();
        fantasmaRosa = new ImageIcon(getClass().getResource("./fantasmaRosa.png")).getImage();
        fantasmaRojo = new ImageIcon(getClass().getResource("./fantasmaRojo.png")).getImage();
        fantasmaNaranja = new ImageIcon(getClass().getResource("./fantasmaNaranja.png")).getImage();

        pacmanArriba = new ImageIcon(getClass().getResource("./pacmanArriba.png")).getImage();
        pacmanAbajo = new ImageIcon(getClass().getResource("./pacmanAbajo.png")).getImage();
        pacmanIzquierda = new ImageIcon(getClass().getResource("./pacmanIzquierda.png")).getImage();
        pacmanDerecha = new ImageIcon(getClass().getResource("./pacmanDerecha.png")).getImage();
    }
}

class Bloque extends PacMan{
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
    public void mostrarMapa(){
        paredes = new HashSet<Bloque>();
        comidas = new HashSet<Bloque>();
        fantasmas = new HashSet<Bloque>();

        // Iterando en el mapa
        for (int i = 0; i < contFilas; i++) {
            for (int j = 0; j < contColumnas; j++) {
                String fila = mapa[i];
                // ...
            }
        }
    }
}
