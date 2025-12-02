package com.example.androidauthi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

  private Button btnLogout;
  private TextView tvWelcome;
  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Quité el código de EdgeToEdge para simplificar el ejemplo
    setContentView(R.layout.activity_main);

    mAuth = FirebaseAuth.getInstance();
    btnLogout = findViewById(R.id.btnLogout);
    tvWelcome = findViewById(R.id.tvWelcome);

    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
      // Si hay usuario, muestra su correo
      tvWelcome.setText("¡Bienvenido!\n" + user.getEmail());
    } else {
      // Si no hay usuario (raro, pero por seguridad), lo mandamos a Login
      goToLogin();
    }


    btnLogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mAuth.signOut();
        goToLogin();
      }
    });
  }

  private void goToLogin() {
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(intent);
    finish(); // Cierra MainActivity
  }
}
