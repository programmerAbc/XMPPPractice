package com.practice;

import android.app.Application;

/**
 * Created by gaofeng on 2016-12-07.
 */

public class MyApplication extends Application {
    XMPPClient xmppClient=null;

    public XMPPClient getXmppClient() {
        return xmppClient;
    }

    public void setXmppClient(XMPPClient xmppClient) {
        this.xmppClient = xmppClient;
    }
}
