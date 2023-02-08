package com.example.visitsapp.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.visitsapp.R;
import com.example.visitsapp.business.impl.Business;
import com.example.visitsapp.delegate.ResponseCallBack;
import com.example.visitsapp.model.request.ForgotPasswordRequest;
import com.example.visitsapp.model.request.LoginRequest;
import com.example.visitsapp.model.responce.ForgotPasswordResponce;
import com.example.visitsapp.model.responce.LoginResponce;
import com.example.visitsapp.ui.MainActivity;
import com.example.visitsapp.utils.SharedPrefrences;
import com.example.visitsapp.utils.alert.AlertUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private TextView tvGoBack;
    private RelativeLayout getOTP;
    private EditText edEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edEmail = findViewById(R.id.email);

        getOTP = findViewById(R.id.getotp);
        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPassword();
            }
        });

        tvGoBack = findViewById(R.id.knowpassword);
        tvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);

            }
        });
    }

    private void getPassword() {

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

        ForgotPasswordRequest loginRequest = new ForgotPasswordRequest();
        loginRequest.setEmail(edEmail.getText().toString());


        Business serviceImp = new Business() ;
        serviceImp.forgotPassword(loginRequest, new ResponseCallBack<ArrayList<ForgotPasswordResponce>>() {
            @Override
            public void onSuccess(ArrayList<ForgotPasswordResponce> body) {

                if (dialog != null) {
                    dialog.dismiss();
                }
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(ForgotPassword.this, message);


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