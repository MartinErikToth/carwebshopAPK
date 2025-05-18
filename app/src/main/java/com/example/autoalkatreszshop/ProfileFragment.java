package com.example.autoalkatreszshop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileFragment extends Fragment {

  private FirebaseAuth mAuth;
  private TextView emailValue;
  private EditText newEmailInput, newPasswordInput;
  private Button btnEditEmail, btnEditPassword, btnSaveChanges, btnLogout;

  public ProfileFragment() {}

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

    mAuth = FirebaseAuth.getInstance();
    emailValue = rootView.findViewById(R.id.email_value);
    newPasswordInput = rootView.findViewById(R.id.new_password_input);
    btnLogout = rootView.findViewById(R.id.btn_logout);

    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
      emailValue.setText(user.getEmail());
    }
    btnEditPassword = rootView.findViewById(R.id.btn_edit_password);
    btnSaveChanges = rootView.findViewById(R.id.btn_save_changes);

    btnEditPassword.setOnClickListener(v -> editPassword());
    btnSaveChanges.setOnClickListener(v -> saveChanges());

    btnLogout.setOnClickListener(v -> logoutUser());

    return rootView;
  }
  private void editPassword() {
    String newPassword = newPasswordInput.getText().toString();
    if (!TextUtils.isEmpty(newPassword) && newPassword.length() >= 6) {
      FirebaseUser user = mAuth.getCurrentUser();
      if (user != null) {
        user.updatePassword(newPassword)
          .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
              Toast.makeText(getContext(), "Jelszó módosítva", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(getContext(), "Jelszó módosítása sikertelen", Toast.LENGTH_SHORT).show();
            }
          });
      }
    } else {
      Toast.makeText(getContext(), "A jelszónak legalább 6 karakter hosszúnak kell lennie!", Toast.LENGTH_SHORT).show();
    }
  }

  private void saveChanges() {
    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
      String newEmail = newEmailInput.getText().toString();
      String newPassword = newPasswordInput.getText().toString();

      if (!TextUtils.isEmpty(newEmail)) {
        user.updateEmail(newEmail)
          .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
              emailValue.setText(newEmail);
              Toast.makeText(getContext(), "E-mail frissítve", Toast.LENGTH_SHORT).show();
            }
          });
      }

      if (!TextUtils.isEmpty(newPassword) && newPassword.length() >= 6) {
        user.updatePassword(newPassword)
          .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
              Toast.makeText(getContext(), "Jelszó frissítve", Toast.LENGTH_SHORT).show();
            }
          });
      }
    }
  }

  private void logoutUser() {
    mAuth.signOut();
    Toast.makeText(getContext(), "Sikeres kijelentkezés", Toast.LENGTH_SHORT).show();
    startActivity(new Intent(getActivity(), LoginActivity.class));
    requireActivity().finish();
  }
}
