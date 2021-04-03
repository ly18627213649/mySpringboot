package com.example.designMode.decorator.forExample;

import org.slf4j.Logger;

public class Test {

    // 使用自己的JsonLogger
    private static final Logger logger = DecoratorLogger.JsonLoggerFactory.getLogger(Test.class);
}
