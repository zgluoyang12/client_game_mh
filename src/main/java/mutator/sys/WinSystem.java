package mutator.sys;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import lombok.Data;
import mutator.log.Log;
import mutator.window.Window;

@Data
public class WinSystem {

    private static WinBase.SYSTEM_INFO system_info = new WinBase.SYSTEM_INFO();

    public static void GetSystemInfo(){
        Kernel32.INSTANCE.GetSystemInfo(system_info);
        Log.info(Pointer.nativeValue(system_info.lpMinimumApplicationAddress) + " : "  + Pointer.nativeValue(system_info.lpMaximumApplicationAddress)) ;
        //Log.info()
    }

    public static void givePermission(WinNT.HANDLE hwnd, String privilegeValue){
        WinNT.HANDLEByReference tokenHandle = new WinNT.HANDLEByReference();
        boolean open = Advapi32.INSTANCE.OpenProcessToken(hwnd, WinNT.TOKEN_ADJUST_PRIVILEGES| WinNT.TOKEN_QUERY,tokenHandle);
        if(open){
            //创建一个令牌特权,初始化为1,用于保存LookupPrivilegeValue函数返回的令牌特权
            WinNT.TOKEN_PRIVILEGES tkp = new WinNT.TOKEN_PRIVILEGES(1);
            tkp.Privileges[0] = new WinNT.LUID_AND_ATTRIBUTES();
            tkp.Privileges[0].Luid = new WinNT.LUID();
            tkp.Privileges[0].Attributes = new WinDef.DWORD(WinNT.SE_PRIVILEGE_ENABLED);
            boolean lookopen = Advapi32.INSTANCE.LookupPrivilegeValue(null , privilegeValue,tkp.Privileges[0].Luid);
            if(lookopen){
                if( Kernel32.INSTANCE.GetLastError() != 0){
                    throw new RuntimeException("LookupPrivilegeValue 失败 : " + Kernel32.INSTANCE.GetLastError());
                }
                Advapi32.INSTANCE.AdjustTokenPrivileges(tokenHandle.getValue(),false, tkp, tkp.size(), null, null);
                if( Kernel32.INSTANCE.GetLastError() != 0){
                     throw new RuntimeException("AdjustTokenPrivileges 失败 : " + Kernel32.INSTANCE.GetLastError());
                }
            }else{
                Log.info("LookupPrivilegeValue失败 : "  + Kernel32.INSTANCE.GetLastError());
            }
        }else{
            Log.info("打开进程令牌失败 : " +  Kernel32.INSTANCE.GetLastError());
        }
    }

    public static void  main(String []args){
        Window w = new Window(null , "QQ");
        WinSystem.givePermission(w.getHwnd(),"QQ");
    }
}
