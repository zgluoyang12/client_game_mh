package mutator.proccess;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sun.jna.platform.win32.WinNT.HANDLE;


import static com.sun.jna.platform.win32.WinNT.TOKEN_ADJUST_PRIVILEGES;
import static com.sun.jna.platform.win32.WinNT.TOKEN_QUERY;

public class ProccessOperation {

    public static ProccessOperation proccessOperation = new ProccessOperation();

    private List<String> processIds  = new ArrayList<String>() ;


    private ProccessOperation(){}

    public static ProccessOperation getProccessOperation(){
        if(null != proccessOperation){
            return proccessOperation;
        }else{
            proccessOperation = new ProccessOperation();
            return proccessOperation;
        }
    }

    public void adminPermission(WinNT.HANDLE handle,String privilegeValue){
        WinNT.HANDLEByReference tokenHandle = new WinNT.HANDLEByReference();
        Advapi32.INSTANCE.OpenProcessToken(handle,TOKEN_ADJUST_PRIVILEGES|TOKEN_QUERY,tokenHandle);
        //创建一个令牌特权,初始化为1,用于保存LookupPrivilegeValue函数返回的令牌特权
        WinNT.TOKEN_PRIVILEGES tkp = new  WinNT.TOKEN_PRIVILEGES(1);
        //初始化令牌特LUID值
        tkp.Privileges[0] = new WinNT.LUID_AND_ATTRIBUTES();
        tkp.Privileges[0].Luid = new WinNT.LUID();
        //tkp.Privileges[0].Attributes   = WinNT.SE_PRIVILEGE_ENABLED
        //查看系统权限的特权值,返回到tkp LUID
        if(Advapi32.INSTANCE.LookupPrivilegeValue(null, privilegeValue, tkp.Privileges[0].Luid))
        {
            //告诉系统启用该令牌
            Advapi32.INSTANCE.AdjustTokenPrivileges(tokenHandle.getValue(), false, tkp, tkp.size(), null, null);
        }
    }

    /**
     * 按进程名搜索进程
     * @param processName
     * @return
     */
    public  List<String> searchByProcessName(String processName){
        Runtime runtime = Runtime.getRuntime();
        List tasklist = new ArrayList();
        BufferedReader reader =null;
        Process process = null;
        try {
            String command = "tasklist -fi \"imagename eq \"" + processName + "\"";
            process = runtime.exec(command);
            System.out.println(command);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            String line = null;
            while((line = reader.readLine())!=null){
                String regex = "\\s{1,}";
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(line);
                if(m.find()){
                    line = line.replace(m.group() , "-");
                    if(!line.contains("PID") && !line.contains("=")){
                        line = line.substring(0,line.indexOf(" "));
                        String []lines =  line.split("-");
                        if(lines.length >= 2){
                            processIds.add(lines[1]);
                        }
                    }

                }

            }
            return processIds;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void isExistsProcess(){
        if(processIds.size() == 0){
            throw new RuntimeException("目标程序未启动");
        }
    }

    public static void main(String []args){
        ProccessOperation proccessOperation = ProccessOperation.getProccessOperation();
        List<String> processIds = proccessOperation.searchByProcessName("chrome.exe");
        for(String s : processIds){
            System.out.println(s);
        }
    }


}
