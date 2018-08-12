package com.github.pgycode.a16.tool;

public interface OnWeakTaskListener<T> {

    void before();

    T middle();

    void after(T t);
}
