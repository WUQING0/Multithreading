package com.company.juc.c_000;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest {

    private static int count = 10;
    private Object o = new Object();

    private void m() {
        synchronized (o) {      //任何线程要执行下面的代码，必须先拿到o的锁
            //hospot中的实现原理，是在一个对象(64位)上，拿出2位来用来标记是否锁定(markword)
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }

    private synchronized static void m1() {      //加了static之后没有this的概念，所以这里等同于synchronized(T.class)
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);

    }


    /*
    面试题：模拟银行账户
    对业务写方法枷锁
    对业务读方法不加锁
    这样行不行？

    容易产生脏读问题
     */
    public static class Account {
        String name;
        double balance;

        public synchronized void set(String name, double balance) {
            this.name = name;
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.balance = balance;
        }

        public /*synchronized*/ double getBalance(String name) {
            return this.balance;
        }

    }

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(() -> a.set("zhangsan", 100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangsan"));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a.getBalance("zhangsan"));
    }


    public static class T {
        synchronized void m() {
            System.out.println("m start");
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m end");
        }

        public static void main(String[] args) {
            new TT().m();
        }
    }
    static class TT extends T {

        @Override
        synchronized void m() {
            System.out.println("child m start");
            super.m();
            System.out.println("child m end");
        }
    }

}
