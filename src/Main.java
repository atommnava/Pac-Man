import javax.swing.*; //Biblioteca gráfica

public class Main {
    public static void main(String[] args) {

        //Área visible en la ventana (mapa)
        int contFilas = 21; //Filas
        int contColumnas = 19; //Columnas
        int marcoTamanio = 32; // Tamaño de pixeles que abarca cada celda
        int anchoBorde = contColumnas * marcoTamanio; //Ancho total
        int largoBorde = contFilas * marcoTamanio; //Largo total

        //Creación de ventana con el título "Pac Man"
        JFrame frame = new JFrame("Pac Man");

        //Configuración de la ventana
        frame.setLocationRelativeTo(null); //Coloca la ventana en el centro de la pantalla
        frame.setResizable(false); // Impide que el usaurio no pueda ampliar la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Al cerrar la ventana, el programa se detiene

        //Define el tamaño de la ventana con lo calculado: 600 * 700 px
        frame.setSize(anchoBorde, largoBorde);

        //Creación de objeto PacMan
        PacMan pacman = new PacMan();
        frame.add(pacman); //Se agrega a la ventana como componente visual
        frame.pack(); //Asegura que la ventana se ajuste a sus medidas
        pacman.requestFocus(); //Permite que pacman pueda recibir órdenes de teclado
        frame.setVisible(true); //Hacer visible la ventana
    }
}
