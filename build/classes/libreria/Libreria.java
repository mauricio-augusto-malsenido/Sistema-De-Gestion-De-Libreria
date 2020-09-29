package libreria;

import javax.swing.JFrame;

/**
 *
 * @author SEBASTIAN
 */
public class Libreria {

    public static void main(String[] args) {
        // TODO code application logic here
        ventanaPrincipal vp= new ventanaPrincipal();
        vp.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vp.setVisible(true);
    }
}
