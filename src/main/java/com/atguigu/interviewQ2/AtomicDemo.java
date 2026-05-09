package com.atguigu.interviewQ2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子类操作
 */
public class AtomicDemo {
    public static void main(String[] args) {

    }
    public void AtomicReferenceDemo(){
        // 原子类操作，CAS原理代码
        AtomicInteger integer = new AtomicInteger(0);
        integer.incrementAndGet();
        integer.getAndIncrement();
        integer.get();

        // 自定义原子操作
        AtomicReference<Integer> referenceInteger = new AtomicReference(100);

        // ABA问题解决方案，带版本号
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100,1);
        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            // 比较并修改，版本号+1
            boolean resule = stampedReference.compareAndSet(100, 2026, stamp, stamp + 1);
        },"111").start();
    }


}
