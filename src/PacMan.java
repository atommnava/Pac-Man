import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class PacMan extends JPanel {
    class Bloque {
        int x;
        int y;
        int ancho;
        int largo;
        Image imagen;
        Bloque(int x, int y, int ancho, int largo, Image imagen) {
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.largo = largo;
            this.imagen = imagen;
        }
    }
    // Mapa
    private String[] mapa = {
            "XXXXXXXXXXXXXXXXXXX",
            "X          X      X",
            "X XXXX XXXX X XXX X",
            "X XXXX XXXX X XXX X",
            "X                 X",
            "X XXXX X XXXXX.XXXX",
            "X      X   X      X",
            "X XXX XXX XXXXXXX X",
            "X XXX XXX XXXXXXX.X",
            "X     XXX  P  A   X", // Fantasma Azul (A)
            "XXXX   XXX XXXX  XX",
            "X          N      X", // Fantasma Naranja (N)
            "X XXXX XX  XXXXX XX",
            "X      X     X R  X", // Fantasma Rosa (R)
            "XXXXX XX XXXXX XX X",
            "X     X      J    X", // Fantasma Rojo (J)
            "XX  XXX XXXX  XXX X",
            "X   X   XXX   X   X",
            "X X X XXXXX X X X X",
            "X X             X X",
            "XXXXXXXXXXXXXXXXXXX",
    };


    // Medidas
    private int contFilas = 21;
    private int contColumnas = 19;
    private int marcoTamanio = 32;
    private int anchoBorde = contColumnas * marcoTamanio;
    private int largoBorde = contFilas * marcoTamanio;

    // Imagenes
    private Image muroImagen;
    private Image fantasmaAzul;
    private Image fantasmaRosa;
    private Image fantasmaNaranja;
    private Image fantasmaRojo;

    // Pacman
    private Image pacmanPrincipal;

    HashSet<Bloque> muros;
    HashSet<Bloque> comidas;
    HashSet<Bloque> fantasmas;
    Bloque pacman;
    Timer timer;
    public int vidas = 3;
    public int puntaje = 0;
    public boolean finDelJuego = false;

    public PacMan() {
        setPreferredSize(new Dimension(anchoBorde, largoBorde));
        setBackground(new Color(0,0,0));
        setFocusable(true);

        // Cargando las imagenes
        pacmanPrincipal = new ImageIcon(getClass().getResource("./pacmanArriba.png")).getImage();
        muroImagen = new ImageIcon(getClass().getResource("./cuadro.png")).getImage();
        fantasmaAzul = new ImageIcon(getClass().getResource("./fantasmaAzul.png")).getImage();
        fantasmaRosa = new ImageIcon(getClass().getResource("./fantasmaRosa.png")).getImage();
        fantasmaNaranja = new ImageIcon(getClass().getResource("./fantasmaNaranja.png")).getImage();
        fantasmaRojo = new ImageIcon(getClass().getResource("./fantasmaRojo.png")).getImage();
        mostrarMapa();
    }

    public void mostrarMapa() {
        muros = new HashSet<>();
        comidas = new HashSet<>();
        fantasmas = new HashSet<>();

        for (int i = 0; i < contFilas; i++) {
            for (int j = 0; j < contColumnas; j++) {
                int x = j * marcoTamanio;
                int y = i * marcoTamanio;
                String fila = mapa[i];
                char mapa = fila.charAt(j);

                if (mapa == 'X') {
                    Bloque muro = new Bloque(x, y, marcoTamanio, marcoTamanio, muroImagen);
                    muros.add(muro);
                } else if (mapa == ' ') {
                    Bloque comida = new Bloque(x + 14, y + 14, 4, 4, null);
                    comidas.add(comida);
                } else if (mapa == 'P') {
                    pacman = new Bloque(x, y, marcoTamanio, marcoTamanio, pacmanPrincipal);
                } else if (mapa == 'A') {
                    Bloque fantasmaA = new Bloque(x, y, marcoTamanio, marcoTamanio, fantasmaAzul);
                    fantasmas.add(fantasmaA);
                } else if (mapa == 'N') {
                    Bloque fantasmaN = new Bloque(x, y, marcoTamanio, marcoTamanio, fantasmaNaranja);
                    fantasmas.add(fantasmaN);
                } else if (mapa == 'R') {
                    Bloque fantasmaR = new Bloque(x, y, marcoTamanio, marcoTamanio, fantasmaRosa);
                    fantasmas.add(fantasmaR);
                } else if (mapa == 'J') {
                    Bloque fantasmaJ = new Bloque(x, y, marcoTamanio, marcoTamanio, fantasmaRojo);
                    fantasmas.add(fantasmaJ);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(pacman.imagen, pacman.x, pacman.y, pacman.ancho, pacman.largo, null);
        for (Bloque muro : muros) {
            g.drawImage(muro.imagen, muro.x, muro.y, muro.ancho, muro.largo, null);
        }
        for (Bloque fantasma : fantasmas) {
            g.drawImage(fantasma.imagen, fantasma.x, fantasma.y, fantasma.ancho, fantasma.largo, null);
        }
    }
}
