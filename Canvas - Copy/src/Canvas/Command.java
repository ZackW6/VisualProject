package Canvas;
/**
 * Command class is a timer/thread mix
 */
public class Command {
    /**
     * Simplified Thread, meant to run once per var time
     * @param run Runnable to run
     * @param time Time per iteration milliseconds
     */
    private Thread thread;
    private boolean isThread=false;
    private double time;
    private Runnable runner;
    /**
     * Runnable you wish to run, time per iteration
     * @param runner
     * @param time
     */
    public Command(Runnable runner, double time){
        this.time=time;
        this.runner=runner;
        thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep((long) time);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runner.run();
            }
        });
    }
    /**
     * Start thread sequence
     */
    public void start(){
        thread.start();
        isThread=true;
    }
    /**
     * Stop thread sequence
     */
    public void stop(){
        thread.interrupt();
        isThread=false;
    }
    /**
     * Reset time per iteration
     */
    public void setTime(double time){
        setCommand(runner,time);
    }
    /**
     * Reset runnable in command
     */
    public void setRunnable(Runnable runner){
        setCommand(runner,time);
    }
    /**
     * Reset the command as new with these parameters
     * @param runner
     * @param time
     */
    public void setCommand(Runnable runner, double time){
        stop();
        this.runner=runner;
        this.time=time;
        thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep((long) time);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runner.run();
            }
        });
        if (isThread){
            start();
        }
    }
    /**
     * Whether or not the command is currently running
     * @return
     */
    public boolean isThreadRunning(){
        return isThread;
    }
}
