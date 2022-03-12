package com.company.juc.c_000;

public class Sleep_Yield_join {

    static void testSleep() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);          //睡眠一段时间，让给其他线程执行(上锁),sleep睡完也回到就绪状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static void testYield() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("--------B" + i);
                if (i % 10 == 0) Thread.yield();        //非常谦让的退出一下,此时其他线程有机会执行yield所在的执行(释放锁,返回就绪状态)
            }
        }).start();
    }

    static void testJoin() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();                               //当运行t2线程时,调用t1时会先执行t1线程,当执行完毕之后再返过来继续执行t2
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}
