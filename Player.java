import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
public class Player
{
    public int score;
    public String codename;
    public String equipmentID;
    public String playerID;
    public boolean base;

    public JLabel baseLabel;
    public JLabel nameLabel;
    public JLabel scoreLabel;
    public Team inTeam;

    Font playerFont = inTeam.playerFont;

    Player(String ID, String name, String EQID, Team team)
    {
        this.score = 0;
        this.codename = name;
        this.equipmentID = EQID;
        this.playerID = ID;
        this.base = false;
        this.inTeam = team;

        baseLabel = new JLabel(" ", SwingConstants.CENTER);
        baseLabel.setFont(playerFont);
        nameLabel = new JLabel(this.codename, SwingConstants.CENTER);
        nameLabel.setFont(playerFont);
        scoreLabel = new JLabel(String.valueOf(this.score), SwingConstants.CENTER);
    }
    Player()
    {
        baseLabel = new JLabel(" ", SwingConstants.CENTER);
        baseLabel.setFont(playerFont);
        nameLabel = new JLabel(" ", SwingConstants.CENTER);
        nameLabel.setFont(playerFont);
        scoreLabel = new JLabel(" ", SwingConstants.CENTER);
    }
    //public void tag()
    public void addPoints(int pointsToAdd, String Tagged)
    {
        boolean sameTeam = false;
        for(int i = 0; i < inTeam.players.size();i++)
        {
            String eqID = inTeam.players.get(i).equipmentID;
            if(eqID == Tagged)
            {
                sameTeam = true;
                i=inTeam.players.size();
            }
        }
        if(!sameTeam)
        {
            this.score=+pointsToAdd;
            inTeam.teamScore=+pointsToAdd;
            this.scoreLabel.setText(String.valueOf(this.score));
            inTeam.updateTable();
        }
        else
        {
            System.out.println("Error: The tagged player is on the same team. No points added");
        }
    }
    public void addToPanel(JPanel parent, GridBagConstraints g1, GridBagConstraints g2, GridBagConstraints g3, Color color)
    {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(baseLabel);
        parent.add(panel,g1);
        panel = new JPanel();
        panel.setBackground(color);
        panel.add(nameLabel);
        parent.add(panel,g2);
        panel = new JPanel();
        panel.setBackground(color);
        panel.add(scoreLabel);
        parent.add(panel, g3);
    }
}
