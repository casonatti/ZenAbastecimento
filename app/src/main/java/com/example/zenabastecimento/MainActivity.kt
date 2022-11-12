package com.example.zenabastecimento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val valorMediaTotal: TextView = findViewById<TextView>(R.id.txtViewMediaTotalValor);
        val valorMediaRelativa: TextView = findViewById<TextView>(R.id.txtViewMediaRelativaValor);
        val valorKm: EditText = findViewById<EditText>(R.id.txtNumberKm);
        val valorLitros: EditText = findViewById<EditText>(R.id.txtNumberLitros);
        val btnAdd: Button = findViewById<Button>(R.id.btn_add);

        btnAdd.setOnClickListener() {
            val km = valorKm.text.toString().toDouble()
            val litros = valorLitros.text.toString().toDouble();
            val result = km / litros;
            valorMediaRelativa.text = result.toString();
        }
    }
}