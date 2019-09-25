package com.practise.concurrent.alarmAgent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 告警代理
 * @author: realz
 * @package: com.practise.concurrent.alarmAgent
 * @date: 2019-09-25
 * @email: zlp951116@hotmail.com
 */
public class AlarmAgent {
    private static Logger log = LoggerFactory.getLogger(AlarmAgent.class);
    //保存该类唯一实例
    private final static AlarmAgent INSTANCE = new AlarmAgent();
    //是否连接上告警服务器
    private boolean connectToServer = false;
    //心跳线程
    private final HeartBeatThread heartBeatThread = new HeartBeatThread();

    private AlarmAgent() {

    }

    public static AlarmAgent getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.connectToServer();
        heartBeatThread.setDaemon(true);
        heartBeatThread.start();
    }

    private void connectToServer() {
        //创建并启动网络连接线程
        new Thread(this::doConnect).start();
    }

    private void doConnect() {
        //模拟实际操作耗时
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
        }
        synchronized (this) {
            connectToServer = true;
            notify();
        }
    }

    public void sendAlarm(String message) throws InterruptedException {
        synchronized (this) {
            //使当前线程等待，直到告警代理与告警服务器的连接建立完毕或恢复
            while (!connectToServer) {
                log.info("Alarm agent was not connected to server");
                wait();
            }
            doSendAlarm(message);
        }
    }

    private void doSendAlarm(String message) {
        //....
        log.info("send message successfully : {}", message);
    }

    class HeartBeatThread extends Thread {
        @Override
        public void run() {
            try {
                //留一定的时间给网络连接线程与告警服务器建立连接
                Thread.sleep(1000);
                while (true) {
                    if (checkConnection()) {
                        connectToServer = true;
                    } else {
                        connectToServer = false;
                        log.info("agent was disconnected from server.");
                        //检测连接中断，重新建立连接
                        connectToServer();
                    }
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
            }

        }

        //检测与告警服务器连接状态
        private boolean checkConnection() {
            boolean isConnect = true;
            final Random random = new Random();
            int rand = random.nextInt(1000);
            if (rand < 500) {
                isConnect = false;
            }
            return isConnect;
        }
    }
}
