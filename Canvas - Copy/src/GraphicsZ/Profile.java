package GraphicsZ;
public class Profile {
    private static int runs;//Only works for 1 version active at a time
    private static long startTime;
    private static long elapsedTime;
    private static long total;
    public static void timeStart(){
        runs++;
        startTime = System.nanoTime();
    }
    public static void timeEnd(){
        
        elapsedTime = System.nanoTime()-startTime;
        total+=elapsedTime;
    }
    public static double getAverage(){
        return total/runs;
    }
    public static long getTime(){
        return elapsedTime;
    }

}
