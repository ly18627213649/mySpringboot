package com.example.xhlang.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象线程任务类
 */
public abstract class AbstractTask extends Thread implements ITask {

    protected static Logger log = LoggerFactory.getLogger(AbstractTask.class);

    public AbstractTask() {
    }

    public void run() {
        this.doRun();
    }

    public abstract void doRun();
}
