import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        int contFilas = 21;
        int contColumnas = 19;
        int marcoTamanio = 32;
        int anchoBorde = contColumnas * marcoTamanio;
        int largoBorde = contFilas * marcoTamanio;
        JFrame ventana = new JFrame("Pac Man");
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false); // El usuario no podrá ampliar la ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(anchoBorde, largoBorde);
    }
}
