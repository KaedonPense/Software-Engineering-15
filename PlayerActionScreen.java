// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

public class PlayerActionScreen extends JFrame {
    //Misc
    JFrame frame;
    JLabel countdownLabel;
    static JPanel gamePanel;
    static GridBagConstraints cons;
    Team greenTeam;
    Team redTeam;
    public static Semaphore timerSem = new Semaphore(1, true);
    Timer timer;
    GridBagConstraints timerGrid;
    //Colors
    static Color red = new Color(200,0,0);      //main red team color
    static Color green = new Color(0,200,0);    //main green team color
    static Color darkRed = red.darker();        //alt red
    static Color darkGreen = green.darker();    //alt green
    static Color gray = new Color(100,100,100); 
    static Color backgroundColor = gray.darker();      //main background color
    //Fonts
    Font headingFont = new Font("impact", Font.ITALIC, 35);
    Font columnHeadingFont = new Font("times new roman", Font.PLAIN, 15);
    Font textFieldFont = new Font("times new roman" , Font.PLAIN, 12);
    
    // Constructor
    PlayerActionScreen(JFrame frame) 
    {
        countdownLabel = new JLabel();
        gamePanel = new JPanel(new GridBagLayout()); //main countdownPanel layout  
        gamePanel.setBackground(backgroundColor);     //Background color
        //gamePanel.setSize(1000,1000);
        timerGrid = new GridBagConstraints();
        timerGrid.fill = GridBagConstraints.BOTH;
        timerGrid.gridwidth = 2;
    }

    // Sets teams
    void setTeams(Team gt, Team rt) 
    {
        greenTeam = setDemoTeam(green, darkGreen);
        redTeam = setDemoTeam(red, darkRed); 
    }

    // TEMPORARY: adds some dummy players for testing purposes
    Team setDemoTeam(Color color1, Color color2) 
    {
        Team team = new Team(color1, color2);
        String name;
        if (color1 == green) 
        {
            name = "Green";
        } 
        else 
        {
            name = "Red";
        }
        for (int i = 0; i < 15; i++) 
        {
            Player tmp = new Player("0" + i, name + i, "0" + i, team);
            team.players.add(tmp);
        }
        team.createTableFromArray();
        team.updateTable();
        return team;
    }

    void setTeamPanels() 
    {
        greenTeam.createTableFromArray();
        redTeam.createTableFromArray();
    }

    void addPanelsToFrame() 
    {
        cons = new GridBagConstraints();
        // Sets values for countdownPanel
        cons.fill = GridBagConstraints.BOTH;
        cons.gridy = 1;
        greenTeam.addToPanel(gamePanel, cons);
        cons.gridx = 1;
        redTeam.addToPanel(gamePanel, cons);
        gamePanel.revalidate(); 
    }

    void setPanels() 
    {
        // Sets values for team panels
        addPanelsToFrame();
        // Sets values for countdownLabel
        
        //countdownLabel.setVisible(true);
    }

    // Creates a countdown screen
    void start() 
    { 
        gamePanel.setBackground(backgroundColor);
        setPanels();
        try
        {
            timerSem.acquire();
            timer = new Timer(35);
            timer.timerText.setFont(headingFont.deriveFont(Font.PLAIN, 70f));
            timer.timerText.setForeground(Color.WHITE);
            gamePanel.add(timer.timerText,timerGrid);
            timer.start();
            
        }
        catch(InterruptedException e)
        {
            System.out.println("Cannot create new timer");
        }
    }

    void update() {
        if(timer.getTime() == 30)
        {
            System.out.println("30 seconds until game start");
        }
        boolean isGameOver = false;
            if (isGameOver) {}
        gameOver();
    }

    void gameOver() {
        System.out.println("Game Over");
        System.exit(0);
    }
    void visible(JFrame frame, boolean addRemove)
    {
        if(addRemove == true)
            frame.setContentPane(gamePanel);
        else
            frame.remove(gamePanel);
    }
}