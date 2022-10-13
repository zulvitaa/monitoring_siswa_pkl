package com.example.monitoringsiswapkl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.monitoringsiswapkl.activity.MainActivity;
import com.example.monitoringsiswapkl.activity.RegisterActivity;
import com.example.monitoringsiswapkl.api.ApiInterface;
import com.example.monitoringsiswapkl.api.ApiServer;
import com.example.monitoringsiswapkl.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    String  textUser, textPass;
    EditText user, pass;
    AppCompatButton  btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    user = findViewById(R.id.username);
    pass = findViewById(R.id.password);
    btnLogin = findViewById(R.id.login);
    btnRegister = findViewById(R.id.register);

    btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            textUser = user.getText().toString();
            textPass = pass.getText().toString();

            if (textUser.equals("")) {
                user.setError("Mohon Diisi");
            }else if (textPass.equals("")) {
                pass.setError("Mohon Diisi");
            }else {
                moveToLogin(textUser, textPass);
            }
        }
    });
}

private void moveToLogin(String textUser, String textPass){
    ApiInterface apiInterface = ApiServer.getClient().create(ApiInterface.class);
    Call<ResponseLogin> responseLoginCall = apiInterface.login(textUser, textPass);
    responseLoginCall.enqueue(new Callback<ResponseLogin>() {
        @Override
        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
            if(response.isSuccessful()){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }

        @Override
        public void onFailure(Call<ResponseLogin> call, Throwable t) {

        }
    });
}

}