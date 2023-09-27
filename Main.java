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

public class Main extends JFrame
{
    static JFrame window;                                           //This is the window as a whole to which JPanels are added
    PlayerEntryScreen playerEntry= new PlayerEntryScreen(this);     //Initilises the Player Entry Panel class. Contains the creation and controller for the class. "this" is refering to the JFrame "window" 
    
    /*
     * @description: The main function to be called when the application is to be ran
     *     @does : Initilizes Main, and starts the function to keep running
     * @param: none
     * @returns: none
     */
    public static void main(String[] args)
    {
        Main m = new Main();
        m.run();
    }

    /*
     * @description: Constructor for main
     *      @does: Sets up JFrame and calls create functions for Panels included in "window"
     *      @does: displays startup screen
     * @param: none
     * @returns: none
     */
    public Main()
    {
        //Startup stuff
        //System.out.print("startInit");
        //View.View();
        this.setTitle("TableDemo");
        this.setSize(1000,1000);
		this.setFocusable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        playerEntry.createPlayerEntryScreenContent();
        //System.out.println("Initilised");
    }

    /*
     * @discription: Keep running Function
     *      @does:  While the call to close JFrame "window" is false keep program running
     * @param: none
     * @returns: none
     */
    public void run()
    {
        //keep running
        while(true)
        {
            //TODO: make update function event driven instead of always running
            playerEntry.Update();

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