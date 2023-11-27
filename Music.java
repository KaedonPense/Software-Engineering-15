import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread{

    FileInputStream fis;
    Player playMP3;
    public Music()
    {
        try
        {
            fis = new FileInputStream("Track01.mp3");
            playMP3 = new Player(fis);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to initialize the player.");
        }
        
    }

    public void run()
    {
        try{
            playMP3.play();
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }

    }

    public void stopPlaying() 
    {
        if (playMP3 != null) {
            playMP3.close();
        }
    }
}



