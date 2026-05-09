package com.atguigu.interviewQ3;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU算法,最近最少使用缓存淘汰策略
 * 方式2: 结合 HashMap + 双向链表(Node)
 *
 * @author ly
 * @since 2026/4/30 14:31
 */
public class LRUCache2Demo {

    class Node<K, V>{ //双向链表节点
        K key;
        V value;
        Node<K,V> prev;
        Node<K,V> next;

        public Node(){
            this.prev = this.next = null;
        }

        public Node(K key, V value) {
            super();
            this.key = key;
            this.value = value;
        }
    }

    // 双向列表,新的插入头部,旧的从尾部移除
    class DoublyLinkedList<K, V>{
        Node<K,V> head;
        Node<K,V> tail;

        public DoublyLinkedList(){
            // 头尾哨兵节点
            this.head = new Node<K,V>();
            this.tail = new Node<K,V>();
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addHead(Node<K,V> node){
            node.next = this.head.next;
            node.prev = this.head;
            this.head.next.prev = node;
            this.head.next = node;
        }

        public void removeNode(Node<K,V> node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
        }

        public Node<K,V> getLast(){
            if (this.tail.prev == this.head){
                return null;
            }
            return this.tail.prev;
        }
    }

    private int cacheSize;
    private Map<Integer,Node<Integer,Integer>> map;
    private DoublyLinkedList<Integer,Integer> doublyLinkedList;

    public LRUCache2Demo(int cacheSize){
        this.cacheSize = cacheSize;
        map = new HashMap<>();
        doublyLinkedList = new DoublyLinkedList<>();
    }

    public int get(int key){
        if (!map.containsKey(key)){
            return -1;
        }

        Node<Integer,Integer> node = map.get(key);

        // 更新节点位置,将常用的节点移置链表头
        doublyLinkedList.removeNode(node);
        doublyLinkedList.addHead(node);

        return node.value;
    }

    public void put(int key, int value){
        if (map.containsKey(key)){
            //更新值
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            map.put(key,node);

            // 更新节点位置,将常用的节点移置链表头
            doublyLinkedList.removeNode(node);
            doublyLinkedList.addHead(node);
        }else{
            // 已经到达最大容量了,把旧的移除,让新的进来
            if (map.size() == cacheSize){
                Node<Integer, Integer> lastNode = doublyLinkedList.getLast();
                map.remove(lastNode.key);
                doublyLinkedList.removeNode(lastNode);
            }

            Node<Integer,Integer> newNode = new Node(key, value);
            map.put(key,newNode);
            doublyLinkedList.addHead(newNode);
        }
    }

    // 测试
    public static void main(String[] args) {
        LRUCache2Demo cache = new LRUCache2Demo(3);
        cache.put(1,1);
        cache.put(2,2);
        cache.put(3,3);
        System.out.println(cache.map.keySet());//输出[1,2,3]

        cache.put(4,4);
        System.out.println(cache.map.keySet());//输出[2,3,4]

        cache.get(3);
        cache.get(3);
        cache.get(3);
        cache.put(5,5);
        System.out.println(cache.map.keySet()); // 淘汰了2 , 输出[3,4,5]
    }
}
