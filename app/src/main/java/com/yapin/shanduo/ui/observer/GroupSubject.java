package com.yapin.shanduo.ui.observer;

/**
 * 作者：L on 2018/6/16 0016 14:53
 */
public interface GroupSubject {

    /**
     * 注册观察者
     *
     * @param observer
     */
    public void addGroupObserver(GroupObserver observer);

    /**
     * 移除观察者
     *
     * @param observer
     */
    public void removeGroupObserver(GroupObserver observer);



}
