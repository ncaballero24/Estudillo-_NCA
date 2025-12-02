package com.example.androidauthi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// El nombre de tu clase "Login" está bien si tu archivo es "Login.java"
public class Login extends AppCompatActivity {

  private EditText etEmail, etPassword;
  private Button btnLogin;
  private TextView tvGoToRegister;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Simplemente cargamos el layout.
    setContentView(R.layout.activity_login);

    mAuth = FirebaseAuth.getInstance();

    // 1. Conectar las Vistas (Views) del XML
    etEmail = findViewById(R.id.etEmail);
    etPassword = findViewById(R.id.etPassword);
    btnLogin = findViewById(R.id.btnLogin);
    tvGoToRegister = findViewById(R.id.tvGoToRegister); // El texto para ir a registro

    // 2. Lógica del botón de Login
    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loginUser();
      }
    });

    // 3. Lógica del texto para ir a Registrarse
    // Asegúrate de tener un TextView con id "tvGoToRegister" en tu XML
    if (tvGoToRegister != null) {
      tvGoToRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // Iniciar RegisterActivity
          startActivity(new Intent(Login.this, RegisterActivity.class));
        }
      });
    }
  }

  // 4. LÓGICA CLAVE: Revisar si el usuario ya inició sesión
  @Override
  protected void onStart() {
    super.onStart();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) {
      // Si ya hay un usuario, mandarlo directo a MainActivity
      updateUI(currentUser);
    }
    // Si currentUser es null, no hace nada y el usuario ve la pantalla de login.
  }

  // 5. Método para iniciar sesión
  private void loginUser() {
    String email = etEmail.getText().toString().trim();
    String password = etPassword.getText().toString().trim();

    if (TextUtils.isEmpty(email)) {
      etEmail.setError("Correo requerido.");
      return;
    }
    if (TextUtils.isEmpty(password)) {
      etPassword.setError("Contraseña requerida.");
      return;
    }

    mAuth.signInWithEmailAndPassword(email, password)
      .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if (task.isSuccessful()) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
          } else {
            Toast.makeText(Login.this, "Error de autenticación.",
              Toast.LENGTH_SHORT).show();
            updateUI(null);
          }
        }
      });
  }

  // 6. Método para navegar a MainActivity
  private void updateUI(FirebaseUser user) {
    if (user != null) {
      Intent intent = new Intent(Login.this, MainActivity.class);
      startActivity(intent);
      finish(); // Cierra esta actividad para que no pueda volver con "atrás"
    }
  }
}
