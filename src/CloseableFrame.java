

import javax.swing.*;

import java.awt.event.*;

public class CloseableFrame
        extends JFrame
        implements WindowListener {
    public CloseableFrame() {
        super();
        addWindowListener(this);
    }

    public CloseableFrame(String str) {
        super(str);
        addWindowListener(this);
    }

    public void
    windowClosed(WindowEvent event) {
    }

    public void
    windowDeiconified(WindowEvent event) {
    }

    public void
    windowIconified(WindowEvent event) {
    }

    public void
    windowActivated(WindowEvent event) {
    }

    public void
    windowDeactivated(WindowEvent event) {
    }

    public void
    windowOpened(WindowEvent event) {
    }

    public void
    windowClosing(WindowEvent event) {
        // dispose of the JFrame object
        dispose();
        // terminate the program
        System.exit(0);
    }
}