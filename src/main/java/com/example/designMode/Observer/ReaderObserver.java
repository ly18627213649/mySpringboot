package com.example.designMode.Observer;

import java.util.Observable;
import java.util.Observer;

/**
 *  观察者: 读者粉丝
 * @author Honey
 * @since 2021/3/31 23:53
 */
public class ReaderObserver implements Observer {
    // 姓名
    private String name;
    // 当前文章
    private String article;

    public ReaderObserver(String name){
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        // 更新文章
        updateArticle(o);
    }

    private void updateArticle(Observable observable) {
        JavaStackObservable javaStackObservable = (JavaStackObservable) observable;
        this.article = javaStackObservable.getArticle();
        System.out.printf("我是读者: %s, 文章更新内容为: %s\n",this.name,this.article);
    }
}
