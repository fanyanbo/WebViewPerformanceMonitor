package com.sxq.webviewperformancemonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtBegin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtBegin = (Button)findViewById(R.id.btBegin);
        mBtBegin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v){
        final MyWebView mWebView = new MyWebView(this); //此时并不算是UI,不能通过view.post或postDelayed更新UI，但可以通过Handler更新或销毁
        mWebView.setVisibility(View.GONE);

        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setLoadsImagesAutomatically(false);

        MyWebViewClient myWebViewClient = new MyWebViewClient();
        myWebViewClient.setTimeOut(5000);
        mWebView.setWebViewClient(myWebViewClient);

        mWebView.setAndroidObject(new AndroidObject() {
            @Override
            public void handleError(String msg) {
                Logger.d("AndroidObject,错误信息:" + msg);
            }

            @Override
            public void handleResourceTiming(String jsonStr) {
                Logger.d("AndroidObject,handleResourceTiming:" + jsonStr);
                try {
                    JSONArray arr = new JSONArray(jsonStr);
                    Logger.d("length = " + arr.length());
                    for(int i=0; i<arr.length(); i++){
                        JSONObject jsonObj = new JSONObject(arr.get(i).toString());
                        String name = jsonObj.getString("name");
                        String entryType = jsonObj.getString("entryType");
                        String initiatorType = jsonObj.getString("initiatorType");
                        double startTime = Double.parseDouble(jsonObj.getString("startTime"));
                        double fetchStart = Double.parseDouble(jsonObj.getString("fetchStart"));
                        double domainLookupStart = Double.parseDouble(jsonObj.getString("domainLookupStart"));
                        double domainLookupEnd = Double.parseDouble(jsonObj.getString("domainLookupEnd"));
                        double connectStart = Double.parseDouble(jsonObj.getString("connectStart"));
                        double connectEnd = Double.parseDouble(jsonObj.getString("connectEnd"));
                        double requestStart = Double.parseDouble(jsonObj.getString("requestStart"));
                        double responseStart = Double.parseDouble(jsonObj.getString("responseStart"));
                        double responseEnd = Double.parseDouble(jsonObj.getString("responseEnd"));
                        Logger.d(i + "==>         name==>" + name);
                        Logger.d(i + "==>    entryType==>" + entryType);
                        Logger.d(i + "==>initiatorType==>" + initiatorType);
                        Logger.d(i + "==>    startTime==>" + startTime);
                        Logger.d(i + "==>  responseEnd==>" + responseEnd);
                        Logger.d("资源请求耗时==>" + (responseEnd-startTime));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void handleNavigationTiming(String jsonStr) {
                Logger.d("AndroidObject,handleNavigationTiming:" + jsonStr);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    long navigationStart = Long.parseLong(jsonObj.getString("navigationStart"));
                    long fetchStart = Long.parseLong(jsonObj.getString("fetchStart"));
                    long domainLookupStart = Long.parseLong(jsonObj.getString("domainLookupStart"));
                    long domainLookupEnd = Long.parseLong(jsonObj.getString("domainLookupEnd"));
                    long connectStart = Long.parseLong(jsonObj.getString("connectStart"));
                    long connectEnd = Long.parseLong(jsonObj.getString("connectEnd"));
                    long requestStart = Long.parseLong(jsonObj.getString("requestStart"));
                    long responseStart = Long.parseLong(jsonObj.getString("responseStart"));
                    long responseEnd = Long.parseLong(jsonObj.getString("responseEnd"));
                    long domLoading = Long.parseLong(jsonObj.getString("domLoading"));
                    long domInteractive = Long.parseLong(jsonObj.getString("domInteractive"));
                    long domContentLoadedEventStart = Long.parseLong(jsonObj.getString("domContentLoadedEventStart"));
                    long domContentLoadedEventEnd = Long.parseLong(jsonObj.getString("domContentLoadedEventEnd"));
                    long domComplete = Long.parseLong(jsonObj.getString("domComplete"));
                    long loadEventStart = Long.parseLong(jsonObj.getString("loadEventStart"));
                    long loadEventEnd = Long.parseLong(jsonObj.getString("loadEventEnd"));
                    Logger.d("           navigationStart==>" + navigationStart);
                    Logger.d("                fetchStart==>" + fetchStart);
                    Logger.d("         domainLookupStart==>" + domainLookupStart);
                    Logger.d("           domainLookupEnd==>" + domainLookupEnd);
                    Logger.d("              connectStart==>" + connectStart);
                    Logger.d("                connectEnd==>" + connectEnd);
                    Logger.d("              requestStart==>" + requestStart);
                    Logger.d("             responseStart==>" + responseStart);
                    Logger.d("               responseEnd==>" + responseEnd);
                    Logger.d("                domLoading==>" + domLoading);
                    Logger.d("            domInteractive==>" + domInteractive);
                    Logger.d("domContentLoadedEventStart==>" + domContentLoadedEventStart);
                    Logger.d("  domContentLoadedEventEnd==>" + domContentLoadedEventEnd);
                    Logger.d("               domComplete==>" + domComplete);
                    Logger.d("            loadEventStart==>" + loadEventStart);
                    Logger.d("              loadEventEnd==>" + loadEventEnd);
                    Logger.d("DNS寻址耗时==>" + (domainLookupEnd-domainLookupStart));
                    Logger.d("TCP连接耗时==>" + (connectEnd-connectStart));
                    Logger.d("首包时间==>" + (responseStart-navigationStart));
                    Logger.d("request请求耗时==>" + (responseEnd-requestStart));
                    Logger.d("DOM解析耗时==>" + (domComplete-domLoading));
                    Logger.d("页面加载耗时==>" + (loadEventEnd-fetchStart));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
//        mWebView.loadUrl("http://www.baidu.com");
        mWebView.loadUrl("http://beta.webapp.skysrt.com/appstore/webxtest/test7/test.html");
    }
}
