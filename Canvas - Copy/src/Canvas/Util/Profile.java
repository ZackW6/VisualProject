package Canvas.Util;
public class Profile {
    private int runs;
    private long startTime;
    private long total;
    public Profile (){};
    public void timeStart(){
        runs++;
        startTime = System.currentTimeMillis();
    }
    public void timeEnd(){
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
