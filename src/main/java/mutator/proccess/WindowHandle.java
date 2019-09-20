package mutator.proccess;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;
import robot.RobotHandler;

import java.awt.event.KeyEvent;

/**
 * windows 窗口操作类
 */
public class WindowHandle {
    

    /**
     * 获取窗口句柄
     */
    public WinDef.HWND getWindowHandle(String windowName){
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null,windowName);
        if (hwnd==null)throw new RuntimeException("窗口不存在,请先运行程序");
        else {
            //打开窗口
            boolean showed = User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_RESTORE );
            //System.out.println(showed);
            //关闭窗口
           // boolean a = User32.INSTANCE.CloseWindow(hwnd);
        }
        return hwnd;
    }

    public void findChildrenWindow(WinDef.HWND hwnd){
        User32.INSTANCE.FindWindowEx(hwnd,null , null , null);

    }

    public int[] getWindowXy(WinDef.HWND hwnd) {
        WinDef.RECT rect = new  WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        System.out.println(rect.left + ":" + rect.top);
        int [] xy = new int[] {rect.left , rect.top};
        return xy;
    }

    public static void main(String []args){
        WindowHandle w = new WindowHandle();
        WinDef.HWND hwnd = w.getWindowHandle("QQ");
        int []xy = w.getWindowXy(hwnd);

        RobotHandler r = new RobotHandler();
        int y =  xy[1] + 213;
        int x = xy[0] + 130;
        r.leftMouseDbClick(x ,y);
       //11111111
        WinDef.HWND hwnd2 =  w.getWindowHandle("Smile");

        int smileXy[] = w.getWindowXy(hwnd2);

        int smileX = smileXy[0] + 399;
        int smileY = smileXy[1] + 448;
        r.leftMouseClick(smileX, smileY);
        r.singleKeyClick(KeyEvent.VK_0);
        r.muchKeyClick(0x11,KeyEvent.VK_ENTER);

        //打开背包界面
        r.muchKeyClick(KeyEvent.VK_ALT,KeyEvent.VK_E);
    }

}
