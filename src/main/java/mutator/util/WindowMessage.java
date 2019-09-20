package mutator.util;

public class WindowMessage {

    // 移 动 鼠 标
    public final static int WM_MOUSEMOVE = 0x0200;
    // 按下鼠 标 左 键
    public final static int WM_LBUTTONDOWN = 0x0201;
    // 释 放鼠 标 左 键
    public final static int WM_LBUTTONUP = 0x0202;
    // 双 击 鼠 标 左 键
    public final static int WM_LBUTTONDBLCLK = 0x0203;
    // 按下鼠 标 右 键
    public final static int WM_RBUTTONDOWN = 0x0204;
    // 释 放鼠 标 右 键
    public final static int WM_RBUTTONUP = 0x0205;
    // 双 击 鼠 标 右 键
    public final static int WM_RBUTTONDBLCLK = 0x0206;
    // 按下鼠 标 中 键
    public final static int WM_MBUTTONDOWN = 0x0207;
    // 释 放鼠 标 中 键
    public final static int WM_MBUTTONUP = 0x0208;
    //双 击 鼠 标 中 键
    public final static int WM_MBUTTONDBLCLK = 0x0209;

    public static int MakeLParam(int x , int y){

        return  0;
    }
}
