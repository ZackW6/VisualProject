package Canvas.Commands;

public abstract class CommandBase {
    protected Thread thread;

    public abstract void start();

    public abstract void stop();

    public abstract void setRunnable(Runnable runner);

    public abstract boolean isThreadRunning();

    public abstract Runnable getRunnable();

    public abstract double getTimer();

    @SuppressWarnings("static-access")
    public void sleep(double time){
        try {
            thread.sleep((long)time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
