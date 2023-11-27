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
        if (sec < 10)
            timerText.setText(min + ":0" + sec%60);
        else
            timerText.setText(min + ":" + sec%60);
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
                sec=59;
            }
            if (sec < 10)
                timerText.setText(min + ":0" + sec%60);
            else
                timerText.setText(min + ":" + sec%60);
            try
            {
                Thread.sleep(999); //should sleep for slightly less than 1 second to allow for runtime of code
            }
            catch(InterruptedException e)
            {
                System.out.println("Timer can't sleep");
            }
        }
    }
    public void setTime(int time)
    {
        sec = time%60;
        min = (time - sec)/60;
    }
}
