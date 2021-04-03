package com.example.designMode.Observer;

import java.util.Observable;

/**
 * 设计模式:观察者模式  现在模拟: 栈长发布文章, 读者订阅栈长有更新就读文章
 *
 * 观察目标类: 栈长
 * @author Honey
 * @since 2021/3/31 23:46
 */
public class JavaStackObservable extends Observable {
    private String article;   // 文章

    /**
     * 目标类发布文章
     */
    public void publish(String article){
        this.article = article;

        // 改变状态
        this.setChanged();

        // 通知所有观察者
        this.notifyObservers();
    }




    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
