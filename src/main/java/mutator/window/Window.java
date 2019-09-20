package mutator.window;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import lombok.Data;
import mutator.util.WindowMessage;

@Data
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
        System.out.println(this.hwnd);
        User32.INSTANCE.PostMessage(this.hwnd,msg ,wparam , lparam);
    }

    public static void main(String[] args){
        Window window = new Window(null , "QQ");
        window.PostMessage(WindowMessage.WM_LBUTTONDBLCLK, 1 ,15007861);
        System.out.println(117 + 229 * 65536);
    }
}
