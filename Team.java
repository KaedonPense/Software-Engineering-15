import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Team extends JFrame
{
    ArrayList<Player> players;
    Color[] colorsArray = new Color[2];
    JPanel teamPanel;
    GridBagConstraints teamGrid = new GridBagConstraints();
    public GridBagConstraints grid1 = new GridBagConstraints();
    public GridBagConstraints grid2 = new GridBagConstraints();
    public GridBagConstraints grid3;
    public int teamScore;
    int xPadding = 250;

    public static Font columnHeadingFont = PlayerEntryScreen.columnHeadingFont;
    public static Font playerFont = PlayerEntryScreen.textFieldFont;
    
    Team(Color color1, Color color2)
    {
        colorsArray[0] = color1;
        colorsArray[1] = color2;
        teamPanel = new JPanel(new GridBagLayout());
        teamGrid.fill = GridBagConstraints.HORIZONTAL;
        teamScore = 0;
        
        grid1.weightx = 0;
        grid1.gridx = 0;
        grid1.ipadx = 100;
        grid1.fill = GridBagConstraints.NONE;
        
        grid2.weightx = 10;
        grid2.gridx = 1;
        grid2.ipadx = xPadding;
        grid2.fill = GridBagConstraints.BOTH;
        
        grid3 = grid2;
        grid3.gridx = 2;

        JPanel heading = new JPanel(new GridBagLayout());
        JLabel base = new JLabel(" ");
        base.setFont(columnHeadingFont);
        JLabel name = new JLabel("Codename");
        name.setFont(columnHeadingFont);
        JLabel score = new JLabel("Points");
        score.setFont(columnHeadingFont);

        heading.add(base, grid1);
        heading.add(name, grid2);
        heading.add(score, grid3);
        teamPanel.add(heading, teamGrid);
        teamGrid.gridy = 1;
    }
    public void createTableFromArray()
    {
        for(int i = 0; i < players.size();i++)
        {
            teamGrid.gridy = i + 1;
            JPanel player = players.get(i).playerPanel;
            teamPanel.add(player, teamGrid);
        }
    }
    public void updateTable()
    {
        //sort the table based on score 
        //should be able to call teamPanel.add(players(i), teamGrid) w/ gridy properly set and it will overwrite the current;
        for(int i = 0; i < players.size(); i++)
        {
            players.get(i).playerPanel.setBackground(colorsArray[i%2]); //setting the background of each to keep consistency
        }
        this.teamPanel.revalidate();
    }

    public void addToPanel(JPanel parent, GridBagConstraints grid)
    {
        parent.add(teamPanel, grid);
    }
}


        
