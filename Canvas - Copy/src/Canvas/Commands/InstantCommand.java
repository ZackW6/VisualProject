package Canvas.Commands;

/**
 * Command class is a thread
 */
public class InstantCommand extends CommandBase{
    /**
     * Simplified Thread runOnce
     * @param run Runnable to run
     */
    private boolean isThread=false;
    private Runnable runner;
    /**
     * Runnable you wish to run
     * @param runner
    */
    public InstantCommand(Runnable runner){
        this.runner=runner;
        thread = new Thread(() -> {
            try {
                runner.run();
            } catch (Exception e) {
                System.out.println("CaughtIC");
                e.printStackTrace();
            }
            
            isThread = false;
        });
    }
    /**
     * Start thread sequence
     */
    @Override
    public void start(){
        thread.start();
        isThread=true;
    }
    /**
     * Stop thread sequence
     */
    @Override
    public void stop(){
        thread.interrupt();
        isThread=false;
    }
    /**
     * Reset the command as new with these parameters
     * @param runner
     */
    @Override
    public void setRunnable(Runnable runner){
        stop();
        this.runner=runner;
        thread = new Thread(() -> {
            try {
                runner.run();
            } catch (Exception e) {
                System.out.println("CaughtIC");
                e.printStackTrace();
            }
            isThread=false;
        });
        if (isThread){
            start();
        }
    }
    /**
     * Whether or not the command is currently running
     * @return
     */
    @Override
    public boolean isThreadRunning(){
        return isThread;
    }
    @Override
    public Runnable getRunnable() {
        return runner;
    }
    @Override
    public double getTimer() {
        return 10;
    }
}
