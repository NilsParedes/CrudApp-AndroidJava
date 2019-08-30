package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.crudapp.helpers.ConexionSQLiteOpenHelper;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        usuario = (EditText)findViewById(R.id.txt_nombre);
        password = (EditText)findViewById(R.id.txt_password);

        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Desarrollado por Nils Paredes "+ ("\uD83D\uDE34"), Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }

    public void ingresar(View view){
        if(usuario.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
            Intent i = new Intent(this, NavActivity.class);
            startActivity(i);
        }else{
            Snackbar.make(view, "Usuario y/o contraseña incocrrecto", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }

    }
}
