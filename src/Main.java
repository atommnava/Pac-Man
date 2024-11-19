import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int contFilas = 21;
        int contColumnas = 19;
        int marcoTamanio = 32; // N pixeles que abarca
        int anchoBorde = contColumnas * marcoTamanio;
        int largoBorde = contFilas * marcoTamanio;
        JFrame frame = new JFrame("Pac Man");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // El usaurio no podrá ampliar la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 600 * 700 px
        frame.setSize(anchoBorde, largoBorde);

        PacMan pacman = new PacMan();
        frame.add(pacman);
        frame.pack();
        pacman.requestFocus();
        frame.setVisible(true);
    }
}
