import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Timer extends Thread
{
    int sec = 0;
    int min = 0;
    public JLabel timerText = new JLabel();
    Timer(int time)
    {
        timerText.setHorizontalAlignment(SwingConstants.CENTER);
        sec = time%60;
        min = (time - sec)/60;
        //timerText.setFont()
        timerText.setText(min + ":" + sec);
    }

    public int getTime()
    {
        return min * 60 + sec;
    }

    public void run()
    {
        while( sec + min > 0)
        {
            sec--;
            if(sec < 0 && min !=0)
            {
                min--;
                sec=60;
            }
            timerText.setText(min + ":" + sec);
            try
            {
                Thread.sleep(999); //should sleep for slightly less than 1 second to allow for runtime of code
            }
            catch(InterruptedException e)
            {
                System.out.println("Timer can't sleep");
            }
        }
            PlayerActionScreen.timerSem.release();
    }
}

/*
 * How to call and run function the following is code, to be added in game
 * import java.util.concurrent.Semaphore;
 * public static Semaphore timerSem;
 * 
 * //in constructor 
 *  timerSem = new Semaphore(1, true);
 * 
 * //when creating new timer
 * try
 * {
 *      timerSem.acquire();
        timer = new Timer(length of timer in seconds);
        timer.start();
    }
    catch(InterruptedException e)
    {
        System.out.println("Can't get new timer thread");
        System.out.println("Timer not started");
    }
 *  
 * Timer timer = new timer(seconds); //the timer is on a seperate thread allowing concurnet running of programs;
 * timer.start(); //This is how you start/call the "run" function for threads
 */ 
