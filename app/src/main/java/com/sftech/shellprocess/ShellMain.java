package com.sftech.shellprocess;

/**
 * 版权：咸鱼信息科技有限公司 版权所有
 *
 * @author lqw
 * 创建日期：2020/5/29 23:47
 * 描述：通过adb 把程序运行在app process中，提高程序权限
 * 通过adb复制到手机：adb push classes.dex /data/local/tmp
 * 通过adb安装命令：app_process -Djava.class.path=/data/local/tmp/classes.dex /system/bin com.sftech.shellprocess.ShellMain &
 */
public class ShellMain {

    public static void main(String[] args) {
        System.out.println("Main Thread Start");
        new ShellThread().start();
        while (Constant.MAIN_THREAD_RUNNING) ;
        System.out.println("Main Thread Stop");
    }

}
