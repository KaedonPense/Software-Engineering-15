import javax.swing.*;
import java.awt.*;
public class Player 
{
    public int score;
    public String codename;
    public String equipmentID;
    public String playerID;
    public boolean base;

    public JPanel playerPanel;
    public JLabel baseLabel;
    public JLabel nameLabel;
    public JLabel scoreLabel;
    public Team inTeam;

    public Font playerFont = inTeam.playerFont;

    Player(String ID, String name, String EQID, Team team)
    {
        this.score = 0;
        this.codename = name;
        this.equipmentID = EQID;
        this.playerID = ID;
        this.base = false;
        this.inTeam = team;

        this.playerPanel = new JPanel(new GridBagLayout());

        baseLabel = new JLabel(" ");
        baseLabel.setFont(playerFont);
        playerPanel.add(baseLabel, inTeam.grid1);

        nameLabel = new JLabel(this.codename);
        nameLabel.setFont(playerFont);
        playerPanel.add(nameLabel, inTeam.grid2);
        
        scoreLabel = new JLabel(String.valueOf(this.score));
        scoreLabel.setFont(playerFont);
        playerPanel.add(scoreLabel, inTeam.grid3);
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
}