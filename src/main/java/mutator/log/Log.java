package mutator.log;

public class Log {

    private static boolean printLog =  true;

    public static void info(String info){
        if(printLog){
            System.out.println(info);
        }
    }
}
