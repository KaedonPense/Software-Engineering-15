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
      
    
    /* 
     * @description: JPanel "playerEntryPanel" constructor
     *      @use:   creates main JPanel and its main contents panels
     * @param {frame} the frame to add the panel to;
     * @return: none
     */
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

                System.out.println("The length of valid player id's is " + playerIDlength + ".");
                System.out.println("the length of valid equipment id's is " + equipIDlength + ".");
                System.out.println("Found on lines 57 and 58");
                
            
        }

    /* 
     * @description: Creates contents of JPanel Player Entry Screen
     *      @use:   The panels are created elsewhere this just creates and adds contents
     * @param: none
     * @return: none
     */
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
        //System.out.println(playerNumber*3+field);
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
    /* 
     * @description: sets the minimum x and y size
     *      @use: Something needs to be at least a certain size
     * @param {padx} int: the minimum horizontal size to set to
     * @param {pady} int: the minimum vertical size to set to 
     * @param {grid} GridBagConstraints: the constrainst/instructions for how to add to the gridLayout
     */
    void gridPadding(int padx, int pady, GridBagConstraints grid)
        {
        grid.ipadx = padx; //sets the gridConstraints minimum horizontal size
        grid.ipady = pady; //sets the gridConstraints minimum vertical size
        }
    /* 
     * @description: how to fill the cell
     *      @use: changing how something fills the cell;
     * @param {horizontal} bool: wether or not to fill horizontally
     * @param {vertical} bool: wether or not to fill vertically
     * @param {grid} GridBagConstraints: the constrainst/instructions for how to add to the gridLayout
     */
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
    /* 
     * @description: what cell to add to
     *      @use: changes the cell to add to when JPanel.add(what, grid) is called
     * @param {column} int: the column to add to
     * @param {row} int: the row to add to
     * @param {grid} GridBagConstraints: the constrainst/instructions for how to add to the gridLayout
     */
    void gridCell(int column, int row, GridBagConstraints grid)
        {
            grid.gridx = column;    //set the gridConstraints of what cell to add to 
            grid.gridy = row;
        }
    /* 
     * @description: Create noneditable string on screen //NOTE: See other JLabel constructor bellow for a more detailed creator
     *      @use: Creates a Jlabel(string) and adds it to layout (c,r)
     * @param {text} string: text that is to be displayed on screen
     * @param {c} integer: the column to add the JLabel to
     * @param {r} integer: the row to add the JLabel to
     * @param {grid} GridBagConstraints: the constrainst/instructions for how to add to the gridLayout
     * @param {panel} JPanel: the panel to add the label to
     */
    JLabel addJLabel(String text, int c, int r, GridBagConstraints grid, JPanel panel)
        {
            JLabel label = new JLabel(text);    //create a new string Label
            //set the cell it should be in, collum c, row r
                grid.gridx = c;                    
                grid.gridy = r;
            label.setHorizontalAlignment(SwingConstants.CENTER);    //Center the text in its cell
            panel.add(label, grid);   //add to playerEntryPanel panel
            return label;   //return statement in case need to use this label outside of this function
        }
    /* 
     * description: Detailed create noneditable text on screen //NOTE:if padding, font, and color are to be null use simple creator above
     *      @use: Creates a Jlabel(string) and adds it to layout (c,r), fills the cell, sets font and color
     * @param {text} string: text that is to be displayed on screen
     * @param {c} int: the column to add the JLabel to
     * @param {r} int: the row to add the JLabel to
     * @param {font} Font: the font to use : null if using default
     * @param {foreground} Color: the color of the text : null if using default
     * @param {background} Color: the color of the background, (highlight?) : null if using defalult
     * @param {grid} GridBagConstraints: the constrainst/instructions for how to add to the gridLayout
     * @param {panel} JPanel: the panel to add the Label to
     * @return {JLabel} the label created in this //NOTE: as of 9/26/23 the return isn't used for anything 
     */
    JLabel addJLabel(String text, int c, int r, Font font, Color foreground, Color background, GridBagConstraints grid, JPanel panel)
        {
            JLabel label = new JLabel(text);
            //set the cell it should be in, collum c, row r
                grid.gridx = c;
                grid.gridy = r;
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
    /* 
     * @description: create textbox function
     *      @use: creates a textbox with contents (text) with n columns in a cell and sets fill
     * @param {test} string: the text to be entered into the textbox //NOTE: If to be blank then equals null;
     * @param {columns} int: how many columns should the textbox have //NOTE: Default should be 1
     * @param {grid} GridBagConstraints: the constrainst/instructions for how to add to the gridLayout
     * @return returns the created textfield //NOTE: this is used to initilize array textfields
     */
    JTextField addTextField(String text, int columns, GridBagConstraints grid)
        {
            JTextField textbox = new JTextField(text, columns); //create new textbox
            return textbox;
        }
    /* 
     * @description: Runs To see what cells have changed
     *      @use: when a cell has changed make sure everything is in parameters and handle conditinals
     * @param: none
     * @return: none //NOTE: may make this return true/false if something changed
     */
    void Update() 
        {
            String newData; //will be the current text in a textbox
            String oldData; //will be the text that was in the cell last time this function ran
            
				// checks when window is the focus
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
                                        //TODO: ADDHERE: Search database for ID, returning codename
                                            //IF no id found then create new entry with id = newData

                                            //if not found search function returns null
                                                //Later : try and highlight codename cell to show that codename needs to be entered
                                                    //call this.setTextFieldBorderError(i+1, true) //This will set the border of the codename field to be a color rather than not empty
                                                    
                                            //if found      textfield[i+1].setText(found name);
                                    }; 
                                    break;
                                case(1): //is codename
                                        //TODO: Search for id and get codename; if returnedName != newData, set the name in database to newData
                                            //call this.setTextFieldBorderError(i, false) //will reset the border to empty if there was an error finding the name
                                    break;
                                case(2): //is equipmentID
                                    if(newData.length() == equipIDlength)
                                    {
                                        //TODO: ADDHERE: Transmit code
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
    /* 
     * @description: Looks through all the players and checks for invalid or duplicate values;
     *      @use:
     * @param 
     * @return: false if there is an error, true if there not an error
     */
    boolean CheckValidPlayers()
        {   
            boolean error = false;
            String playerID;
            String codename;
            String equipmentID;

            String[] playerIDs = new String[30];
            String[] playerNames = new String[30];
            String[] equipmentIDs = new String[30];
            for(int player = 0; player < 30; player++)
            {
                playerID = textField[player*3].getText();
                codename = textField[player*3+1].getText();
                equipmentID = textField[player*3+2].getText();
                //check empty
                String check = this.checkPlayer(playerIDs, playerNames, equipmentIDs, playerID, codename, equipmentID, player);
                if(check.equals("empty"))
                {
                    //player is empty thus valid
                }
                if(check.equals("true"))
                {
                }
                if(check.equals("false"))
                {
                    error = true;
                }
                playerIDs[player] = playerID;
                playerNames[player] = codename;
                equipmentIDs[player] = equipmentID;
            }
            return !error; //if error = true return false; 
        }
    /* 
     * @description:
     *      @use:
     * @param
     * @return
     */
    @Override
    public void actionPerformed(ActionEvent e)
        {
            //System.out.println("Action detected");
        }
    /* 
     * @description: Check if a player has any errors/isvalid
     *      @use: Checks the values of inputed player against inputed arrays and returns appropriate string //Makes sure there are no duplicates
     * @param {pIDs} array of strings: A collection of all player ID's already ran/checked
     * @param {names} array of strings: A collection of all player codenames that have already been ran/checked
     * @param {eqIDs} array of strings: A collection of all player equipmentIDs that have already been checked
     * @param {id} string: the player ID to look for
     * @param {name} string: the codename to look for
     * @param {eqID} string: the equipment ID to look for
     */
    String checkPlayer(String[] pIDs, String[] names, String[] eqIDs, String id, String name, String eqID, int playerNumber)
        {
            String valid = "true"; //by defalult set to return the player is valid
            
            if((id == null)&&(name == null)&&(eqID == null)) //check Empty
                return "empty"; //i.e. the textfield are valid
            else //ie something about the player exists
                {   
                //Checking individual null
                    if(id == null)
                        {
                            valid = "false";
                            this.setTextFieldBorderError(playerNumber*3, true); //player is invalid because of this thus highlight the textbox
                        }
                    if(name == null)
                        {
                            valid = "false";
                            this.setTextFieldBorderError(playerNumber*3 + 1, true);//player is invalid because of this thus highlight the textbox
                        }
                    if(eqID == null)
                        {
                            valid = "false";
                            this.setTextFieldBorderError(playerNumber*3+2, true);//player is invalid because of this thus highlight the textbox
                        }
                //Check against array and check length
                    for(int i = 0; pIDs[i+1] != null; i++)
                        {
                        //playerID
                            if(id.length() == playerIDlength)
                                {
                                    if(pIDs[i].equals(id)) //runs 
                                    {
                                        valid = "false";
                                        this.setTextFieldBorderError(playerNumber*3, true); //player is invalid because of this thus highlight the textbox
                                    }
                                }
                        //codename
                            if(names[i].equals(name))   
                                {
                                    valid = "false";
                                    this.setTextFieldBorderError(playerNumber*3+1, true); //player is invalid because of this thus highlight the textbox
                                }
                        //EquipmentID
                            if(eqID.length() == equipIDlength)
                                {
                                    if(eqIDs[i].equals(eqID))
                                    {
                                        valid = "false";
                                        this.setTextFieldBorderError(playerNumber*3+2, true);
                                    }
                                }
                        }
                    if(valid.equals("true"))
                        {
                            //make sure that the textborders are the default
                            this.setTextFieldBorderError(playerNumber*3,false);
                            this.setTextFieldBorderError(playerNumber*3+1,false);
                            this.setTextFieldBorderError(playerNumber*3+2,false);
                        }
                    return valid;
                    }
        }
    /*
     * @description: adds or removes the playerEntryPanel from the inputed frame
     *      @uses:
     * @param: {frame} JFrame: The frame to add or remove to/from
     * @param: {addRemove} Bool: true:add to the frame, false: remove from frame
     * @return: none
     */
    void visible(JFrame frame, boolean addRemove)
        {
            if(addRemove == true)
                frame.setContentPane(playerEntryPanel);
            else
                frame.remove(playerEntryPanel);
        }
    /*
     * @description: Checks what keys are pressed and sets boolean for it to true
     *      @uses: 
     * @param: KeyEvent from focused component
     * @return: none
     */	
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
    /*
     * @description: changes the border around a textField to show if there is something wrong or not
     *      @uses:  
     * @param: {textfield} int: the number in the textFields array to change
     * @param: {error} bool: what to set the border to, true: there is an error thus highlight it, false: set it to the default border(emptyBorder)
     * @return: none
     */
    void setTextFieldBorderError(int textfield, boolean error)
        {
            if(error) // if there is something wrong
                textField[textfield].setBorder(errorBorder);
            else
                textField[textfield].setBorder(emptyBorder);
        }


    void CreateTeams(Team greenTeam, Team redTeam)
    {
        for(int i = 0; i < 30; i=+2)
        {
            if(! textField[i*3].equals(""))
            {
                Player p = new Player(textField[i*3].getText(), textField[i*3+1].getText(), textField[i*3+2].getText(),greenTeam);
                greenTeam.players.add(p);
            }
        }
        for(int i = 1; i < 30; i=+2)
        {
            if(! textField[i*3].equals(""))
            {
                Player p = new Player(textField[i*3].getText(), textField[i*3+1].getText(), textField[i*3+2].getText(),redTeam);
                redTeam.players.add(p);
            }
        }
    }
}
