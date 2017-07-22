package com.example.administrator.ccoupons.Main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.LoginThread;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterActivity;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.mob.MobApplication;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class WelcomeActivity extends AppCompatActivity {
    private static String url = DataHolder.base_URL + DataHolder.login_URL;
    private LoginInformationManager loginInformationManager;
    private boolean auto_login;
    private String username;
    private String password;
    private LoginThread thread;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    parseMessage(thread.getResponse());
                    break;
            }
        }
    };
    Button login;
    Button register;

    //处理返回回来的json
    private void parseMessage(String response) {
        if (response.indexOf("result") != -1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userId = jsonObject.getString("userid");
                MyApp app = (MyApp) getApplicationContext();
                app.setUserId(userId);
                System.out.println("Response = " + response);
                Toast.makeText(getApplicationContext(), "登录成功\n账号:" + username +
                        "\n密码:" + password, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WelcomeActivity.this, MainPageActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
                System.out.println("Login success");
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Login failed");
            Toast.makeText(getApplicationContext(), "用户名/密码错误", Toast.LENGTH_SHORT).show();
            startButtonAnimation();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //MobSDK.init(this, "");

        login = (Button) findViewById(R.id.welcome_login_button);
        register = (Button) findViewById(R.id.welcome_register_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, MainPageActivity.class));//为了能直接登录而修改了
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
                finish();
            }
        });
        loginInformationManager = new LoginInformationManager(this);
        auto_login = loginInformationManager.getAutoLogin();
        if (auto_login == false) {
            /*
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);*/
            startButtonAnimation();
        }
        if (auto_login == true) {
            username = loginInformationManager.getUsername();
            password = loginInformationManager.getPassword();
            requestLogin(url, username, password);
        }



    }

    //登录
    private boolean requestLogin(String url, String username, String password) {
        thread = new LoginThread(url, username, password, handler, getApplicationContext());
        thread.start();
        //TODO 播放动画
        return false;
    }

    private void startButtonAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        login.startAnimation(animation);
        register.startAnimation(animation);
    }




}

