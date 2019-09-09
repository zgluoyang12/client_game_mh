package robot;

import mutator.util.RandomUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotHandler {

    private  Robot robot = null;

    private  int KEY_DELAY = 50;

    /**
     * 鼠标左键单击
     * @param x
     * @param y
     */
    public void leftMouseClick(int x , int y){
        try{
             robot = new Robot();
             //移动到指定坐标
             robot.mouseMove(x, y);
             robot.mousePress(KeyEvent.BUTTON1_MASK);
             robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 鼠标左键双击
     * @param x
     * @param y
     */
    public void leftMouseDbClick(int x , int y){
        try{
            robot = new Robot();
            //移动到指定坐标
            robot.mouseMove(x, y);
            robot.mousePress(KeyEvent.BUTTON1_MASK);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
            robot.delay(RandomUtils.random(KEY_DELAY));
            robot.mousePress(KeyEvent.BUTTON1_MASK);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 单按键操作
     * @param key
     */
    public void singleKeyClick(int   key){
        robot.keyPress(key);
        robot.keyRelease(key);
    }

    /**
     * 组合键
     * @param args
     */

    public void muchKeyClick(int firstKey , int secondKey){
        robot.keyPress(firstKey);
        robot.delay(KEY_DELAY);
        robot.keyPress(secondKey);
        robot.keyRelease(secondKey);
        robot.keyRelease(firstKey);
    }

    public static void main(String[] args){

    }

}
