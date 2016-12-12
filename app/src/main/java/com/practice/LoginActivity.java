package com.practice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEt;
    EditText passwordEt;
    EditText ipEt;
    EditText portEt;
    Button loginBtn;
    MyApplication myApplication;
    XMPPClient xmppClient;
    TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEt = (EditText) findViewById(R.id.usernameEt);
        passwordEt = (EditText) findViewById(R.id.passwordEt);
        myApplication = (MyApplication) getApplication();
        ipEt = (EditText) findViewById(R.id.ipEt);
        portEt = (EditText) findViewById(R.id.portEt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        errorTv = (TextView) findViewById(R.id.errorTv);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xmppClient = new XMPPClient(ipEt.getText().toString(), Integer.parseInt(portEt.getText().toString()));
                xmppClient.connect(usernameEt.getText().toString(), passwordEt.getText().toString(), new XMPPClient.ConnectCallback() {
                    @Override
                    public void finished(Exception e) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        if (e != null) {
                            final String exceptionMessage=e.getMessage();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    errorTv.setText(exceptionMessage);
                                }
                            });
                        }else{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    LoginActivity.this.finish();
                                    myApplication.setXmppClient(xmppClient);
                                    LoginActivity.this.startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }
                            });
                        }
                    }
                });
            }
        });
    }

}
