package com.practice;

import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gaofeng on 2016-12-06.
 */

public class XMPPClient implements ChatManagerListener {
    private static final String TAG = XMPPClient.class.getSimpleName();

    public enum REGISTER_RESULT {SUCCESS, FAIL, NO_RESPONSE, CONFLICT}

    XMPPTCPConnection connection;
    volatile boolean connected;
    XMPPClientListener xmppClientListener;
    ChatManager chatManager;
    Lock mutex = new ReentrantLock();

    public XMPPClient(String host, int port) {
        XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
        config.setServiceName(host);
        config.setHost(host);
        config.setPort(port);
        config.setDebuggerEnabled(false);
        config.setCompressionEnabled(false);
        config.setSendPresence(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        connection = new XMPPTCPConnection(config.build());
    }

    public void connect(final String username, final String password, final ConnectCallback connectCallback) {
        new Thread() {
            @Override
            public void run() {
                mutex.lock();
                if (connected == false) {
                    Exception exceptionCaught = null;
                    try {
                        connection.connect();
                        connection.login(username, password);
                        chatManager = ChatManager.getInstanceFor(connection);
                        chatManager.addChatListener(XMPPClient.this);
                        connected = true;
                    } catch (Exception e) {
                        exceptionCaught=e;
                    } finally {
                        connectCallback.finished(exceptionCaught);
                    }
                } else {
                    connectCallback.finished(new Exception("已经连接"));
                }
                mutex.unlock();
            }
        }.start();
    }

    public void send(String message, String userJID) throws SmackException.NotConnectedException {
        chatManager.createChat(userJID).sendMessage(message);
    }

    public void disconnect() {
        chatManager = null;
        xmppClientListener = null;
        connection.disconnect();
        connected = false;
    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        if (xmppClientListener != null) {
            xmppClientListener.onChatCreated(chat, createdLocally);
        }
    }

    public interface XMPPClientListener {
        void onChatCreated(Chat chat, boolean createdLocally);
    }

    public interface ConnectCallback {
        void finished(Exception e);
    }
}
