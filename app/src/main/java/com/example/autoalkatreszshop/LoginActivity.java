package com.example.autoalkatreszshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email, password;
    private Button loginBtn;
    private TextView registerRedirect;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerRedirect = findViewById(R.id.registerRedirect);
        mAuth = FirebaseAuth.getInstance();
        TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        TextInputEditText passwordEditText = findViewById(R.id.password);

        passwordLayout.setEndIconOnClickListener(v -> {
            if (passwordEditText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        loginBtn.setOnClickListener(view -> {
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Tölts ki minden mezőt!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Sikertelen belépés!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        registerRedirect.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

      Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
      Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

      email.startAnimation(slideUp);
      password.startAnimation(slideUp);
      loginBtn.startAnimation(fadeIn);
      registerRedirect.startAnimation(fadeIn);
    }

    public void onForgotPasswordClick(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}

