package com.example.administrator.ccoupons.User;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserUpdateNicknameActivity extends AppCompatActivity {
    private String nickname;
    private MyApp app;
    private final static String updateUserInformationURL = GlobalConfig.base_URL + GlobalConfig.updateUserInformation_URL;

    @BindView(R.id.user_update_nickname_toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_update_nickname)
    TextView updatenick;
    @BindView(R.id.update_nickname_edit)
    EditText nicknameEdit;

    @OnClick(R.id.user_update_nickname)
    public void onclick(View view){
        nickname = nicknameEdit.getText().toString();
        update(nickname);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_nickname);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        app = (MyApp) getApplicationContext();
        nicknameEdit.setHint(app.getNickname());
    }

    private void update(String nickname) {
        String url_str = updateUserInformationURL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userID", app.getUserId());
        map.put("nickname", nickname);

        ZLoadingDialog dialog = new ZLoadingDialog(UserUpdateNicknameActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
                .setLoadingColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCanceledOnTouchOutside(false)
                .setHintText("正在修改...")
                .show();
        ConnectionManager connectionManager = new ConnectionManager(url_str, map, dialog);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {
                Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后再试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConnectionFailed() {
                Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        connectionManager.connect();
    }

    private void parseMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String result = jsonObject.getString("result");
            if (result.equals("success")) {
                app.setNickname(nickname);
                Toast.makeText(UserUpdateNicknameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if (result.equals("nickname exist")) {
                Toast.makeText(UserUpdateNicknameActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}