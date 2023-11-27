/*
    @This: Player Entry Screen Class
        @Version: 1.2                      //#.#
        @State: WIP                        //test, alpha, workInProgress, buggy, final
    @Who
        @Author(s): Kaedon Pense                     //name, name
        @Editor(s):
    @Last Edited
        @Date: 9/27/23        
        @By: Kaedon Pense
        @What:
    @Description:
        @Use: This creates and maintains the Player Entry Panel
        @Has Access to: Ports and Database
*/

//imports
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.table.*;
    import javax.swing.JScrollPane;
    import javax.swing.border.Border;
    import java.awt.event.ActionListener; 
    import java.awt.event.ActionEvent;
	 import java.awt.event.KeyListener;
	 import java.awt.event.KeyEvent;
    import java.util.Scanner;
    import java.io.IOException;



public class PlayerEntryScreen extends JFrame implements ActionListener, KeyListener// Could name this Window to be more accurate  
{
    //Static Variables
        //Panels
            static JPanel playerEntryPanel; //The JPanel this is for. (creates and edits)
            static JPanel textFieldsPanel; //The panel that is inside the scroll pane. Scroll pane layouts are odd, so the JScrollPane bellow contains this object and how the contents should be displayed
            static JScrollPane scroll; //Makes the textfields scrollable if window size too small for its contents
        //Grids
            static GridBagConstraints mainGrid; //The layoutmanager for this pannel 
            static GridBagConstraints scrollGrid;
            static GridLayout playerLayout = new GridLayout(1,3,5,0); //Grid layout for individual panels. Used when a panel that needs to contain 3 objects is created i.e for each player and for the columnHeadings    
        //Colors
            static Color red = new Color(200,0,0);      //main red team color
            static Color green = new Color(0,200,0);    //main green team color
            static Color darkRed = red.darker();        //alt red
            static Color darkGreen = green.darker();    //alt green
            static Color gray = new Color(100,100,100); 
            static Color backgroundColor = gray.darker();      //main background color
        //Fonts
            public static Font headingFont = new Font("impact", Font.ITALIC, 35);
            public static Font columnHeadingFont = new Font("times new roman", Font.PLAIN, 15);
            public static Font textFieldFont = new Font("times new roman" , Font.PLAIN, 12);
        //Arrays
            static JTextField[] textField = new JTextField[90];    //An array of JTextfields, in this instance the number i%3 gives what the cell should be. = 0 -> playerID, = 1 -> Codename, =2 -> EquipmentID
            static String[] checkData = new String[90];     //An array of the previous contents of JTextfields - updated when a cell is changed
		  //Keys
		  		boolean f5; // switch to PlayAction
				boolean f12; // clear entries
        //Other
            static int playerIDlength = 5; //the required length of the player ID, use this to determine if cell is full -> do actions
            static int equipIDlength = 5;
            static Border emptyBorder = BorderFactory.createEmptyBorder(5,5,5,5); //A transparent border so something doesn't fill its panel. USED: to make collored player backgrounds visible, else the white textfield covers the panel. 
            static Border errorBorder = BorderFactory.createBevelBorder(1, Color.BLUE, Color.BLUE, backgroundColor,gray);
				boolean moveToPlayAction = false;
        //constructors

	//Database
		Database database;
      
    
    PlayerEntryScreen(JFrame frame)
        {   
            //Main panel
                playerEntryPanel = new JPanel();                     //Initilize player entry panel
                playerEntryPanel.setLayout(new GridBagLayout());     //main panel layout
                playerEntryPanel.setBackground(backgroundColor);            //Background color
                mainGrid = new GridBagConstraints();            //create the gridConstraints used for main
                mainGrid.fill = GridBagConstraints.BOTH;        //Make the grid fill the panel in both directions
            //TextFields Panel
                textFieldsPanel = new JPanel();                 //Initilize panel to add textfields to
                textFieldsPanel.setLayout(new GridBagLayout()); //TextFields layout
                textFieldsPanel.setBackground(backgroundColor);        //Background color
                scrollGrid = new GridBagConstraints();          //create gridConstraints for textFields layout  
                scrollGrid.fill = GridBagConstraints.BOTH;

					 this.addKeyListener(this);
					 this.setFocusable(true);
					 this.requestFocusInWindow();
            //TODO: Find the correct length of player ID and eq id, set the variables and remove all bellow println's
                System.out.println("");
                System.out.println("");
                System.out.println("IMPORTANT");
                System.out.println("The length of valid player id's is " + playerIDlength + ".");
                System.out.println("the length of valid equipment id's is " + equipIDlength + ".");
                System.out.println("Found on lines 62 and 63");
                
            
        }

    void createPlayerEntryScreenContent()
        {
            int columnShift = 1; //Shift columns right n cells. USE: if we want to have a column on the left for player numbers. In doing so we need to make sure the Team headings are still aligned properly
            //create the labels
                //Teams - green and red at very top of panel
                    //Initilizing How to add
                        this.gridPadding(100,100, mainGrid);    //Components added until this is called again have a minimum size of 100,100
                        this.gridFill(true,true, mainGrid);     //Components added until this is called again fill their cell in both directions
                        mainGrid.weightx = 10;                  //The weight of the components when resizing the frame; Example, given 2 objects with weight 10 and 1 respectively for every 11 units of size changed the space aloted to the first component will be (aprox) 10 times more
                        mainGrid.weighty = 3;
                    //Main headings
                        this.addJLabel("Green",1+columnShift, 0, headingFont, green, null, mainGrid, playerEntryPanel); //Create new JLabel string that displays "Green" in cell (1+columnShift,0) with font headingFont, color green, no background color, using mainGrid constraints, adding to playerEntryPanel 
                        this.addJLabel("Red", 4+columnShift, 0, headingFont, red, null, mainGrid, playerEntryPanel);    //Create new JLabel string that displays "Red" in cell (4+columnShift,0) with font headingFont, color red, no background color, using mainGrid constraints, adding to the playerEntryPanel 
                //Column Headings - Text displayed above textfields
                    String[] headings = {"PlayerID","Codename","EquipID"}; //The headings for the columns
                    this.gridPadding(50,0, mainGrid); //set minimum size to (50,0); //Setting minimum height here to 0 makes it so the height is equal to the height of contents. i.e doesn't add size
                    this.gridFill(true,false, mainGrid); //fill horizontally but not vertically
                    mainGrid.gridwidth = 3; //set the grid to take up 3 cells 
                    for(int i = 0; i < 2; i++)  //Do this once for each team
                        {   
                            JPanel header = new JPanel();   //These need to be added to new panel to keep aligned with the textfields under them. Textfields later are added to panels so they have the background colors. 
                            header.setBackground(backgroundColor);   //set background to the background color
                            header.setLayout(playerLayout); //playerLayout is an unintuitive name in this case however, this is just to keep the layout of the columnheadings the same as their contents(players)
                            header.setBorder(emptyBorder);  //is used to make colored panels behind players visible thus needed here to keep alignment
                            for(int ii = 0; ii < 3; ii++) //Adding headings
                            {
                                this.addJLabel(headings[ii], ii, 0, columnHeadingFont, Color.WHITE, null, mainGrid, header); //create JLabel string displaying "headings[ii]" in cell(ii,0)(to be added to row 1 in playerEntryPanel) using columnHeading font, color white, no background, (layout isnt gridBag thus grid is irrelivant), adding to heading panel
                            }
                            this.gridCell(columnShift+i*3,1, mainGrid); //set the cell to add to mainGrid to be (columnShift + i*3 ,1) where i is 0 or 1, thus sets to cell (shift + 0, 1) or (shift + 3) or the first cell of each player.
                            playerEntryPanel.add(header, mainGrid); //Add created and filled panel to the playerEntryPanel using the mainGrid constraint
                        }
            //create text textfields
                //setup how to add to grid
                    scrollGrid.weightx = 5;
                    scrollGrid.weighty = 5;
                    scrollGrid.gridwidth = 3;   //fill 3 cells
                    this.gridFill(true, true, scrollGrid);  //fill both directions
                    this.gridPadding(0,0, scrollGrid);  //no minimum size, i.e. add components to grid using their size. 
                Color[] colorsToUse = {green,red,darkGreen,darkRed}; // the array of colors to use in this for loop 
                int setColor = 0; //the index of what color in colorsToUse
                int playerNumber = 0;
                for(int row = 0; row < 15; row++) //add row for each of 15 players per team. 1 Row includes player n from green and red team
                    {   
                        for(int column = columnShift; column < 5; column+=3)
                            {
                                //Create a new player panel
                                    JPanel player = new JPanel();       //new panel
                                    player.setLayout(playerLayout);     //set layout of panel to playerLayout
                                    player.setBorder(emptyBorder);      //set border to empty
                                    player.setBackground(colorsToUse[setColor]);    //setBackground to the alternating colorsToUse
                                //NextColor
                                    setColor++;
                                    if(setColor >3)
                                        setColor = 0;
                                for(int field = 0; field < 3; field++) //Create 3 textfields for player
                                    {
                                        JTextField textbox = this.addTextField(null, 1, scrollGrid); // scroll grid irrelivant as layout is not gridBag
                                        textbox.setFont(textFieldFont);
                                        textField[playerNumber*3+field] = textbox;  //set the relevant array number to this
                                        checkData[playerNumber] = null;     //initilize checkdata for the cell. i.e "last checked contents of cell"
                                        player.add(textbox); //add each textfield to the player panel
                                    }
                                playerNumber++; //set next player number
                                this.gridCell(column, row, scrollGrid); //set cell to add player panel to 
                                textFieldsPanel.add(player, scrollGrid);    //add player panel
                            }
                }
            //How should scrollpanel fill player Entry Panel
                mainGrid.gridheight = 10;
                mainGrid.gridwidth = 7;     //ie set to add as filling all columns and 10 rows
                mainGrid.weighty = 25;
            this.gridCell(0,2,mainGrid); //set what cell to add scroll to 
            this.gridFill(true,true,mainGrid);  //to fill both directions
            this.gridPadding(0,0,mainGrid); //let it size itself
            scroll = new JScrollPane(textFieldsPanel); //create a new JScrollPanel with its contents being the textFieldsPanel
            playerEntryPanel.add(scroll, mainGrid); //add the scroll to the playerEntryPanel, using mainGrid constraints

        }
    void gridPadding(int padx, int pady, GridBagConstraints grid)
        {
        grid.ipadx = padx; //sets the gridConstraints minimum horizontal size
        grid.ipady = pady; //sets the gridConstraints minimum vertical size
        }
    void gridFill(boolean horizontal, boolean vertical, GridBagConstraints grid)
        {
            if(horizontal)  //horizontal fill
                grid.fill = GridBagConstraints.HORIZONTAL;
            if(vertical)    //vertical fill
                grid.fill = GridBagConstraints.VERTICAL;
            if(horizontal && vertical)  //fill both vertical and horizontal
                grid.fill = GridBagConstraints.BOTH;
            if(!horizontal && !vertical)   //dont fill any direction
                grid.fill = GridBagConstraints.NONE;
        }
    void gridCell(int column, int row, GridBagConstraints grid)
        {
            grid.gridx = column;    //set the gridConstraints of what cell to add to 
            grid.gridy = row;
        }
    JLabel addJLabel(String text, int c, int r, GridBagConstraints grid, JPanel panel)
        {
            JLabel label = new JLabel(text);    //create a new string Label
            //set the cell it should be in, collum c, row r
                gridCell(c,r, grid);
            label.setHorizontalAlignment(SwingConstants.CENTER);    //Center the text in its cell
            panel.add(label, grid);   //add to playerEntryPanel panel
            return label;   //return statement in case need to use this label outside of this function
        }
    JLabel addJLabel(String text, int c, int r, Font font, Color foreground, Color background, GridBagConstraints grid, JPanel panel)
        {
            JLabel label = new JLabel(text);
            //set the cell it should be in, collum c, row r
                gridCell(c,r, grid);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            //Added Details
                if(font != null)    //set font 
                    label.setFont(font);
                if(foreground != null) //set forground, for JLabel(string) this is the text color
                    label.setForeground(foreground); 
                if(background != null)  //set background
                    label.setBackground(backgroundColor);
            panel.add(label, grid);   
            return label;
        }
    JTextField addTextField(String text, int columns, GridBagConstraints grid)
        {
            JTextField textbox = new JTextField(text, columns); //create new textbox
            return textbox;
        }
    void Update() 
        {
            String newData; //will be the current text in a textbox
            String oldData; //will be the text that was in the cell last time this function ran
            String code = null;
				// checks when window is the focus
				if (f5) // switch to PlayAction
				{
					System.out.println("Switch Screen");
                    if(this.CheckValidPlayers())
                    {
					    moveToPlayAction = true;
                    }
                    else
                    {
                        moveToPlayAction = false;
                    }
					f5 = false;
				}

				if (f12) // clear entry data
				{
					clearData();
					System.out.println("Cleared Data");
					f12 = false;
				}
				
				for(int i = 0; i < 90; i++)
            {
               if (textField[i].hasFocus()) // adds keyListener to selected cell for key commands
					{
						textField[i].addKeyListener(this);
					}
					
					// checks when textfield is the focus
					if (f5) // switch to PlayAction
					{
						System.out.println("Switch Screen");
						moveToPlayAction = true;
						
						f5 = false;
					}

					if (f12) // clear entry data
					{
						clearData();
						System.out.println("Cleared Data");
						f12 = false;
					}


					 newData = textField[i].getText().trim();  //get text from box
                if(!newData.isEmpty()) // there is something in the cell   
                {
                    oldData = checkData[i]; //previous data in cell
                    if(newData.equals(oldData)) // in java == checks if they are the same object not if they have the same content. ".equals()" basicly says if(valueOf == valueOf) //With == it is always false because it is checking if string == string
                        {
                        }
                    else //if the data in cell has changed
                        {
                            //System.out.println("new data in: " + i);
                            checkData[i] = newData; //set the data of "previous data in cell" to the new cell data
                            switch(i%3) //this handles the 3 cases of what the data is in reference to. If 0 its a playerID, if 1 its a codename, if 2 its a equipmentID
                            {
                                case(0): //is playerID
                                    if(newData.length() == playerIDlength)
                                    {
                                        code = database.ReturnCodeName(textField[i].getText());
                                        if(code != null)
                                            textField[i+1].setText(code);
                                        if(code == null)
                                            textField[i+1].setText(code);
                                    }; 
                                    break;
                                case(1): //is codename
                                        code = database.ReturnCodeName(textField[i-1].getText());
                                        if(code == null && textField[i].getText() != code && textField[i-1].getText() != null)
                                            database.InsertData(textField[i-1].getText(), textField[i].getText());
                                        if(code != null && !(textField[i].getText().equals(code)))
                                            database.UpdateData(textField[i-1].getText(), textField[i].getText());
                                    break;
                                case(2): //is equipmentID
                                    if(newData.length() == equipIDlength)
                                    {
                                        try
                                        {
                                            udpBroadcast.sendPacket(newData);
                                        }
                                        catch(IOException e)
                                        {

                                        }
                                    };
                                    break;
                            }
                        }   
                }
            }
        }
 
    boolean CheckValidPlayers()
        {   
            boolean error = false;
            String playerID;
            String codename;
            String equipmentID;

            //String[] playerIDs = new String[30];
            //String[] playerNames = new String[30];
            //String[] equipmentIDs = new String[30];
            for(int player = 0; player < 30; player++)
            {
                playerID = textField[player*3].getText();
                codename = textField[player*3+1].getText();
                equipmentID = textField[player*3+2].getText();
                //check empty
                String check = this.checkPlayer(playerID, codename, equipmentID, player);
                if(check.equals("empty"))
                {
                }
                if(check.equals("true"))
                {
                }
                if(check.equals("false"))
                {
                    error = true;
                }
                //playerIDs[player] = playerID;
                //playerNames[player] = codename;
                //equipmentIDs[player] = equipmentID;
            }
            if(error)
                System.out.println("ERROR Invalid Player(s) cannot continue");
            return !error; //if error = true return false; 
        }
 
    @Override
    public void actionPerformed(ActionEvent e)
        {
            //System.out.println("Action detected");
        }
    String checkPlayer(String id, String name, String eqID, int playerNumber)
        {
            String valid = "true"; //by defalult set to return the player is valid

            
            if((id.length() == 0)&&(name.length() == 0)&&(eqID.length() == 0)) //check Empty
                return "empty"; //i.e. the textfield are valid
            else //ie something about the player exists
                {   
                    String playerError = "---Error initilizing what player has an error---";
                    String message;
                    String message2;
                    switch(playerNumber%2)
                        {
                            case(0): playerError = "Green player " + (playerNumber/2+1) + " : "; break;
                            case(1): playerError = "Red player" + ((playerNumber-1)/2 +1)+ " : "; break;
                        }
                //Checking individual null
                    if(id.length() == 0)
                        {
                            message = " player ID is empty but player is not.";
                            message2 = " player ID should be of lenght ";
                            System.out.println(playerError + message + message2 + playerIDlength);
                            valid = "false";
                            this.setTextFieldError(playerNumber*3, true); //player is invalid because of this thus highlight the textbox
                        }
                    if(id.length() != playerIDlength)
                        {
                            message = " length of playerID is ";
                            message2 = " needs to be length ";
                            System.out.println(playerError + message + id.length() + message2 + playerIDlength);
                            valid = "false";
                            this.setTextFieldError(playerNumber*3, true); //player is invalid because of this thus highlight the textbox
                        }
                    if(name.length() == 0)
                        {   
                            message = " codename is empty";
                            System.out.println(playerError + message);
                            valid = "false";
                            this.setTextFieldError(playerNumber*3 + 1, true);//player is invalid because of this thus highlight the textbox
                        }
                    if(eqID.length() == 0)
                        {
                            message = " equipment ID is empty but player is not.";
                            message2 = " equipment ID should be of lenght ";
                            System.out.println(playerError + message + message2 + equipIDlength);
                            valid = "false";
                            this.setTextFieldError(playerNumber*3+2, true); //player is invalid because of this thus highlight the textbox
                        }
                    if(eqID.length() != equipIDlength)
                        {
                            message = " length of equipmentID is ";
                            message2 = " needs to be length ";
                            System.out.println(playerError + message + eqID.length() + message2 + equipIDlength);
                            valid = "false";
                            this.setTextFieldError(playerNumber*3+2, true); //player is invalid because of this thus highlight the textbox
                        }
                //Check against array and check length
                    for(int i = 0; i < 30; i++)
                        {
                            String player2 = "error setting player 2";
                            String id2 = textField[i*3].getText();
                            String name2 = textField[i*3 + 1].getText();
                            String eqID2 = textField[i*3 + 2].getText();
                            switch(i%2)
                            {
                                case(0): player2 = "Green player " + (i/2 + 1); break;
                                case(1): player2 = "Red player " + ((i-1)/2 + 1); break;
                            }
                        if(playerNumber != i)
                        {
                            //playerID                
                                if(id.length() == playerIDlength)
                                    {
                                        if(id.equals(id2)) //runs 
                                        {
                                            valid = "false";
                                            message = " id is the same as ";
                                            System.out.println(playerError + message + player2);
                                            this.setTextFieldError(playerNumber*3, true); //player is invalid because of this thus highlight the textbox
                                            this.setTextFieldError(i*3, true);
                                        }
                                    }
                            //codename
                            if(name.length() != 0)
                            {
                                if(name.equals(name2))
                                    {
                                        valid = "false";
                                        message = " codename is the same as ";
                                        System.out.println(playerError + message + player2);
                                        this.setTextFieldError(playerNumber*3+1, true); //player is invalid because of this thus highlight the textbox
                                        this.setTextFieldError(i*3 + 1, true);
                                    }
                                }
                            //EquipmentID
                                if(eqID.length() == equipIDlength)
                                    {
                                        if(eqID.equals(eqID2))
                                        {
                                            valid = "false";
                                            message = " equipment ID is the same as ";
                                            System.out.println(playerError + message + player2);
                                            this.setTextFieldError(playerNumber*3+2, true);
                                            this.setTextFieldError(i*3+2, true);
                                        }
                                    }
                            }
                        }
                    if(valid.equals("true"))
                        {
                            //make sure that the textborders are the default
                            this.setTextFieldError(playerNumber*3,false);
                            this.setTextFieldError(playerNumber*3+1,false);
                            this.setTextFieldError(playerNumber*3+2,false);
                        }
                    return valid;
                    }
        }
    void visible(JFrame frame, boolean addRemove)
        {
                if(addRemove == true)
            {
                    frame.setContentPane(playerEntryPanel);
                    database = new Database();
                    //System.out.println("Added Player Entry to frame");
            }
                else
            {
                    frame.remove(playerEntryPanel);
                    if(database.connect !=null)
                        database.CloseConnection();
                    //System.out.println("Removed Player Entry from frame");
            }
        }
 
	 public void keyPressed(KeyEvent e) {} // not used
	 public void keyTyped(KeyEvent e) {} // not used
	 public void keyReleased(KeyEvent e)
	 {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_F5: f5 = true; break;
			case KeyEvent.VK_F12: f12 = true; break;
		}
	 }
	 /*
     * @description: Clears the text in textfields and the "previous data in cell"
     *      @uses: When "new game"
     * @param: none
     * @return: none
     */
    void clearData()
        {
            for(int i = 0; i < 90; i++)
            {
                textField[i].setText(null);
                checkData[i] = null;
            }
        }

    void setTextFieldError(int textfield, boolean error)
        {
            if(error) // if there is something wrong
            {
                textField[textfield].setBackground(gray);
                textField[textfield].setForeground(Color.WHITE);
            }
            else
            {
                textField[textfield].setBackground(Color.WHITE);
                textField[textfield].setForeground(Color.BLACK);
            }
        }


    void CreateTeams(Team greenTeam, Team redTeam)
    {
        for(int i = 0; i < 30; i=i+2)
        {
            if(textField[i*3].getText().equals(""))
            {
                Player p = new Player();
                greenTeam.players.add(p);
            }
            else
            {
                Player p = new Player(textField[i*3].getText(), textField[i*3+1].getText(), textField[i*3+2].getText(),greenTeam);
                greenTeam.players.add(p);
            }
        }
        for(int i = 1; i < 30; i=i+2)
        {
            if(textField[i*3].getText().equals(""))
            {
                Player p = new Player();
                redTeam.players.add(p);
            }
            else
            {
                Player p = new Player(textField[i*3].getText(), textField[i*3+1].getText(), textField[i*3+2].getText(),redTeam);
                redTeam.players.add(p);
            }
        }
    }
    public boolean addDebugPlayers()
    {
        if(textField[0].getText().length() > 1)
        {
            return false;
        }
        int numPlayers = 4; 
        String playerid;
        String equipid;
        for(int i = 0; i< numPlayers; i++)
        {
            playerid = Integer.toString(i);
            while(playerid.length() != playerIDlength)
            {
                playerid = "0" + playerid;
            }
            equipid = Integer.toString(i);
            while(equipid.length() != equipIDlength)
            {
                equipid = "0" + equipid;
            }
            textField[i*3].setText(playerid);
            textField[i*3 + 2].setText(equipid);
        }
        return true;
    }
}
