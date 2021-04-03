package com.example.proto;

import java.util.HashMap;
import java.util.Map;

/**
 *  常量 --几种写法
 *
 *  @author liyang
 *  @since  2019/8/31 0:23
 */
public class ProtoConstants {

    // 方法一: 接口形式, 利用接口的属性默认用static final 修饰
    public interface ConversationType {
        int ConversationType_Private = 0;
        int ConversationType_Group = 1;
        int ConversationType_ChatRoom = 2;
        int ConversationType_Channel = 3;
        int ConversationType_Thing = 4;
    }

    // 方法二: 常量类
    public static class ChatType{
        public static final int 单聊_VALUE = 1;
        public static final String 单聊_LABEL = "单聊";

        public static final int 群聊_VALUE = 2;
        public static final String 群聊_LABEL = "群聊";

        public static Map<Integer,String> map = null;

        static{
            // 初始化map
            map = new HashMap<>();

            map.put(单聊_VALUE,单聊_LABEL);
            map.put(群聊_VALUE,群聊_LABEL);
        }
    }

    // 方法三: 枚举
    public enum MsgType{
        TEXT(1,"文字"),
        PICTURE(2,"图片"),
        VOICE(3,"语音"),
        VIDEO(4,"视屏"),
        LOCATION(5,"位置");

        private int code;
        private String value;

        MsgType(int code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    // 方法四: 直接常量属性
    public static final int QUEUE_MAX_SIZE = 5000;
}
