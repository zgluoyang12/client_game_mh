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



}
