package com.example.visitsapp.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.configuration.ConfigurationResponse;
import com.example.visitsapp.model.request.LoginRequest;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private RelativeLayout rlLogin;
    private EditText edPassword, edEmail;
    private TextView tvForgotPass;
    private LinearLayout llpass, llpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.email);
        edPassword = findViewById(R.id.password);

        llpassword = findViewById(R.id.passll);
        llpass = findViewById(R.id.pass);
        llpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llpassword.setVisibility(View.VISIBLE);
                llpass.setVisibility(View.GONE);
            }
        });

        tvForgotPass = findViewById(R.id.forgot);
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefrences.getInstance().setIsLogin(true);
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        rlLogin = findViewById(R.id.login);
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });
    }

    private void loginRequest() {

        if(edPassword.getText().toString().isEmpty()){
            edPassword.setError("Please enter password");
            return;
        }
        if(edEmail.getText().toString().isEmpty()){
            edEmail.setError("Please enter password");
            return;
        }
        if(!isEmailValid(edEmail.getText().toString())){
            edEmail.setError("Please enter proper email");
            return;
        }

        final AlertDialog dialog = AlertUtils.showLoader(this);

        if (dialog != null) {
            dialog.show();
        }

       LoginRequest loginRequest = new LoginRequest();
       loginRequest.setEmail(edEmail.getText().toString());
       loginRequest.setPassword(edPassword.getText().toString());


        Business serviceImp = new Business() ;
        serviceImp.login(loginRequest, new ResponseCallBack<LoginResponce>() {
            @Override
            public void onSuccess(LoginResponce body) {



                SharedPrefrences.getInstance().setIsLogin(true);
                SharedPrefrences.getInstance().setAccessToken(body.token);
                SharedPrefrences.getInstance().setloginResponse(body);
//                getConfig();
                if (dialog != null) {
                    dialog.dismiss();
                }

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(Login.this, message);


            }
        });
    }

    private void getConfig() {



        Business serviceImp = new Business() ;
        serviceImp.getConfiguration( new ResponseCallBack<ConfigurationResponse>() {
            @Override
            public void onSuccess(ConfigurationResponse body) {

                SharedPrefrences.getInstance().setConfig(body);
                if(body.user_image != null && !body.user_image.equals("")){

                    SharedPrefrences.getInstance().setProfileImage(body.user_image);

                }


            }

            @Override
            public void onFailure(String message) {


                AlertUtils.showAlert(Login.this, message);


            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}