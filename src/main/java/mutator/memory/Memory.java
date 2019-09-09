package mutator.memory;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import mutator.proccess.WindowHandle;
import org.apache.log4j.Logger;

import static com.sun.jna.platform.win32.WinNT.PROCESS_ALL_ACCESS;

public class Memory {

    private Long minAddreass;
    private Long endAddress;
    private Logger log = Logger.getLogger(Memory.class);

    public void queryProcessAddressRange(WinDef.HWND hwnd){
        WinBase.SYSTEM_INFO info = new WinBase.SYSTEM_INFO();
//        IntByReference wow64Process = new IntByReference();//用于赋值
//        boolean flag = Kernel32.INSTANCE.IsWow64Process(hwnd,wow64Process);
//        if(flag){
//            wow64Process.getPointer().nativeValue(info.lpMaximumApplicationAddress);
//        }else{
//            throw new RuntimeException("进程未打开");
//        }
        IntByReference lpdwProcessId = new IntByReference();//用于赋值
        int Tid = User32.INSTANCE.GetWindowThreadProcessId(hwnd, lpdwProcessId);
        //获得进程句柄
        WinNT.HANDLE handle  =Kernel32.INSTANCE.OpenProcess(PROCESS_ALL_ACCESS,false, lpdwProcessId.getValue());
        System.out.println(lpdwProcessId.getValue());
     //   this.minAddreass = p1.nativeValue(info.lpMinimumApplicationAddress);
        Tlhelp32.MODULEENTRY32W lpme = new Tlhelp32.MODULEENTRY32W();
        System.out.println(Kernel32.INSTANCE.Module32FirstW(handle,lpme));
        if(Kernel32.INSTANCE.Module32FirstW(handle,lpme)) {
            Pointer p1 = lpme.modBaseAddr;
            this.minAddreass = p1.nativeValue(info.lpMinimumApplicationAddress);
            if(Kernel32.INSTANCE.Module32NextW(handle, lpme))
            {
               Pointer p2 = lpme.modBaseAddr;
               this.endAddress = p2.nativeValue(info.lpMaximumApplicationAddress);
            }
        }
        System.out.println(Kernel32.INSTANCE.GetLastError());
//        Long minAddreass  = Pointer.nativeValue(info.lpMinimumApplicationAddress);
//        Long maxAddress  =  Pointer.nativeValue(info.lpMaximumApplicationAddress);
//        //log.debug(minAddreass + " : " + maxAddress);
//        int lastError = Kernel32.INSTANCE.GetLastError();
//        if(lastError!=0){
//            throw new RuntimeException("获取内存起始地址失败！");
//        }
    }

    public static void main(String []args){
        Memory m = new Memory();
        WindowHandle w = new WindowHandle();
        WinDef.HWND hwnd = w.getWindowHandle("QQ");
        System.out.println(hwnd);
         m.queryProcessAddressRange(hwnd);
         System.out.println(m.minAddreass  + " : " + m.endAddress);
    }
}
