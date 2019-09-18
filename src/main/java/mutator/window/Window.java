package mutator.window;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class Window {



    private  WinDef.HWND hwnd;

    public Window(String className , String windowName){
        this.hwnd = User32.INSTANCE.FindWindow(className, windowName);
    }

    public void SendMessage(int msg , int wparamVal , int lparamVal){
        WinDef.WPARAM wparam = new WinDef.WPARAM(wparamVal);
        WinDef.LPARAM lparam = new WinDef.LPARAM(lparamVal);
        User32.INSTANCE.SendMessage(this.hwnd,msg , wparam , lparam);
    }

    public void PostMessage(int msg , int wparamVal , int lparamVal){
        WinDef.WPARAM wparam = new WinDef.WPARAM(wparamVal);
        WinDef.LPARAM lparam = new WinDef.LPARAM(lparamVal);
        User32.INSTANCE.PostMessage(this.hwnd,msg ,wparam , lparam);
    }
}
