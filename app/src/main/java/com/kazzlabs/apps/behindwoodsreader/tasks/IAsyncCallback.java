package com.kazzlabs.apps.behindwoodsreader.tasks;

/**
 * Created by kasturip on 8/9/14.
 */
public interface IAsyncCallback<T> {
    public void onComplete(T result);
}
