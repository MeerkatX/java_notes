package com.meerkatx.netty.nettydemo;

import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.*;

import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/16
 * @Description: Promise用法
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Future
        EventExecutor executor = new DefaultEventExecutor();
        Promise promise = new DefaultPromise(executor);


        promise.addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future<Integer> future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("success");
                }else{
                    System.out.println("fail");
                }
            }
        }).addListener(new GenericFutureListener<Future<Integer>>() {

            @Override
            public void operationComplete(Future<Integer> future) throws Exception {
                System.out.println("end"+ future.get());
            }
        });

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                promise.setSuccess(123);
//                promise.setFailure(new RuntimeException());
            }
        });

        promise.sync();
    }
}
