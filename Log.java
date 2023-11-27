import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Log extends JFrame
{
    JFrame log;
    JPanel panel;
    JScrollPane scroll;
    int row = 0;
    GridBagConstraints grid = new GridBagConstraints();
    Log()
    {
        System.out.println("new Log");
        log = new JFrame("Message Log");
        log.setSize(500,1000);
        //log.setLayout(new GridBagLayout());
        log.setVisible(true);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        grid.gridx = 0;
        grid.gridy = row;
        panel.setVisible(true);
        scroll = new JScrollPane(panel);
        log.add(scroll);
    }
    public void tagLog(String Tagger, String Tagged)
    {
        JLabel event = new JLabel();
        event.setText(Tagger + " hit player " + Tagged + " and has scored 10 points");
        panel.add(event, grid);
        this.revalidate();
        row = row + 1;
        grid.gridy = row;
        this.pack();
    }
    public void greenBase(String Tagger)
    {
        JLabel event = new JLabel();
        event.setText(Tagger + " hit the greenBase 3 times and has scored 100 points");
        panel.add(event, grid);
        this.revalidate();
        row = row + 1;
        grid.gridy = row;
        this.pack();
    }
    public void redBase(String Tagger)
    {
        JLabel event = new JLabel();
        event.setText(Tagger + " hit the redBase 3 times and has scored 100 points");
        panel.add(event, grid);
        this.revalidate();
        row = row + 1;
        grid.gridy = row;
        this.pack();
    }
    public void friendlyFire(String Tagger)
    {
        JLabel event = new JLabel();
        event.setText(Tagger + " has hit a teammate and has been timedout");
        panel.add(event, grid);
        this.revalidate();
        row = row + 1;
        grid.gridy = row;
        this.pack();
    }
    public void close()
    {
        this.setVisible(false);
        this.dispose();
    }
}