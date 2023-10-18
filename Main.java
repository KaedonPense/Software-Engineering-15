/*
    @This:
        @Name:  
        @Version:                       //#.#
        @State:                         //test, alpha, workInProgress, buggy, final
    @Who
        @Author(s):                     //name, name
        @Editor(s):
    @Last Edited
        @Date:                          
        @By:
        @What:
    @Description:
        @Use:
        @Has Access to: 
*/

//Imports
    import javax.swing.JFrame;
    import javax.swing.*;
    import java.awt.*;

public class Main extends JFrame
{
    //Variables
        //static JFrame window;   //This is the window as a whole to which JPanels are added
        String[] Screens = {"startScreen","playerEntryScreen","gameScreen"}; 
        String ControllingScreen = Screens[0]; //On app start, screen to display is startScreen //Used in run(), switch statement of which screen is active   
                                            
        StartScreen startScreen = new StartScreen(this);
        PlayerEntryScreen playerEntry= new PlayerEntryScreen(this);     //Initilises the Player Entry Panel class. Contains the creation and controller for the class. "this" is refering to the JFrame "window" 
    
    /* @description: The main function to be called when the application is to be ran
     *     @use : Initilizes Main, and starts the function to keep running
     * @param: none
     * @returns: none
     */
    public static void main(String[] args)
        {
            Main m = new Main();
            //Game g = new Game();
            m.run();
        }

    /* @description: Constructor for main
     *      @use: Sets up JFrame and calls create functions for Panels included in "window"
     *      @use: displays startup screen
     * @param: none
     * @returns: none
     */
    public Main()
        {
            //Creation of the Frame
                this.setTitle("Proton");
                this.setSize(1000,1000);
	        	this.setFocusable(true);
                this.setLayout(new FlowLayout());
		        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(true);
            //Creation of screens
                playerEntry.createPlayerEntryScreenContent();
                //TODO: add creation of game screen here

        }

    /* @discription: Keep running Function
     *      @use:  While the call to close JFrame "window" is false keep program running
     * @param: none
     * @returns: none
     */
    public void run()
        {
            Team greenTeam;
            Team redTeam;
            while(true) //keep running
                {
                    this.revalidate();  //this removes the now showing contents on start
                
                    switch(ControllingScreen)
                        {
                            case("startScreen"): //i.e Screens[0]
                                startScreen.run();
                                ControllingScreen = Screens[1]; //Change the screen to display to the player entry Screen
                                playerEntry.visible(this, true); //set the entry screen to be visible
                                this.pack();
                        	break;
                            case("playerEntryScreen"): //i.e Screens[1]
                                //TODO: event handeling
                                    playerEntry.Update();
                                    greenTeam = new Team(playerEntry.green, playerEntry.darkGreen);
                                    redTeam = new Team(playerEntry.red, playerEntry.darkRed);
                                    //On continue to game screen
                                        /*  if(playerEntry.checkValidPlayers == True)
                                         *      {   
                                         *          greenTeam = new Team(playerEntry.green, playerEntry.darkGreen);
                                         *          redTeam = new Team(playerEntry.red, playerEntry.darkRed);
                                         *          playerEntry.createTeams(greenTeam, redTeam);
                                         *          playerEntry.visible(this, false); //remove playerEntry panel from screen
                                         *          ControllingScreen = Screens[2]; //set controlling screen to gameScreen
                                         *      }
                                         */
                                break;
                            case("gameScreen"): //i.e Screens[2]
                                break;
                        }                    
                    //Delay statement - thread saver
                    try
                        {Thread.sleep(40);}
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
        }
}
