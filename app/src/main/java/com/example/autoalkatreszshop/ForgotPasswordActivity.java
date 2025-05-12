package com.example.autoalkatreszshop;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.os.Handler;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password); 

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);

        findViewById(R.id.resetPasswordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Kérlek add meg az email címed", Toast.LENGTH_SHORT).show();
                    return;
                }

                resetPassword(email);
            }
        });
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Jelszó visszaállító email elküldve!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); 
                        }, 2000); 
                    } else {
                        Toast.makeText(this, "Hiba történt. Próbáld újra!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
