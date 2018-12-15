import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTimer{
    private static Timer timer;
    private static AtomicInteger timeLeft;

    public static void initTimer(int secs){
	timeLeft = new AtomicInteger(secs);
	TimerTask task = new TimerTask(){
		@Override
		public void run() {
		    int t1 = timeLeft.decrementAndGet();
		    if (t1 == 0){
			System.out.println("You have ran out of time, GG");
			System.exit(0);
		    }
		}
	    };
	timer = new Timer();
	timer.schedule(task, 0, 1000);
    }
    public static int getTime(){
	return timeLeft.intValue();
    }
    public static void addTime(int secs){
	timeLeft.addAndGet(secs);
	System.out.println(secs + " seconds have been added to the clock");
    }
}
