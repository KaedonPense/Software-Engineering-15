import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Timer extends Thread
{
    int sec = 0;
    int min = 0;
    String s1 = "0"; 
    String s10 = "0";
    public JLabel timerText = new JLabel();
    
    Timer(int time)
    {
        timerText.setHorizontalAlignment(SwingConstants.CENTER);

        
        sec = time%60;
        if(sec > 30)
            sec = 15;
        min = (time - sec)/60;
        s1 = Integer.toString(sec%10);
        s10 = Integer.toString((sec - sec%10)/10);
        System.out.println(s10);
        timerText.setText(min + ":" + s10 + s1);
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
            s1 = Integer.toString(sec%10);
            s10 = Integer.toString((sec - sec%10)/10);
            //System.out.println(s10);
            timerText.setText(min + ":" + s10 + s1);
            //timerText.setText(min + ":" + sec);
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
