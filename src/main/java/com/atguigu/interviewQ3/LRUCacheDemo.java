package com.atguigu.interviewQ3;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU算法,最近最少使用缓存机制
 * 方式一: 继承LinkedHashMap
 *
 * @param <K>
 * @param <V>
 */
public class LRUCacheDemo<K,V> extends LinkedHashMap<K,V> {

    private int capacity; //缓存坑位

    public LRUCacheDemo(int capacity) {
        // accessOrder  true: 按访问顺序排序 （常用的数据在尾部，不常用的在头部（容易被淘汰））
        // false:按插入顺序排序
        super(capacity,0.75F,true);
        this.capacity = capacity;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K,V> eldest){
        // 当缓存大小超过容量时，移除最久未使用的元素（即链表的头部）
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LRUCacheDemo cache = new LRUCacheDemo(3);

        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        System.out.println(cache.keySet());

        cache.get("1");
        System.out.println(cache.keySet());//"最近一直在访问1,1排在了最后"

        // 插入新元素 4，此时容量超额
        cache.put("4", "4");
        System.out.println(cache.keySet()); // 淘汰了2
    }
}
