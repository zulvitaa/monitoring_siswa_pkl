package com.example.monitoringsiswapkl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.monitoringsiswapkl.LoginActivity;
import com.example.monitoringsiswapkl.R;
import com.example.monitoringsiswapkl.api.ApiInterface;
import com.example.monitoringsiswapkl.api.ApiServer;
import com.example.monitoringsiswapkl.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    String user, pass, confirpass;
    EditText username, password, confirPassword;
    AppCompatButton register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirPassword = findViewById(R.id.tlconfirPassword);

        register = findViewById(R.id.btnregis);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                confirpass = confirPassword.getText().toString();

                if (confirpass.equals(pass)) {
                    moveToRegister(user, pass);
                }else {
                    password.setError("Password tidak sama");
                }
            }
        });

        user = username.getText().toString();
        pass = password.getText().toString();
        confirpass = confirPassword.getText().toString();

        if (confirpass.equals(pass)) {
            moveToRegister(user, pass);

        }else {
            password.setError("Password tidak sama");
        }

    }

    private void moveToRegister(String user, String pass) {
        ApiInterface apiInterface = ApiServer.getClient().create(ApiInterface.class);
        Call<ResponseLogin> call = apiInterface.register(user, pass);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Berhasil Mendaftar", Toast.LENGTH_LONG).toString();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal Mendaftar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}