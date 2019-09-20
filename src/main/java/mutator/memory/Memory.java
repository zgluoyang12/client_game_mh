package mutator.memory;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import lombok.Data;
import mutator.log.Log;
import mutator.proccess.WindowHandle;
import mutator.sys.WinSystem;
import org.apache.log4j.Logger;

import static com.sun.jna.platform.win32.Tlhelp32.TH32CS_SNAPMODULE;
import static com.sun.jna.platform.win32.WinNT.*;

@Data
public class Memory {

    private Pointer minAddreass;
    private Pointer endAddress;
    private Logger log = Logger.getLogger(Memory.class);

    public int memoryScore = 0;

    public  void queryProcessAddressRange(WinDef.DWORD pid){
        Log.info("Memory queryProcessAddressRange pid : " + pid.intValue());
        WinNT.HANDLE module = Kernel32.INSTANCE.CreateToolhelp32Snapshot(TH32CS_SNAPMODULE,pid);
        lastError(Kernel32.INSTANCE.GetLastError());
        Tlhelp32.MODULEENTRY32W moduleentry32W = new Tlhelp32.MODULEENTRY32W();
        boolean  firstFlag = Kernel32.INSTANCE.Module32FirstW(module, moduleentry32W);
        //Kernel32.INSTANCE.OpenProcess(PROCESS_ALL_ACCESS, false, pid.intValue());
        if(firstFlag){
            this.minAddreass =   moduleentry32W.modBaseAddr;
        }
        boolean nextFlag = Kernel32.INSTANCE.Module32NextW(module , moduleentry32W);
        if(nextFlag){
            this.endAddress =moduleentry32W.modBaseAddr;
        }

        Log.info("开始:" + this.minAddreass + " , 结束： " + this.endAddress);
    }

    public void searchMemory(int pid){
        WinNT.HANDLE handle = Kernel32.INSTANCE.OpenProcess(PROCESS_ALL_ACCESS, false, pid);
       // WinSystem.givePermission(handle ,SE_DEBUG_NAME);
        lastError(Kernel32.INSTANCE.GetLastError());
        WinNT.MEMORY_BASIC_INFORMATION memoryInfo = new WinNT.MEMORY_BASIC_INFORMATION();
        BaseTSD.SIZE_T size = new BaseTSD.SIZE_T();
        while(Pointer.nativeValue(this.minAddreass) <= Pointer.nativeValue(this.endAddress)){
            BaseTSD.SIZE_T s  =Kernel32.INSTANCE.VirtualQueryEx( handle ,this.minAddreass , memoryInfo, new BaseTSD.SIZE_T( memoryInfo.size()));
            Log.info("内存大小(regionSize) ： " + memoryInfo.regionSize.longValue() + "byte ");
            Log.info("内存保护状态 :"  + memoryInfo.protect);
            if(memoryInfo.protect.intValue() != PAGE_READONLY){
               // throw new RuntimeException("内存不可读");
            }
            if(s.intValue() == 0)  break;
            Log.info("内存搜索状态 ： " + memoryInfo.state.intValue());
            //判断内存是否已提交,非空闲内存
            Pointer buffer = new Pointer(memoryInfo.regionSize.intValue());
           if(memoryInfo.state.intValue() == MEM_COMMIT){

               IntByReference intByReference = new IntByReference();
               boolean flag = Kernel32.INSTANCE.ReadProcessMemory(handle,  this.minAddreass,buffer, memoryInfo.regionSize.intValue(), intByReference);
               Log.info(buffer.toString());
               if(!flag){
                   throw new RuntimeException("内存读取失败:" +  Kernel32.INSTANCE.GetLastError());
               }
               for(int i = 0; i < memoryInfo.regionSize.intValue(); i++){
                   int memoryValue = buffer.getByte(i);
                   memoryScore++;
                   Log.info("address : " + (Pointer.nativeValue(this.minAddreass) + i)  + "value : " + memoryValue);
               }
           }

        }
    }

    private void lastError(int lastError){
        Log.info("lasterror : " + lastError);
        if(lastError == 5){
            throw new RuntimeException("无法打开进程,系统Debug权限获取失败,请以管理员方式重新运行程序!");
        }else if(lastError == 299){
            throw new RuntimeException("权限不足，只有部分权限!");
        }else if(lastError != 0){
            throw new RuntimeException("无法打开该进程,OpenProcess函数返回错误码:" + lastError);
        }
    }

    public static void main(String []args){
        Memory m = new Memory();
        //140725612707840
        m.queryProcessAddressRange(new WinDef.DWORD(5928));
        m.searchMemory(5928);
    }
}
