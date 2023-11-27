import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class PlayerActionScreen extends JFrame implements KeyListener {
    //Misc
    static JPanel gamePanel;
    static GridBagConstraints cons;
    public static Semaphore timerSem = new Semaphore(1, true);
    static String startSignal = "202";
    static String endSignal = "221";
	static String redBaseHit = "53";
	static String greenBaseHit = "43";
    JFrame frame;
    JLabel countdownLabel;
	 
    static Team greenTeam;
    static Team redTeam;
    JPanel heading;

    Timer timer;
    Music music = new Music();
    boolean transmitted;
    static Log log;
    GridBagConstraints timerGrid;
    boolean inStartup;
	 static List<ArrayList<String>> tagTable = Collections.synchronizedList(new ArrayList<ArrayList<String>>());
    boolean flashWining = false;

    //Colors
    static Color red = new Color(200,0,0);      //main red team color
    static Color green = new Color(0,200,0);    //main green team color
    static Color darkRed = red.darker();        //alt red
    static Color darkGreen = green.darker();    //alt green
    static Color gray = new Color(100,100,100); 
	 static Color black = new Color(0, 0, 0);
    static Color backgroundColor = gray.darker();      //main background color
    //Fonts
    public static Font headingFont = new Font("impact", Font.ITALIC, 35);
    Font columnHeadingFont = new Font("times new roman", Font.PLAIN, 24);
    Font textFieldFont = new Font("times new roman" , Font.PLAIN, 20);

	 //Key
	 boolean f5; // switch to PlayerEntry
	 boolean moveToPlayerEntry;
    
    // Constructor
    PlayerActionScreen(JFrame frame) 
    {
        countdownLabel = new JLabel();
        
        gamePanel = new JPanel(new GridBagLayout()); //main countdownPanel layout
        gamePanel.setBackground(backgroundColor);     //Background color
        heading = new JPanel();
        heading.setBackground(backgroundColor);
        heading.setLayout(new GridBagLayout());
		timerGrid = new GridBagConstraints();
        timerGrid.fill = GridBagConstraints.BOTH;
        timerGrid.ipadx = 100;
        //timerGrid.gridwidth = 2;
        timerGrid.gridx = 1;
        timerGrid.gridy = 0;

    }

    // Sets teams
    void setTeams(Team gt, Team rt) 
    {
        if (gt!=null && rt != null) {
            greenTeam = gt;
            redTeam = rt;
            gt.createTableFromArray();
            gt.updateTable();
            rt.createTableFromArray();
            rt.updateTable();
            return;
        } else {
            greenTeam = setDemoTeam(green, darkGreen);
            redTeam = setDemoTeam(red, darkRed); 
        }
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
        cons.gridx = 0;
        cons.gridwidth =2;
        cons.fill = GridBagConstraints.BOTH;
        gamePanel.add(heading,cons);
        // Sets values for countdownPanel
        cons.fill = GridBagConstraints.BOTH;
        cons.gridy = 1;
        cons.gridwidth = 1;
        greenTeam.addToPanel(gamePanel, cons);
		  cons.gridx = 1;
        redTeam.addToPanel(gamePanel, cons);

		  gamePanel.setFocusable(true);
		  gamePanel.requestFocusInWindow();

        gamePanel.revalidate();
    }

	 // Add new tag data to table
	 static void updateTagTable(String[] data)
	 {
		 System.out.println("Updating Table");
		 tagTable.add(new ArrayList<String>()); // new row
		 for (int i = 0; i < 2; i++) // adds info to columns
		 {
			 tagTable.get(tagTable.size() - 1).add(i, data[i]);
		 }
 
		//  System.out.println(tagTable);
		 checkTeams(data[0], data[1]);
	 }

	 // checks teams for tagger and run addpoints function
	 static void checkTeams(String taggerEquipID, String tagEquipID) throws NullPointerException
	 {
		 //  System.out.println("Checking Teams, Tagger: " + taggerEquipID);
		 boolean found = false;
		 int points;

		 // determines points
		 if (tagEquipID == greenBaseHit || tagEquipID == redBaseHit)
		 	points = 100;
		 else
			points = 10;

		 // checks green team
		 for (int i = 0; i < greenTeam.players.size(); i++)
		 {
			if (greenTeam.players.get(i).equipmentID != null)
			{
				// System.out.println("Checking " + greenTeam.players.get(i).equipmentID);
				if (Integer.parseInt(greenTeam.players.get(i).equipmentID) == Integer.parseInt(taggerEquipID))
				{
					found = true;
					greenTeam.players.get(i).addPoints(points, tagEquipID);
                    if(points == 100)
                    {
                        log.redBase(greenTeam.players.get(i).equipmentID);
                        greenTeam.players.get(i).taggedBase();
                    }
                    else
                    {
                        for(int g = 0; g < greenTeam.players.size(); g++)
                        {
                            if(Integer.parseInt(greenTeam.players.get(i).equipmentID) == Integer.parseInt(tagEquipID))
                            {
                                log.friendlyFire(taggerEquipID);
                                found = true;
                            }
                        }
                        if (!found)
                            log.tagLog(taggerEquipID,tagEquipID);
                    }
				}
			}
		 }

		 // if no match, checks red team
		 if (found == false)
		 {
			for (int i = 0; i < redTeam.players.size(); i++)
			{
				if (greenTeam.players.get(i).equipmentID != null)
				{
					if (Integer.parseInt(redTeam.players.get(i).equipmentID) == Integer.parseInt(taggerEquipID))
					{
						found = true;
						redTeam.players.get(i).addPoints(points, tagEquipID);
                        if(points == 100)
                        {
                            log.greenBase(redTeam.players.get(i).equipmentID);
                            redTeam.players.get(i).taggedBase();
                        }
                        else
                        {
                            for(int g = 0; g < redTeam.players.size(); g++)
                            {
                                if(Integer.parseInt(redTeam.players.get(i).equipmentID) == Integer.parseInt(tagEquipID))
                                {
                                    log.friendlyFire(taggerEquipID);
                                    found = true;
                                }
                            }
                            if (!found)
                                log.tagLog(taggerEquipID,tagEquipID);
                        }
					}
				}
			}
		 }
	 }

    // Creates a countdown screen
    void start() 
    { 
        gamePanel.setBackground(backgroundColor);
        addPanelsToFrame();
        inStartup = true;
        try
        {
            timerSem.acquire();
            timer = new Timer(10);
            timer.timerText.setFont(headingFont.deriveFont(Font.PLAIN, 70f));
            timer.timerText.setForeground(Color.WHITE);
            timerGrid.gridx = 1;
            timerGrid.gridy = 0;
            heading.add(timer.timerText,timerGrid);
            timer.start();
        }
        catch(InterruptedException e)
        {
            System.out.println("Cannot create new timer");
        }
    }

    void update() {
        boolean isGameOver = false;
        if (timer.getTime()==0) {
            if (inStartup) {
                inStartup = false;
                timer.setTime(10); // Begins proper game timer of 6min (360sec)
                timerGrid.gridx = 0;
                heading.add(greenTeam.teamScoreLabel,timerGrid);
                timerGrid.gridx = 2;
                heading.add(redTeam.teamScoreLabel,timerGrid);
		        music.start();
                log = new Log();
                try {
                    udpBroadcast.sendPacket(startSignal);
                    System.out.println("Transmitting game start signal.");
                } catch (Exception e) {
                    System.out.println("Could not broadcast start signal.");
                    e.printStackTrace();
                    System.exit(0);
                }
            } else {
                isGameOver = true;
                if(!transmitted)
                {
                    try {
                        udpBroadcast.sendPacket(endSignal);
                        System.out.println("Transmitting game end signal.");
                    } catch (Exception e) {
                        System.out.println("Could not broadcast end signal.");
                        e.printStackTrace();
                        System.exit(0);
                    }
                    log.close();
                    timerSem.release();
                }

            }
        boolean transmitted = false;
        }

        if (!inStartup) {
            calcWining();
        }

        if (isGameOver)
	    {
	        music.stopPlaying();
            gameOver();
	    }
    }

    void gameOver() {
        if(!transmitted)
        {
            System.out.println("Game Over");
            transmitted = true;
        }
 
		  gamePanel.addKeyListener(this);

		  //while (!f5) { // wait for key press
		  if (f5) // switch to PlayerEntry
		  {
			  System.out.println("Switch Screen");
			  moveToPlayerEntry = true;
			  
			  f5 = false;
		  //}
        }
    }
    void visible(JFrame frame, boolean addRemove)
    {
        if(addRemove == true)
            frame.setContentPane(gamePanel);
        else
            frame.remove(gamePanel);
    }

	 public void keyPressed(KeyEvent e) {} // not used
	 public void keyTyped(KeyEvent e) {} // not used
	 public void keyReleased(KeyEvent e)
	 {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_F6: f5 = true; break;
		}
	 }

     void calcWining()
     {
        Color c = Color.WHITE;
        if(flashWining)
            c = Color.LIGHT_GRAY;
        if(greenTeam.teamScore > redTeam.teamScore)
            greenTeam.teamScoreLabel.setForeground(c);
        else
            redTeam.teamScoreLabel.setForeground(c);
        flashWining = !flashWining;
     }
}
