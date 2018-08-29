package com.sxq.webviewperformancemonitor;

import android.webkit.JavascriptInterface;


/**
 * Created by shixiaoqiangsx on 2017/4/11.
 */

public abstract class AndroidObject {


    private volatile boolean mIsDataReturn = false ;
    private long startTime ;
    private long endTime ;

    /**
     *用于收集ResourceTiming信息
     *
     * @param jsonStr
     */
    @JavascriptInterface
    public void sendResourceTiming(String jsonStr) {
        mIsDataReturn = true ;
        endTime = System.currentTimeMillis();
        Logger.d("js成功执行时间：" + (endTime-startTime));
        handleResourceTiming(jsonStr);
    }

    /**
     *用于收集NavigationTiming信息
     *
     * @param jsonStr
     */
    @JavascriptInterface
    public void sendNavigationTiming(String jsonStr) {
        mIsDataReturn = true ;
        endTime = System.currentTimeMillis();
        Logger.d("js成功执行时间：" + (endTime-startTime));
        handleNavigationTiming(jsonStr);
    }


    /**
     * 用于收集js的执行错误
     * @param msg
     */
    @JavascriptInterface
    public void sendError(String msg) {
        handleError(msg);
    }


    /**
     * 处理错误信息，可能会被回调多次
     * @param msg
     */
    public abstract void handleError(String msg) ;

    /**
     *
     * @param jsonStr
     */
    public abstract void handleResourceTiming(String jsonStr);
    public abstract void handleNavigationTiming(String jsonStr);

    public boolean isDataReturn() {
        return mIsDataReturn;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
