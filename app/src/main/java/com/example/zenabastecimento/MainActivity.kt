package com.example.zenabastecimento

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val valorMediaTotal: TextView = findViewById<TextView>(R.id.txtViewMediaTotalValor);
        val valorMediaRelativa: TextView = findViewById<TextView>(R.id.txtViewMediaRelativaValor);
        val valorKm: EditText = findViewById<EditText>(R.id.txtNumberKm);
        val valorLitros: EditText = findViewById<EditText>(R.id.txtNumberLitros);
        val btnAdd: Button = findViewById<Button>(R.id.btn_add);
        var km: Double = 0.0;
        var litros: Double = 0.0;
        var result: Double = 0.0;

        val mediaTotalPath = "media_total.txt";
        val mediaTotalFile = File(mediaTotalPath);

        if(mediaTotalFile.length() > 0) {
            val lines: List<String> = mediaTotalFile.readLines();
            var subtotal: Double = 0.0;
            lines.forEach() {
                line-> subtotal += line.toDouble();
            }

            valorMediaTotal.text = (subtotal/lines.size).toString();

        } else {
            valorMediaTotal.text = "0";
        }

        valorKm.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                km = valorKm.text.toString().toDouble();
            }
        })

        valorLitros.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                var litrosStr: String?;
                litrosStr = valorLitros.text.toString()
                if(litrosStr.isNotEmpty()) {
                    litros = litrosStr.toDouble();
                }

                result = km / litros;
                valorMediaRelativa.text = result.toString();
            }
        })

        btnAdd.setOnClickListener() {
            //mediaTotalFile.writeText("Teste!");
        }
    }
}