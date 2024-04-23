package Canvas;

public abstract class CommandBase {
    public abstract void start();

    public abstract void stop();

    public abstract void setRunnable(Runnable runner);

    public abstract boolean isThreadRunning();
}
