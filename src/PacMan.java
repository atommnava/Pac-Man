import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class PacMan extends JPanel implements KeyListener, ActionListener {

    class Bloque {
        int x;
        int y;
        int ancho;
        int largo;
        Image imagen;

        int inicioX;
        int inicioY;
        int velocidadX = 0;
        int velocidadY = 0;
        /* Direcciones
         * U = Up
         * D = Down
         * R = Right
         * L = Left
         */
        char direccion = 'U';
        Bloque(int x, int y, int ancho, int largo, Image imagen) {
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.largo = largo;
            this.imagen = imagen;
            this.inicioX = x;
            this.inicioY = y;
        }
        public void nuevaDireccion(char direccion) {
            this.direccion = direccion;
            nuevaVelocidad();

            // Cambiar la imagen de Pac-Man según la dirección
            if (this.direccion == 'U') {
                pacman.imagen = pacmanPrincipal;  // Pac-Man mirando hacia arriba
            } else if (this.direccion == 'D') {
                pacman.imagen = pacmanAbajo;   // Pac-Man mirando hacia abajo
            } else if (this.direccion == 'L') {
                pacman.imagen = pacmanIzquierda; // Pac-Man mirando hacia la izquierda
            } else if (this.direccion == 'R') {
                pacman.imagen = pacmanDerecha;  // Pac-Man mirando hacia la derecha
            }
        }

        public void nuevaVelocidad(){
            if (this.direccion == 'U') {
                this.velocidadX = 0;
                this.velocidadY = (-marcoTamanio / 4);
            } else if (this.direccion == 'D') {
                this.velocidadX = 0;
                this.velocidadY = (marcoTamanio / 4);
            } else if (this.direccion == 'L') {
                this.velocidadX = (-marcoTamanio / 4);
                this.velocidadY = 0;
            } else if (this.direccion == 'R') {
                this.velocidadX = (marcoTamanio / 4);
                this.velocidadY = 0;
            }
            if (pacman.direccion == 'U') {
                pacman.imagen = pacmanPrincipal;
            }
        }
    }

    // ******************************************************
    // *                  Mecánica principal                *
    // ******************************************************

    // Mapa
    private String[] mapa = {
            "XXXXXXXXXXXXXXXXXXX",
            "X          X      X",
            "X XXXX XXXX X XXX X",
            "X XXXX XXXX X XXX X",
            "X                 X",
            "X XXXX X XXXXX XXXX",
            "X      X   X      X",
            "X XXX XXX XXXXXXX X",
            "X XXX XXX X XXXXX X",
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
    private Image pacmanAbajo;
    private Image pacmanIzquierda;
    private Image pacmanDerecha;


    HashSet<Bloque> muros;
    HashSet<Bloque> comidas;
    HashSet<Bloque> fantasmas;
    Bloque pacman;
    Timer timer;
    public int vidas = 3;
    public int puntaje = 0;
    public boolean finDelJuego = false;
    public char direcciones[] = {'U','R','L','D'};

    public PacMan() {
        setPreferredSize(new Dimension(anchoBorde, largoBorde));
        setBackground(new Color(0,0,0));
        addKeyListener(this);
        setFocusable(true);

        // Cargando las imagenes
        muroImagen = new ImageIcon(getClass().getResource("./cuadro.png")).getImage();

        pacmanPrincipal = new ImageIcon(getClass().getResource("./pacmanArriba.png")).getImage();
        pacmanAbajo = new ImageIcon(getClass().getResource("./pacmanAbajo.png")).getImage();
        pacmanIzquierda = new ImageIcon(getClass().getResource("./pacmanIzquierda.png")).getImage();
        pacmanDerecha = new ImageIcon(getClass().getResource("./pacmanDerecha.png")).getImage();

        fantasmaAzul = new ImageIcon(getClass().getResource("./fantasmaAzul.png")).getImage();
        fantasmaRosa = new ImageIcon(getClass().getResource("./fantasmaRosa.png")).getImage();
        fantasmaNaranja = new ImageIcon(getClass().getResource("./fantasmaNaranja.png")).getImage();
        fantasmaRojo = new ImageIcon(getClass().getResource("./fantasmaRojo.png")).getImage();
        mostrarMapa();
        timer = new Timer(50, this);
        timer.start();
    }

    public void mostrarMapa() {
        muros = new HashSet<>();
        comidas = new HashSet<>();
        fantasmas = new HashSet<>();

        for (int i = 0; i < contFilas; i++) {
            for (int j = 0; j < contColumnas; j++) {
                String fila = mapa[i];
                char mapa = fila.charAt(j);
                int x = j * marcoTamanio;
                int y = i * marcoTamanio;

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

    public void mover(){
        pacman.x += pacman.velocidadX;
        pacman.y += pacman.velocidadY;
    }

    public void draw(Graphics g) {
        g.drawImage(pacman.imagen, pacman.x, pacman.y, pacman.ancho, pacman.largo, null);
        for (Bloque muro : muros) {
            g.drawImage(muro.imagen, muro.x, muro.y, muro.ancho, muro.largo, null);
        }
        for (Bloque fantasma : fantasmas) {
            g.drawImage(fantasma.imagen, fantasma.x, fantasma.y, fantasma.ancho, fantasma.largo, null);
        }
        g.setColor(Color.white);
        for (Bloque comida : comidas) {
            g.fillRect(comida.x, comida.y, comida.ancho, comida.largo);
        }
        // Para ver el puntaje
        g.setFont(new Font("Raleway", Font.PLAIN, 24));
        if (finDelJuego) {
            // Perdiste
            g.drawString("Perdiste",25,20);
        } else {
            // No has perdido
            g.drawString("❤" + String.valueOf(vidas) + " Puntaje: " + String.valueOf(puntaje),25,20);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("KeyEvent: " + e.getKeyCode()); // Para ver la tecla presionada
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.nuevaDireccion('U');
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.nuevaDireccion('D');
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.nuevaDireccion('L');
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.nuevaDireccion('R');
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mover();
        repaint(); // Repintar nuestro frame cada vez que nos movemos
    }
}
