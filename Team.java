import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import javax.accessibility.*;

public class Team extends JFrame
{
    ArrayList<Player> players = new ArrayList<Player>();
    Color[] colorsArray = new Color[2];
    JPanel teamPanel;
    Color backgroundColor = PlayerEntryScreen.backgroundColor;
    //GRID VARS
        Insets empty = new Insets(0,0,0,0);
        int xPadding = 50;
        int yPadding = 5;
        GridBagConstraints mainGrid = new GridBagConstraints();
        GridBagConstraints backGrid = new GridBagConstraints();
        //instead of changing constraints for every addition instead just change gridy and have gridConstraint for each column
        public GridBagConstraints gridC1 = new GridBagConstraints();
        public GridBagConstraints gridC2 = new GridBagConstraints();
        public GridBagConstraints gridC3 = new GridBagConstraints();
    public int teamScore;
    public JLabel teamScoreLabel;
    
    public static Font columnHeadingFont = (PlayerEntryScreen.columnHeadingFont).deriveFont(25f);
    public static Font playerFont = (PlayerEntryScreen.textFieldFont).deriveFont(12f);
    static Border emptyBorder = BorderFactory.createEmptyBorder(5,0,5,0); //A transparent border so something doesn't fill its panel.
    static GridBagLayout layout = new GridBagLayout();
    Team(Color color1, Color color2)
    {
        colorsArray[0] = color1; //alternating colors
        colorsArray[1] = color2;

        teamPanel = new JPanel();
        teamPanel.setLayout(layout);
        teamPanel.setBackground(backgroundColor);
        //Initilize Grids
            //mainGrid
                mainGrid.weightx = 2; 
                mainGrid.weighty = 2;
                mainGrid.fill = GridBagConstraints.BOTH;
            //backGrid
                backGrid.gridwidth = 3;
                backGrid.weightx = 2; 
                backGrid.weighty = 2;
                backGrid.fill = GridBagConstraints.BOTH;
                backGrid.ipady = 5;
            //gridC1
                gridC1.weightx = 2; 
                gridC1.weighty = 2;
                gridC1.fill = GridBagConstraints.BOTH;
                gridC1.ipadx = xPadding;
                //gridC1.ipady = 50;
            //gridC2
                gridC2.gridx = 1;
                gridC2.weightx = 2; 
                gridC2.weighty = 2;
                gridC2.fill = GridBagConstraints.BOTH;
                gridC2.ipadx = xPadding;
                //gridC2.ipady = 50;
            //gridC3
                gridC3.gridx = 2;
                gridC3.weightx = 2; 
                gridC3.weighty = 2;
                gridC3.fill = GridBagConstraints.BOTH;
                gridC3.ipadx = xPadding;
                //gridC3.ipady = 50;
        //Heading
            Color headingTextColor = Color.WHITE;
            JLabel base = new JLabel(" ",SwingConstants.CENTER);
            base.setFont(columnHeadingFont);
            base.setForeground(headingTextColor);
            JLabel name = new JLabel("Codename", SwingConstants.CENTER);
            name.setFont(columnHeadingFont);
            name.setForeground(headingTextColor);
            JLabel score = new JLabel("Points", SwingConstants.CENTER);
            score.setFont(columnHeadingFont);
            score.setForeground(headingTextColor);
            teamPanel.add(base,gridC1);
            teamPanel.add(name,gridC2);
            teamPanel.add(score,gridC3);

        teamScore = 0;
        teamScoreLabel = new JLabel(String.valueOf(teamScore), SwingConstants.CENTER);
        teamScoreLabel.setForeground(Color.WHITE);
        teamScoreLabel.setFont(PlayerActionScreen.headingFont);
    }
    public void createTableFromArray()
    {
        for(int i = 0; i < players.size();i++)
        {
            this.setGridRow(i+1);
            Color c = colorsArray[i%2];
            if(!players.get(i).exists)
                c = backgroundColor;
            players.get(i).addToPanel(teamPanel,gridC1,gridC2,gridC3,c);
        }
    }
    public void updateTable()
    {
        this.teamPanel.revalidate();
        //this.sortTable();
    }

    public void addToPanel(JPanel parent, GridBagConstraints grid)
    {
        grid.fill = GridBagConstraints.BOTH;
        grid.weightx = 10;
        teamPanel.revalidate();
        parent.add(teamPanel, grid);
    }
    public void setGridRow(int y)
    {
        gridC1.gridy = y;
        gridC2.gridy = y;
        gridC3.gridy = y;
    }
    public void sortTable()
    {
        ArrayList<Player> tempPlayers = new ArrayList<Player>();
        int numPlayers = 0;
        for(int i = 0; i <players.size(); i++)
        {
            if(players.get(i).exists)
            {
                numPlayers= numPlayers + 1;
                tempPlayers.add(players.get(i));
                this.removePlayer(i+1);
            }
        }
        int row = 1;
        this.setGridRow(row);
        while(tempPlayers.size() > 1)
        {
            int high = tempPlayers.get(0).score;
            int playerH = 0;
            for(int i = 1; i < tempPlayers.size(); i++)
            {
                if(tempPlayers.get(i).score > high)
                {
                    high = tempPlayers.get(i).score;
                    playerH = i;
                }
            }
            tempPlayers.get(playerH).addToPanel(teamPanel,gridC1,gridC2,gridC3,colorsArray[row%2]);
            tempPlayers.remove(playerH);
            row = row + 1;
            setGridRow(row);
        }
        tempPlayers.get(0).addToPanel(teamPanel,gridC1,gridC2,gridC3,colorsArray[row%2]);
    }
    void removePlayer(int i)
    {   
        Component[] components = teamPanel.getComponents();
        for (Component component : components)
        {
            GridBagConstraints c = layout.getConstraints(component);
            if(c.gridy == i)
            {
                teamPanel.remove(component);
            }
        }
    }
}
	//  public void sortTable()
   //  {
   //      Player next;
   //      int row = -1;
   //      ArrayList<Player> tempPlayers = players;
	// 	  ArrayList<Player> newTemp = new ArrayList<Player>();
	// 	  ArrayList<Integer> entries;
	// 	  int maxi = 0;
	// 	  int maxscore = 0;

	// 	goes through adding the highest to another list 
	// 	then removing from templist until empty
   //      while (!tempPlayers.isEmpty())
	// 	  {
	// 			for (int i = 0; i < tempPlayers.size(); i++)
	// 			{
	// 				if (tempPlayers.get(i).score > maxscore)
	// 				{
	// 					maxscore = tempPlayers.get(i).score;
	// 					maxi = i;
	// 				}
	// 			}

	// 			newTemp.add(tempPlayers.get(maxi - 1));
	// 			tempPlayers.remove(maxi - 1);
	// 	  }

	// 	  if (!newTemp.isEmpty())
	// 	  {	
	// 			players = newTemp;
	// 			createTableFromArray();
	// 	  }
   //  }
