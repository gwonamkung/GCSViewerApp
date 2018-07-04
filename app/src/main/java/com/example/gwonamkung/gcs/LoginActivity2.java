package com.example.gwonamkung.gcs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity2 extends AppCompatActivity {
    Button button;
    EditText editText;
    Intent intent;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = findViewById(R.id.login_button);
        editText = findViewById(R.id.editText);
        intent = new Intent(this,MainActivity2.class);
        context = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("ip",editText.getText().toString());
                Toast.makeText(context,editText.getText(),Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
