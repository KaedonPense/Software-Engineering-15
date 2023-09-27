/*
    @This:
        @Name:  
        @Version:                       //#.#
        @State:                         //test, alpha, workInProgress, buggy, final
    @Who
        @Author(s): Kaedon Pense                     //name, name
        @Editor(s):
    @Last Edited
        @Date: 9/26/23        
        @By: Kaedon Pense
        @What:
    @Description:
        @Use:
        @Has Access to: 
*/

//imports
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.table.*;
    import javax.swing.JScrollPane;
    import java.awt.event.ActionListener; 
    import java.awt.event.ActionEvent;
    import java.util.Scanner;



public class PlayerEntryScreen extends JFrame implements ActionListener// Could name this Window to be more accurate  
{
    //Static Variables
        static JPanel playerEntry; //The JPanel this is for. (creates and edits)
        static GridBagConstraints grid; //The layoutmanager for this pannel 
        static JTextField[] textField = new JTextField[90];    //An array of JTextfields, in this instance the number i%3 gives what the cell should be. = 0 -> playerID, = 1 -> Codename, =2 -> EquipmentID
        static String[] checkData = new String[90];     //An array of the previous contents of JTextfields - updated when a cell is changed
        static Color red = new Color(200,0,0);
        static Color green = new Color(0,200,0);
        static Color darkRed = red.darker();
        static Color darkGreen = green.darker();
        static Color gray = new Color(100,100,100);
        static Color darkGray = gray.darker();
        static int playerIDlength; //the required length of the player ID, use this to determine if cell is full -> do actions
        static int equipIDlength;

    /* @description: JPanel "playerEntry" constructor
     *      @use:   creates Jpanel, sets layoutmanager gridbag and its constraints, adds to passed in JFrame
     * @param {frame} the frame to add the panel to;
     * @return: none
     */
    PlayerEntryScreen(JFrame frame)
        {   
            playerEntry = new JPanel();
            playerEntry.setLayout(new GridBagLayout());
            grid = new GridBagConstraints();
            grid.fill = GridBagConstraints.BOTH;
            frame.add(playerEntry);
        }

    /* @description: Creates contents of JPanel Player Entry Screen
     *      @use:   The panel itself is created elsewhere this just creates and adds contents
     * @param: none
     * @return: none
     */
    void createPlayerEntryScreenContent()
        {
            int columnShift = 0; //Shift columns right n cells. USE: if we want to have a column on the left for player number
            int row = 0; //the row to add items to
            //create the labels
                //Teams - green and red at very top of panel
                    //Initilizing How to add
                        Font team = new Font("impact", Font.ITALIC, 25);
                        this.gridPadding(100,100);
                        this.gridFill(true,true);
                    //Adding text to screen
                        this.addJLabel("Green",1+columnShift, 0, team, green, null);
                        this.addJLabel("Red", 4+columnShift, 0, team, red, null);
        
                //Column Headings - Text displayed above textfields
                    String[] heading = {"PlayerID","Codename","EquipID","PlayerID","Codename","EquipID"}; //The heading for cells
                    row = 1;
                    //How to add
                        //Font //May have to change the font
                        this.gridPadding(50,0); //set minimum width to (h,v);
                        this.gridFill(true,false); //fill horizontally but not vertically
                    for(int i = 0; i < 6; i++) //Adding headings
                    {
                        this.addJLabel(heading[i], i+columnShift, row);
                    }

        
            //create text textfields
                //TODO: FUTURE : Instead of adding these to the PlayerEntry Panel add them to a scrollable Panel;
                //setup how to add to grid
                    this.gridPadding(100,0);   //set minimum width to 100   
                    this.gridFill(true,false);  //set to fill horizontally
                row = 2; //Start adding at the 2nd row. row 0 is the teams, row 1 is the column heading
                Color[] colorsToUse = {green,red,darkGreen,darkRed}; // the array of colors to use in this for loop
                int setColor = 0; //the index of what color in colorsToUse 
                for(int i = 0; i < 15; i++) //add row for each of 15 players per team. 1 Row includes player n from green and red team
                {   
                    JPanel greenPlayer = new JPanel(); //create and fill new green player fields
                        //Background
                            greenPlayer.setBackground(colorsToUse[setColor]);
                            setColor++; //change the color to red, normal or dark respectively
                            //Shouldn't need to set layout as it should automatically set it to add filling a column then if row is full add to next row
                        //Contents   
                            for(int field = 0; field < 3; field++)
                            {
                                JTextField textbox = this.addTextField(null, 1); //creates a new textField for every field the player needs. Sets text to null, columns to 1
                                textField[i*6 + field] = textbox;   //sets respective textbox in array equal to this textbox
                                checkData[i*6 + field] = null;  //set respective "past data value" to the initial text value;
                                greenPlayer.add(textbox); //add this textbox to the pannel.
                            } 
                            this.gridCell(columnShift, i+row); //set to add to cell (shift, i+row)
                            playerEntry.add(greenPlayer, grid); //add to playerEntry in respective column
                    JPanel redPlayer = new JPanel(); //create and fill new red player fields
                        //Background
                            redPlayer.setBackground(colorsToUse[setColor]);
                            setColor++; //change color back to green, normal or dark respectively
                            //Shouldn't need to set layout as it should automatically set it to add filling a column then if row is full add to next row
                            if(setColor == 4)   //the last color in array was just used so go back to the start of colorsToUse.
                                setColor = 0; 
                        //Contents
                            for(int field = 3; field < 6; field++)
                            {
                                JTextField textbox = this.addTextField(null, 1); //creates a new textField for every field the player needs. Sets text to null, columns to 1
                                textField[i*6 + field] = textbox;   //sets respective textbox in array equal to this textbox
                                checkData[i*6 + field] = null;  //set respective "past data value" to the initial text value;
                                redPlayer.add(textbox); //add this textbox to the panel.
                            }
                            this.gridCell(columnShift + 3, i+row);  //set to add to cell (shift, i+row)
                            playerEntry.add(redPlayer, grid);       //add to playerEntry in respective column
                    
                }
        }
    /* @description: sets the minimum x and y size
     *      @use: Something needs to be at least a certain size
     * @param {padx} int: the minimum horizontal size to set to
     * @param {pady} int: the minimum vertical size to set to 
     */
    void gridPadding(int padx, int pady)
        {
        grid.ipadx = padx; //sets the gridConstraints minimum horizontal size
        grid.ipady = pady; //sets the gridConstraints minimum vertical size
        }
    /* @description: how to fill the cell
     *      @use: changing how something fills the cell;
     * @param {horizontal} bool: wether or not to fill horizontally
     * @param {vertical} bool: wether or not to fill vertically
     */
    void gridFill(boolean horizontal, boolean vertical)
        {
            if(horizontal)
                grid.fill = GridBagConstraints.HORIZONTAL;
            if(vertical)
                grid.fill = GridBagConstraints.VERTICAL;
            if(horizontal && vertical)
                grid.fill = GridBagConstraints.BOTH;
            if(!horizontal && !vertical)
                grid.fill = GridBagConstraints.NONE;
        }
    /* @description: what cell to add to
     *      @use: changes the cell to add to when JPanel.add(what, grid) is called
     * @param {column} int: the column to add to
     * @param {row} int: the row to add to
     */
    void gridCell(int column, int row)
        {
            grid.gridx = column;
            grid.gridy = row;
        }
    /* @description: Create noneditable string on screen //NOTE: See other JLabel constructor bellow for a more detailed creator
     *      @use: Creates a Jlabel(string) and adds it to layout (c,r)
     * @param {text} string: text that is to be displayed on screen
     * @param {c} integer: the column to add the JLabel to
     * @param {r} integer: the row to add the JLabel to
     * @return {JLabel} the label created in this //NOTE: as of 9/26/23 the return isn't used for anything 
     */
    JLabel addJLabel(String text, int c, int r)
        {
            JLabel label = new JLabel(text);    //create a new string Label
            //set the cell it should be in, collum c, row r
                grid.gridx = c;                    
                grid.gridy = r;
            label.setHorizontalAlignment(SwingConstants.CENTER);    //Center the text in its cell
            playerEntry.add(label, grid);   //add to playerEntry panel
            return label;   //return statement in case need to use this label outside of this function
        }
    /* @description: Detailed create noneditable text on screen //NOTE:if padding, font, and color are to be null use simple creator above
     *      @use: Creates a Jlabel(string) and adds it to layout (c,r), fills the cell, sets font and color
     * @param {text} string: text that is to be displayed on screen
     * @param {c} int: the column to add the JLabel to
     * @param {r} int: the row to add the JLabel to
     * @param {font} Font: the font to use : null if using default
     * @param {foreground} Color: the color of the text : null if using default
     * @param {background} Color: the color of the background, (highlight?) : null if using defalult
     * @return {JLabel} the label created in this //NOTE: as of 9/26/23 the return isn't used for anything 
     */
    JLabel addJLabel(String text, int c, int r, Font font, Color foreground, Color background)
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
                    label.setBackground(background);
            playerEntry.add(label, grid);   
            return label;
        }
    /* @description: create textbox function
     *      @use: creates a textbox with contents (text) with n columns in a cell and sets fill
     * @param {test} string: the text to be entered into the textbox //NOTE: If to be blank then equals null;
     * @param {columns} int: how many columns should the textbox have //NOTE: Default should be 1
     * @return returns the created textfield //NOTE: this is used to initilize array textfields
     */
    JTextField addTextField(String text, int columns)
        {
            JTextField textbox = new JTextField(text, columns); //create new textbox
            return textbox;
        }
    /* @description: Runs To see what cells have changed
     *      @use: when a cell has changed make sure everything is in parameters and handle conditinals
     * @param: none
     * @return: none //NOTE: may make this return true/false if something changed
     */
    void Update() //TODO: make this event triggered not continuously ran
        {
            String newData; //will be the current text in a textbox
            String oldData; //will be the text that was in the cell last time this function ran
        
            for(int i = 0; i < 90; i++) //check all textboxes
            {
                newData = textField[i].getText();  //get text from box
                if(newData != null) // there is something in the cell   
                {
                    oldData = checkData[i]; //previous data in cell
                    if(newData.equals(oldData)) // in java == checks if they are the same object not if they have the same content. ".equals()" basicly says if(valueOf == valueOf) //With == it is always false because it is checking if string == string
                        {}
                    else //if the data in cell has changed
                        {
                            checkData[i] = newData; //set the data of "previous data in cell" to the new cell data
                            switch(i%3) //this handles the 3 cases of what the data is in reference to. If 0 its a playerID, if 1 its a codename, if 2 its a equipmentID
                            {
                                case(0): //is playerID
                                    if(newData.length() == playerIDlength)
                                    {
                                        //TODO: ADDHERE: Search database for ID, returning codename
                                            //if not found search function returns null
                                                //Later : try and highlight codename cell to show that codename needs to be entered
                                                    //For this may have a setError() function that changes the border of textbox to indicate an error
                                                    //Will also need a setNotError() to undo border
                                            //if found      textfield[i+1].setText(found name);
                                    }; 
                                    break;
                                case(1): //is codename
                                    //DO stuff
                                    break;
                                case(2): //is equipmentID
                                    if(newData.length() == equipIDlength)
                                    {
                                        //TODO: ADDHERE: Transmit code
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
                String check = this.checkPlayer(playerIDs, playerNames, equipmentIDs, playerID, codename, equipmentID);
                if(check.equals("empty"))
                {
                    //player is empty thus valid
                }
                if(check.equals("true"))
                {

                }
                
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
    /* @description: Check if a player has any errors/isvalid
     *      @use: Checks the values of inputed player against inputed arrays and returns appropriate string //Makes sure there are no duplicates
     * @param {pIDs} array of strings: A collection of all player ID's already ran/checked
     * @param {names} array of strings: A collection of all player codenames that have already been ran/checked
     * @param {eqIDs} array of strings: A collection of all player equipmentIDs that have already been checked
     * @param {id} string: the player ID to look for
     * @param {name} string: the codename to look for
     * @param {eqID} string: the equipment ID to look for
     */
    String checkPlayer(String[] pIDs, String[] names, String[] eqIDs, String id, String name, String eqID)
        {
            String valid = "true"; 
            //check Empty
                if((id == null)&&(name == null)&&(eqID == null))
                    return "empty"; //i.e. the textfield are valid
                else
                    {   
                    //Checking individual null
                        if(id == null)
                            {
                                valid = "false";
                                //TODO: handle event
                            }
                        if(name == null)
                            {
                                valid = "false";
                                //TODO: handle event
                            }
                        if(eqID == null)
                            {
                                valid = "false";
                                //TODO: handle event
                            }
                    //Check against array and check length
                        for(int i = 0; pIDs[i=1] != null; i++)
                            {
                            //playerID
                                if(id.length() == playerIDlength)
                                    {
                                        if(pIDs[i].equals(id))
                                        {
                                            valid = "false";
                                            //TODO: handle event
                                        }
                                    }
                                else
                                    {
                                        valid = "false";
                                        //Todo: handle event
                                    }
                            //codename
                                if(names[i].equals(name))
                                    {
                                        valid = "false";
                                        //TODO: handle event
                                    }
                            //EquipmentID
                                if(eqID.length() == equipIDlength)
                                    {
                                        if(eqIDs[i].equals(eqID))
                                        {
                                            valid = "false";
                                            //TODO: handle event
                                        }
                                    }
                                else
                                    {
                                    valid = "false";
                                      //TODO: handle event
                                    }
                            }
                    return valid;
                    }
        }
}