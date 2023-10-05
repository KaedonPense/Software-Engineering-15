/*
    @This: Start Screen class
        @Version: 1.0                      //#.#
        @State: WIP                        //test, alpha, workInProgress, buggy, final
    @Who
        @Author(s): Gabby Simancas                     //name, name
        @Editor(s): 
    @Last Edited
        @Date: 9/28                         
        @By: Gabby Simancas
        @What: Creation
    @Description:
        @Use: Use run() to make splash screen come up for 3 seconds
        @Has Access to: JFrame passed in as parameter
*/

import javax.swing.*;

public class StartScreen extends JFrame {

    //Member variables
    ImageIcon icon;
    JFrame frame;
    JLabel lbl;

    //Constructor
    public StartScreen(JFrame frame) {
        this.frame = frame;
        icon = new ImageIcon("./logo.jpg");
    }

    //Makes splash screen appear for 3 seconds
    void run() {
        lbl = new JLabel(icon);
        frame.add(lbl);
        frame.setSize(750,500);
        frame.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
