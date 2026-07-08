package pe.utp.micromouse.app;

import pe.utp.micromouse.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class MicroMouseApp {
    private MicroMouseApp() { }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
