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
        "X...........X.....X",
        "X.XXXX.XXXX.X.XXX.X",
        "X.XXXX.XXXX.X.XXX.X",
        "X..................X",
        "X.XXXX.X.XXXXX.XXXX",
        "X.....X...X........X",
        "XXXXX.XXX.XXXXXXX.XX",
        "XXXXX.XXXXXXXXXXX.XX",
        "XXXXX.XXXX.P..A...XX", // Fantasma Azul (A)
        "XXXXX.XXXX.XXXXXXXXX",
        "X........X...N.....X", // Fantasma Naranja (N)
        "X.XXXX.XX.XXXXXX.XX",
        "X.....XX.....X.R..X", // Fantasma Rosa (R)
        "XXXXX.XX.XXXXX.XX.XX",
        "X.....X......J.....X", // Fantasma Rojo (J)
        "XXXXXXXXXXXXXXXXXXX"
    };
    
    // Medidas
    private int contFilas = 21;
    private int contColumnas = 19;
    private int marcoTamanio = 32;
    private int anchoBorde = contColumnas * marcoTamanio;
    private int largoBorde = contFilas * marcoTamanio;

    // Fantasmas
    private Image muroImagen;
    private Image fantasmaAzul;
    private Image fantasmaRosa;
    private Image fantasmaNaranja;
    private Image fantasmaRojo;

    // Pacman
    private Image pacmanPrincipal;

    //HashSet<Bloque> muros;
    //HashSet<Bloque> comidas;
    //HashSet<Bloque> fantasmas;
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

        mostrarMapa();
    }
    public void mostrarMapa(){
        for (int i = 0; i < contFilas; i++){
            for (int j = 0; j < contColumnas; j++){
                int x = j * marcoTamanio;
                int y = i * marcoTamanio;
                pacman = new Bloque(x,y,marcoTamanio, marcoTamanio, pacmanPrincipal);
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        draw(g);
    }
    public void draw(Graphics g){
        g.drawImage(pacman.imagen, pacman.x, pacman.y, pacman.ancho, pacman.largo, null);
    }
}


