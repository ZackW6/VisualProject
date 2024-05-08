package Canvas.Commands;

import Canvas.Util.Profile;

/**
 * Command class is a timer/thread mix, will atempt to match time allotted but if process takes too long will just run with 0 intended delay
 */
public class Command extends CommandBase{
    /**
     * Simplified Thread, meant to run once per var time
     * @param run Runnable to run
     * @param time Time per iteration milliseconds
     */
    private Thread thread;
    private boolean isThread=false;
    private double time;
    private Runnable runner;
    private Profile profile = new Profile();
    private boolean isFinished = false;
    private Runnable onFinish;
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
                if (isThread){
                    profile.start();
                    try {
                        runner.run();
                    } catch (Exception e) {
                        System.out.println("CaughtC");
                        e.printStackTrace();
                    }
                    
                    double expendedTime = profile.getTime();
                    profile.stop();
                    try {
                        if ((double)time - expendedTime>0){
                            Thread.sleep((long) time-(long)expendedTime);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        System.out.println("TIME");
                        e.printStackTrace();
                    }
                }else{
                    isFinished = true;
                    if (onFinish != null){
                        onFinish.run();
                    }
                    break;
                }
            }
        });
        thread.start();
    }
    /**
     * Start thread sequence
     */
    public void start(){
        isFinished = false;
        isThread=true;
        if (!thread.isAlive()){
            thread.start();
        }
    }
    /**
     * Stop thread sequence
     */
    public void stop(){
        isThread = false ;
        isFinished = false;
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
                if (isThread){
                    profile.start();
                    try {
                        runner.run();
                    } catch (Exception e) {
                        System.out.println("CaughtC");
                        e.printStackTrace();
                    }
                    
                    double expendedTime = profile.getTime();
                    profile.stop();
                    try {
                        if ((double)time - expendedTime>0){
                            Thread.sleep((long) time-(long)expendedTime);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        System.out.println("TIME");
                        e.printStackTrace();
                    }
                }else{
                    isFinished = true;
                    
                    if (onFinish != null){
                        onFinish.run();
                    }
                    break;
                }
            }
        });
    }
    /**
     * Whether or not the command is currently running
     * @return
     */
    public boolean isThreadRunning(){
        return isThread;
    }

    @Override
    public Runnable getRunnable() {
        return runner;
    }

    @Override
    public double getTimer() {
        return time;
    }

    public boolean isCommandFinished(){
        return isFinished;
    }

    public void whenCommandFinished(Runnable run){
        onFinish = run;
    }

}
