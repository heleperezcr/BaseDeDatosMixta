package com.example.ladm_u3_basededatosmixta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity4 : AppCompatActivity() {
    val columnas = arrayListOf<String>("CARRERAUNO", "CARRERADOS", "ESCUELA", "FECHA")
    val basedatos = BaseDatos(this, "nuevoingreso", null, 1)

    var colSeleccionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)


        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, columnas)
        columnabusqueda.adapter = adaptador

        columnabusqueda.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                colSeleccionada = columnas[position]
                Toast.makeText(this@MainActivity4, "Seleccionaste: " + columnas[position], Toast.LENGTH_LONG)
                    .show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                colSeleccionada = "ID"
            }
        }

        buscar.setOnClickListener {
            var persona = basedatos.readableDatabase
            val lista = ArrayList<String>()

            var clave = clave.text.toString()

            var result = persona.query("ESTUDIANTE", arrayOf("*"), "$colSeleccionada=?", arrayOf(clave), null, null, null)

            if (result.moveToFirst()){
                do {
                    val data = "Nombre: "+result.getString(1) + "\nEscuela: " + result.getString(2) + "\nTelefono: " + result.getString(3)+ "\nCarrera 1: " + result.getString(4)+ "\nCarrera 2: " + result.getString(5)+ "\nCorreo: " + result.getString(6)+ "\nFecha: " + result.getString(7)
                    lista.add(data)
                } while (result.moveToNext())
            } else{
                lista.add("NO SE ENCONTRARON COINCIDENCIAS")
            }
            listabusqueda.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista)
        }

        buscarregresar.setOnClickListener {
            finish()
        }


    }
}