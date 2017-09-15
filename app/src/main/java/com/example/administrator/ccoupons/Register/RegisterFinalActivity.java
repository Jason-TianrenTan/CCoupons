package com.example.administrator.ccoupons.Register;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.CustomEditText.ClearableEditText;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterFinalActivity extends AppCompatActivity {


    private String[] GenderChars = {"男", "女"};
    private LoginInformationManager loginInformationManager;
    private final static String requestURL = GlobalConfig.base_URL + GlobalConfig.register_URL;
    private Button button_next;
    private RadioGroup radioGroup;
    private int gender;
    private ClearableEditText nickname_edittext;
    private String phoneString, password;


    /**
     * parse response string from server
     * @param response
     */
    private void parseMessage(String response) {
        System.out.println("response = " + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String errno = jsonObject.getString("errno");
            if (errno.equals("1")) {
                //注册失败
                Toast.makeText(getApplicationContext(), "账号已经存在", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterFinalActivity.this, RegisterActivity.class));
            } else {
                loginInformationManager.setAutoLogin(true).setUsername(phoneString).setPassword(password);
                Intent intent = new Intent(RegisterFinalActivity.this, MainPageActivity.class);
                intent.putExtra("username", phoneString).putExtra("password", password);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);

        Toolbar toolbar = (Toolbar) findViewById(R.id.register_final_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nickname_edittext = (ClearableEditText) findViewById(R.id.register_final_username);

        loginInformationManager = new LoginInformationManager(this);
        phoneString = getIntent().getStringExtra("phone_number");
        password = getIntent().getStringExtra("password");
        gender = Gender.MALE;
        System.out.println(phoneString + "," + password + "," + gender);
        button_next = (Button) findViewById(R.id.register_final_button_next);
        radioGroup = (RadioGroup) findViewById(R.id.register_final_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                if (buttonId == R.id.radio_button_male)
                    gender = Gender.MALE;
                else gender = Gender.FEMALE;
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = nickname_edittext.getText().toString();
                Register(nickname);
            }
        });
    }


    /**
     * Login
     */
    private void Login() {
        Intent intent = new Intent(RegisterFinalActivity.this, MainPageActivity.class);
        MyApp app = (MyApp) getApplicationContext();
        app.setNickname(nickname_edittext.getText().toString());
        app.setPhoneNumber(phoneString);
        app.setGender(gender);
        startActivity(intent);
    }


    /**
     * Register
     * @param nickname user's nickname
     */
    private void Register(String nickname) {
        String url_str = requestURL;
        String md5pass = null;
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            md5pass = new PasswordEncoder().EncodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("username", phoneString);
        map.put("password", md5pass);
        map.put("nickname", nickname);
        map.put("gender", GenderChars[gender]);

        ZLoadingDialog dialog = new ZLoadingDialog(RegisterFinalActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
                .setLoadingColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCanceledOnTouchOutside(false)
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


}
