package com.atguigu.interviewQ3;

import java.util.HashMap;
import java.util.Map;

/**
 * 03_力扣算法第一题: 两数之和
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 你可以按任意顺序返回答案
 */
public class TwoSumDemo {

    public static void main(String[] args) {
        int[] nums = new int[]{2,7,11,15};
        // 两种算法解
//        int[] result = towSum1(nums, 9);
        int[] result = towSum2(nums, 9);

    }

    /**
     * 暴力破解
     * @param nums
     * @param target
     * @return
     */
    public static int[] towSum1(int[] nums,int target){
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j<nums.length; j++){
                if (target - nums[i] == nums[j] ){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    /**
     * 利用map优化解法
     * @param nums
     * @param target
     * @return
     */
    public static int[] towSum2(int[] nums,int target){
        Map<Integer,Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int partnerNumber = target - nums[i];
            if (map.containsKey(partnerNumber)){
                return new int[]{map.get(partnerNumber),i};
            }
            map.put(nums[i],i);
        }
        return null;
    }
}
