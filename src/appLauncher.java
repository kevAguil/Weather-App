import javax.swing.*;

public class appLauncher {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run(){
                // Display weather app gui
                new weatherAppGUI().setVisible(true);

            }
        });
    }


    
}
