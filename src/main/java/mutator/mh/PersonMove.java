package mutator.mh;

import mutator.util.RandomUtils;
import robot.RobotHandler;

import java.awt.event.KeyEvent;

public class PersonMove {

    private RobotHandler robotHandler = new RobotHandler();

    public void moveTo(int x , int y){
        //先打开地图键
        try{
            long time = Long.valueOf(RandomUtils.random(100));
            robotHandler.singleKeyClick(KeyEvent.VK_TAB);
            Thread.sleep(time);
            robotHandler.leftMouseClick(x,y);
            //关闭地图
            robotHandler.singleKeyClick(KeyEvent.VK_TAB );
        }catch(Exception e){

        }

    }

}
