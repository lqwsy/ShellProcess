package com.sftech.shellprocess;

/**
 * 版权：咸鱼信息科技有限公司 版权所有
 *
 * @author lqw
 * 创建日期：2020/5/30 0:16
 * 描述：启动soket的线程
 */
public class ShellThread extends Thread {

    @Override
    public void run() {
        System.out.println("----------------ShellThread run----------------");
        new ShellService(new ShellService.ShellExcListener() {
            @Override
            public String executeCommand(String command) {
                if (command.equals(Constant.THREAD_IS_START_COMMAND)) {
                    return Constant.THREAD_IS_READY;
                } else if (command.equals(Constant.MAIN_THREAD_RUNNING_CHANGE)) {
                    Constant.MAIN_THREAD_RUNNING = false;
                    return Constant.MAIN_THREAD_RUNNING_STOP;
                }
                try {
                    ShellUtils.ServiceShellCommandResult sr = ShellUtils.execCommand(command, false);
                    if (sr.result == 0) {
                        return Constant.COMMAND_EXC_SUCCESS + sr.successMsg;
                    } else {
                        return Constant.COMMAND_EXC_FAILURE + sr.errorMsg;
                    }
                } catch (Exception e) {
                    return Constant.COMMAND_EXC_ERROR + e.toString();
                }
            }
        }, Constant.SOCKET_PORT);
    }
}
