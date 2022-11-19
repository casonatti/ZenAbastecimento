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
import java.io.*

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
        var numElementos : Int = 0;

        val filePath: String = "MyFileStorage";
        var myExternalFile : File ?= null;

        //calcula a media total atual
        try {
            val fileName : String = "media_total.txt";
            myExternalFile = File(getExternalFilesDir(filePath), fileName);

//            myExternalFile.delete();
//            myExternalFile.createNewFile();

            if(fileName.toString()!=null && fileName.toString().trim()!=""){
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val listaMediaTotal : MutableList<String> = mutableListOf();
                var text: String? = null
                var calc : Double = 0.0;
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    listaMediaTotal.add(text.toString());
                    numElementos++;
                }
                fileInputStream.close()
                for(item in listaMediaTotal) {
                    println(item);
                    calc += item.toDouble();
                }
                if(numElementos == 0)
                    valorMediaTotal.text = numElementos.toString();
                else
                    valorMediaTotal.text = (calc/numElementos).toString();
                Toast.makeText(applicationContext, numElementos.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (e: IOException) {
            e.printStackTrace();
        }

        valorKm.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                var kmStr: String?;
                kmStr = valorKm.text.toString()
                if(kmStr.isNotEmpty()) {
                    km = kmStr.toDouble();
                }
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
            myExternalFile = File(getExternalFilesDir(filePath), "media_total.txt");
            try {
                Toast.makeText(applicationContext,"Saving data...",Toast.LENGTH_SHORT).show();
                val fileOutputStream = FileOutputStream(myExternalFile, true);
//                fileOutputStream.write(valorMediaRelativa.text.toString().toByteArray());
                fileOutputStream.bufferedWriter().use { out ->
                    out.write(valorMediaRelativa.text.toString() + "\n");
                }
                fileOutputStream.close();

                //atualiza o valor da media total
                val mediaRelativaAtual : Double = valorMediaRelativa.text.toString().toDouble();
                var mediaTotalAtual : Double = valorMediaTotal.text.toString().toDouble();
                mediaTotalAtual *= numElementos;
                numElementos++;
                valorMediaTotal.text = ((mediaTotalAtual + mediaRelativaAtual)/numElementos).toString()
            } catch(e: IOException) {
                e.printStackTrace();
            }
        }
    }
}