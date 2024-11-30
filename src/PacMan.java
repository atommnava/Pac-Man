import javax.swing.*; //Componentes gráficos
import java.awt.*; //Clases de componentes gráficos
import java.awt.event.ActionEvent; //Acciones
import java.awt.event.ActionListener; //Recibir acciones
import java.awt.event.KeyEvent; //Representar un evento de teclado
import java.awt.event.KeyListener; //Escuchar eventos de teclado
import java.util.HashSet; //Almacena elementos únicos (sin duplicados)
import java.util.Random;

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

        // Actualizar la dirección en la que se mueve Pac-Man, ajustar su velocidad en función de esa dirección y cambiar su imagen para reflejar el nuevo movimiento.
        public void nuevaDireccion(char direccion) {
            char prevDir = this.direccion;
            this.direccion = direccion;
            nuevaVelocidad(); //velocidad de pac-man en función de su posición (x,y)

            this.x += this.velocidadX;
            this.y += this.velocidadY;
            for (Bloque muro : muros) {
                if (colision(this, muro)) {
                    this.x -= this.velocidadX;
                    this.y -= this.velocidadY;
                    this.direccion = prevDir;
                    nuevaVelocidad();
                }
            }
        }


        //Ajustar las velocidades de Pac-Man en los ejes X e Y dependiendo de la dirección en la que se mueve
        public void nuevaVelocidad(){
            //Comprueba la dirección actual
            if (this.direccion == 'U') {
                this.velocidadX = 0;
                this.velocidadY = (-marcoTamanio / 4); //Establece la velocidad en función del tamaño de los bloques del juego.
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

            // Cambiar la imagen de Pac-Man según la dirección
            if (pacman.direccion == 'U') {
                pacman.imagen = pacmanPrincipal;  // Pac-Man mirando hacia arriba
            } else if (pacman.direccion == 'D') {
                pacman.imagen = pacmanAbajo;   // Pac-Man mirando hacia abajo
            } else if (pacman.direccion == 'L') {
                pacman.imagen = pacmanIzquierda; // Pac-Man mirando hacia la izquierda
            } else if (pacman.direccion == 'R') {
                pacman.imagen = pacmanDerecha;  // Pac-Man mirando hacia la derecha
            }
        }

        public void reiniciar(){
            this.x = this.inicioX;
            this.y = this.inicioY;
        }
    }

    // ******************************************************
    // *                  Mecánica principal                *
    // ******************************************************

    // Mapa
    private String[] mapa = {
            "XXXXXXXXXXXXXXXXXXX",
            "X           X      X",
            "X  XXX XXXX X  XX X",
            "X  XXX XXXX X  XX X",
            "X                 X",
            "X XXXX X XXXXX  XXX",
            "X      X   X      X",
            "X XXX XXX XXXXXXX X",
            "X XXX  XX X XXXXX X",
            "X      XX  P   A  X", // Fantasma Azul (A)
            "XXXX   XXX XXXX  XX",
            "X          N      X", // Fantasma Naranja (N)
            "X XXXX XX  XXXXX XX",
            "X      X     X R  X", // Fantasma Rosa (R)
            "XXXXX XX XXXXX XX X",
            "X     X      J    X", // Fantasma Rojo (J)
            "XX  XXX  XXX  XXX X",
            "X   X    XX   X   X",
            "X X X  XXXX X X X X",
            "X X             X X",
            "XXXXXXXXXXXXXXXXXXX",
    };


    // Medidas
    private int contFilas = 21;
    private int contColumnas = 19;
    private int marcoTamanio = 32;
    private int anchoBorde = contColumnas * marcoTamanio;
    private int largoBorde = contFilas * marcoTamanio;

    // Variables para almacenar las imagenes
    //Muro
    private Image muroImagen;
    //Fantasmas
    private Image fantasmaAzul;
    private Image fantasmaRosa;
    private Image fantasmaNaranja;
    private Image fantasmaRojo;
    //Pacman posiciones
    private Image pacmanPrincipal;
    private Image pacmanAbajo;
    private Image pacmanIzquierda;
    private Image pacmanDerecha;


    HashSet<Bloque> muros; //almacena todos los bloques que representan los muros en el juego, evitando que se repitan.
    HashSet<Bloque> comidas; //garantiza que no haya dos bloques con comida en la misma posición.
    HashSet<Bloque> fantasmas;
    Bloque pacman;
    Timer timer;
    public int vidas = 3;
    public int puntaje = 0;
    public boolean finDelJuego = false;
    public char direcciones[] = {'U','R','L','D'};
    Random rand = new Random();

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

        // Para que los Fantasmas vayan en direcciones aleatorias
        for (Bloque fantasma : fantasmas) {
            char nuevaDireccion = direcciones[rand.nextInt(4)];
            fantasma.nuevaDireccion(nuevaDireccion);
        }
        timer = new Timer(50, this);
        timer.start();
        System.out.println("Paredes " + muros.size());
        System.out.println("Comidas " + comidas.size());
        System.out.println("Fantasmas " + fantasmas.size());
    }

    public void mostrarMapa() {
        muros = new HashSet<>();//Conjunto vacío para muros
        comidas = new HashSet<>(); //Conjunto vacío para comidas
        fantasmas = new HashSet<>(); //Conjunto vacío para fantasmas

        for (int i = 0; i < contFilas; i++) { //recorre filas del mapa
            for (int j = 0; j < contColumnas; j++) { //recorre columnas del mapa
                String fila = mapa[i]; //obtiene el texto de la fila actual
                char mapa = fila.charAt(j); //obtiene el caracter en la posición j de la cadena
                //Coordenadas gráficas en pixeles
                int x = j * marcoTamanio;
                int y = i * marcoTamanio;

                //Interpreta los caracteres del mapa y crea objetos del tipo Bloque para representar los elementos
                if (mapa == 'X') { //Crea un objeto Bloque en la posición (x, y) con tamaño marcoTamanio x marcoTamanio.
                    Bloque muro = new Bloque(x, y, marcoTamanio, marcoTamanio, muroImagen);
                    muros.add(muro); //Se añade al conjunto de muros
                } else if (mapa == ' ') { //Crea un objeto Bloque que representa comida, ubicado un poco centrado dentro del cuadro (x + 14, y + 14).
                    Bloque comida = new Bloque(x + 14, y + 14, 4, 4, null);
                    comidas.add(comida); //Se añade al conjunto
                } else if (mapa == 'P') { //Crea un objeto Pac-man
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

    //Controla cómo se mueve Pac-Man y verifica si puede avanzar sin chocar con un muro
    public void mover() {
        int nuevaX = pacman.x + pacman.velocidadX;
        int nuevaY = pacman.y + pacman.velocidadY;

        // COLISIONES entre pacman, comida, fantasma y muros

        // Verificar si la nueva posición de Pacman colide con un muro
        boolean colisionConMuro = false;

        //Recorre el conjunto de muros
        for (Bloque muro : muros) {
            // Verificar si Pacman colisiona con algún muro
            //Pac-Man está a la izquierda del muro: nuevaX < muro.x + muro.ancho
            //Pac-Man está a la derecha del muro: nuevaX + pacman.ancho > muro.x
            //Pac-Man está por encima del muro: nuevaY < muro.y + muro.largo
            //Pac-Man está por debajo del muro: nuevaY + pacman.largo > muro.y

            if (nuevaX < muro.x + muro.ancho && nuevaX + pacman.ancho > muro.x &&
                    nuevaY < muro.y + muro.largo && nuevaY + pacman.largo > muro.y) {
                colisionConMuro = true;
                break; // Si colisiona, no se mueve
            }
        }

        // Si no hay colisión, permite que Pacman se mueva
        if (!colisionConMuro) {
            pacman.x = nuevaX;
            pacman.y = nuevaY;
        }

        // Interacción pacman-fantasma
        for (Bloque fantasma : fantasmas) {
            if (colision(pacman, fantasma)) {
                vidas -= 1;
                if (vidas <= 0) {
                    finDelJuego = true;
                    return;
                }
                reiniciarPos();
            }
            if (fantasma.y == marcoTamanio * 9 && fantasma.direccion != 'U' && fantasma.direccion != 'D') {
                fantasma.nuevaDireccion('U');
            }
            fantasma.x += fantasma.velocidadX;
            fantasma.y += fantasma.velocidadY;
            for (Bloque muro : muros) {
                if (colision(fantasma, muro) || fantasma.x <= 0 || fantasma.x + fantasma.ancho >= anchoBorde) {
                    fantasma.x -= fantasma.velocidadX;
                    fantasma.y -= fantasma.velocidadY;
                    char nuevaDireccion = direcciones[rand.nextInt(4)];
                    fantasma.nuevaDireccion(nuevaDireccion);
                }
            }
        }

        // Interacción pacman-comida
        Bloque puntoComido = null; // Punto comido en NULL porque empeiza desde 0
        for (Bloque comida : comidas) {
            if (colision(pacman, comida)) {
                puntoComido = comida;
                puntaje = puntaje + 10;
            }
        }
        comidas.remove(puntoComido);
    }

    // Método parac comprobar la realación de colisioens pacman - comida y pacman - fantasma
    public boolean colision(Bloque a, Bloque b){
        return a.x < b.x + b.ancho &&
                a.x + a.ancho > b.x &&
                a.y < b.y + b.ancho &&
                a.y + a.ancho > b.y;
    }

    //Se dibujan los elementos en pantalla
    public void draw(Graphics g) {
        g.drawImage(pacman.imagen, pacman.x, pacman.y, pacman.ancho, pacman.largo, null);
        //Recorre cada bloque del muro y lo dibuja
        for (Bloque muro : muros) {
            g.drawImage(muro.imagen, muro.x, muro.y, muro.ancho, muro.largo, null);
        }
        //Recorre cada fantasma y los dibuja
        for (Bloque fantasma : fantasmas) {
            g.drawImage(fantasma.imagen, fantasma.x, fantasma.y, fantasma.ancho, fantasma.largo, null);
        }
        g.setColor(Color.white); //establece el color de las comidas
        for (Bloque comida : comidas) {
            g.fillRect(comida.x, comida.y, comida.ancho, comida.largo); //dibuja un rectángulo
        }
        // Muestra el puntaje y las vidas en la parte superior de la pantalla.
        g.setFont(new Font("Raleway", Font.PLAIN, 24)); //Tipo de letras y tamaño de fuente
        if (finDelJuego) {
            // Perdiste
            g.drawString("GAME OVER",275,300);
        } else {
            // No has perdido
            g.drawString("❤" + String.valueOf(vidas) + " Puntaje: " + String.valueOf(puntaje),25,20);
        }
    }

    public void reiniciarPos()
    {
        pacman.reiniciar();
        pacman.velocidadX = 0;
        pacman.velocidadY = 0;
        for (Bloque fantasma : fantasmas) {
            fantasma.reiniciar();
            char nuevaDireccion = direcciones[rand.nextInt(4)];
            fantasma.nuevaDireccion(nuevaDireccion);
        }
    }

    //Dibujar panel de la interfaz gráfica
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //Pintarse correctamente
        draw(g); //Se dibuja
    }

    //Maneja los eventos de teclas que se presionan
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Maneja los eventos de tecla presionada.
    @Override
    public void keyPressed(KeyEvent e) {

    }

    //Se activa cuando el usuario suelta una tecla del teclado
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("KeyEvent: " + e.getKeyCode()); // Para ver la tecla presionada

        if (finDelJuego) {
            finDelJuego = false;
        }
        //Compara el código de la tecla que se tecleó para realizar su acción específica
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
        if (finDelJuego)  {
            timer.stop();
        }
    }
}
