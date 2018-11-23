public class Timer{
    public static long time;
    public static long delay;
    private static boolean update = true;
    
    public static void updateTimer(){
        if(update == true){
            time = System.currentTimeMillis() - delay;
        }
    }
    public static void pauseTimer(){
        update = false;
    }
    public static void continueTimer(){
        delay = System.currentTimeMillis() - time;
        update = true;
    }
}