package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil {

    protected static Logger log = LoggerFactory.getLogger(ListUtil.class);

    /**
     * 获取两个集合不同
     * <p>
     * 说明: 集合里面装的是对象时, 需要重写equeals 和hashCode 方法
     *
     * @param oldList 原来的集合
     * @param newList 新集合
     * @return delete:原集合独有的(需要删除的);update:交集的数据(需要更新的);add:新集合独有的(需要新增的)
     */
    public static Map<String, List> findListDiff(List oldList, List newList) {

        //判断不能为空
        if (oldList == null || oldList.isEmpty() || newList == null || newList.isEmpty()) {

            return null;
        }
        //保存最后的数据
        Map<String, List> mapList = new HashMap();

//        //复制rps1，作为备份
//        List oldList_bak = new ArrayList<Object>(oldList);
//
//        //1、获取oldList中与newList中不同的元素
//        oldList.removeAll(newList);
//
//        //2、获取oldList和newList中相同的元素
//        oldList_bak.removeAll(oldList);
//
//        //3、获取newList中与oldList中不同的元素
//        newList.removeAll(oldList_bak);
//
//        mapList.put("delete", oldList);     // oldList中独有的数据
//        mapList.put("update", oldList_bak); // 交集的数据
//        mapList.put("add", newList);        // newList中的独有数据

        List newList_bak = new ArrayList<Object>(newList);

        newList.removeAll(oldList);
        newList_bak.removeAll(newList);
        oldList.removeAll(newList_bak);

        mapList.put("add", newList);
        mapList.put("update", newList_bak);
        mapList.put("delete", oldList);

        return mapList;
    }
}
