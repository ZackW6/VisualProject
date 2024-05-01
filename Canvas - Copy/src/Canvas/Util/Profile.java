package Canvas.Util;
public class Profile {
    private int runs;
    private long startTime;
    private long total;
    public Profile (){};
    public void start(){
        runs++;
        startTime = System.currentTimeMillis();
    }
    public void stop(){
        runs++;
        total+=getTime();
    }
    public double getAverage(){
        return total/runs;
    }
    public long getTime(){
        return System.currentTimeMillis()-startTime;
    }

}
