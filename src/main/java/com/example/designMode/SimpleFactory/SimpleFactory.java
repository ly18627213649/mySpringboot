package com.example.designMode.SimpleFactory;


/**
 * 简单工厂模式
 *
 * @author iyang
 * @since  2019/11/15 9:18
 */
public class SimpleFactory {
    // 公司名称
    private static String bussionName;
    // 包名称
    private static String packageName;
    static {
        // 这里的参数可以配到properties文件, 用java自带的Properties.load(SimpleFactory.class.getClassLoader().getResourceAsStream("xxx.properties");)
        bussionName = "Ali";
        packageName = "com.liyang.designMode.SimpleFactory";
    }

    /**
     * 实例化具体实现对象
     */
    public IService createSMS(){

        IService iService = new AliSMSImpl();

        return iService;

    }

    // 思考: 如果需要动态的创建具体业务对象, 可以通过反射的方式, 先读取配置文件中配置的具体业务类, 通过反射的方式实例化对象
    public static IService getSMS() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        String className = packageName.concat(".").concat(bussionName + "SMSImpl");

        IService SMS = (IService)Class.forName(className).newInstance();

        return SMS;
    }
}
