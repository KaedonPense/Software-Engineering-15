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
    JPanel styleB = new JPanel();
    Font playerFont = inTeam.playerFont;
    Font styleFont = new Font("impact", Font.BOLD, 20);
    Border raisedBevel = BorderFactory.createRaisedBevelBorder();
    Border loweredBevel = BorderFactory.createLoweredBevelBorder();
    Border compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
    public boolean exists = false;

    Player(String ID, String name, String EQID, Team team)
    {
        this.score = 0;
        this.codename = name;
        this.equipmentID = EQID;
        this.playerID = ID;
        this.base = false;
        this.inTeam = team;
        this.exists = true;

        baseLabel = new JLabel("B", SwingConstants.CENTER);
        baseLabel.setFont(styleFont);
        baseLabel.setBackground(Color.yellow);
        baseLabel.setBorder(compound);
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
      //  System.out.println("Adding Points"); 
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
			this.score+=pointsToAdd;
            inTeam.teamScore+=pointsToAdd;
            inTeam.teamScoreLabel.setText(String.valueOf(inTeam.teamScore));
            this.scoreLabel.setText(String.valueOf(this.score));
            inTeam.sortTable();
            inTeam.updateTable();
        }
        else
        {
            System.out.println("Error: The tagged player is on the same team. No points added");
        }
    }
    public void addToPanel(JPanel parent, GridBagConstraints g1, GridBagConstraints g2, GridBagConstraints g3, Color color)
    {
        this.styleB.setBackground(color);
        parent.add(this.styleB,g1);
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(nameLabel);
        parent.add(panel,g2);
        panel = new JPanel();
        panel.setBackground(color);
        panel.add(scoreLabel);
        parent.add(panel, g3);
    }
    public void taggedBase()
    {
       styleB.add(baseLabel);
    }

    public void setPanel(int row, JPanel parent, Color color)
    {
        styleB.setBackground(color);
        parent.setComponentZOrder(this.styleB, row * 3);
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.add(nameLabel);
        parent.setComponentZOrder(panel, row*3 + 1);
        panel = new JPanel();
        panel.setBackground(color);
        panel.add(scoreLabel);
        parent.setComponentZOrder(panel,row*3 + 2);
    }
}
