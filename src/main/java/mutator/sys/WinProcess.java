package mutator.sys;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import lombok.Data;
import mutator.memory.Memory;
import mutator.window.Window;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

@Data
public class WinProcess {

    private  IntByReference lpRoccessId = new IntByReference();

    public void GetWindowThreadProcessId(WinDef.HWND hwnd){
        User32.INSTANCE.GetWindowThreadProcessId(hwnd,lpRoccessId);
    }

    public static void main(String [] args){
        Window w = new Window(null ,"QQ");
        WinProcess p = new WinProcess();
        p.GetWindowThreadProcessId(w.getHwnd());
        System.out.println(p.getLpRoccessId().getValue());

        WinSystem.GetSystemInfo();

    }
}
