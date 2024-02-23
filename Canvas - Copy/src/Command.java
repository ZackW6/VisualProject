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
     * start thread sequence
     */
    public void start(){
        thread.start();
        isThread=true;
    }
    /**
     * stop thread sequence
     */
    public void stop(){
        thread.interrupt();
        isThread=false;
    }
    /**
     * reset time per iteration
     */
    public void setTime(double time){
        setCommand(runner,time);
    }
    /**
     * reset runnable in command
     */
    public void setRunnable(Runnable runner){
        setCommand(runner,time);
    }
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
    public boolean isThreadRunning(){
        return isThread;
    }
    public void inputMethod(Runnable method){
        
    }

}
