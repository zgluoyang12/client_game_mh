package mutator.util;


public class RandomUtils {

    public static int random(int scale){
        return (int)(Math.random() * scale + 10);
    }

    public static void main(String[] args){
        System.out.println(RandomUtils.random(50));
    }
}
