package com.example.ladm_u3_basededatosmixta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    val basedatos = BaseDatos(this, "nuevoingreso", null, 1)
    var IDs = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mostrarTodos()

        registroregresar.setOnClickListener {
            finish()
        }

        busqueda.setOnClickListener {
            val otraVentana = Intent(this, MainActivity4::class.java)
            startActivity(otraVentana)
        }


    }


    //se invoca cada que se cambie la BD
    fun mostrarTodos() {
        var persona = basedatos.readableDatabase
        val lista = ArrayList<String>()

        IDs.clear()
        //este es como un select
        var resultado = persona.query("ESTUDIANTE", arrayOf("*"), null, null, null, null, null)
        if (resultado.moveToFirst()) {
            //me puede regresar 1 o mas registros por eso el do while
            do {
                val data = "Nombre: "+resultado.getString(1) + "\nEscuela: " + resultado.getString(2) + "\nTelefono: " +resultado.getString(3)+ "\nCarrera 1: " + resultado.getString(4)+ "\nCarrera 2: " + resultado.getString(5)+ "\nCorreo: " + resultado.getString(6)+ "\nFecha: " + resultado.getString(7)
                lista.add(data)
                IDs.add(resultado.getInt(0))
            } while (resultado.moveToNext())
        } else {
            lista.add("LA TABLA ESTA VACIA")
        }
        listaPersonas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista)

        listaPersonas.setOnItemClickListener { adapterView, view, i, l ->
            val idSeleccionado = IDs.get(i)
            var nombre = lista.get(i)
            nombre = nombre.substring(0, nombre.indexOf("\n")).uppercase()

            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("QUE DESEAS HACER CON: ${nombre}?")
                .setPositiveButton("NADA"){d,i->}
                .setNegativeButton("ELIMINAR"){d,i->
                    eliminar(idSeleccionado)
                }
                .setNeutralButton("ACTUALIZAR"){d,i->
                    actualizar(idSeleccionado)
                }
                .show()

        }

    }

    private fun actualizar(idSeleccionado: Int){
        val otraVentana = Intent(this, MainActivity3::class.java)
        //Este es para enviar datos de una ventana a otra
        otraVentana.putExtra("idSeleccionado", idSeleccionado.toString())
        startActivity(otraVentana)
    }

    private fun eliminar(idSeleccionado: Int) {
        //se pone writable por que se va a modificar
        val resultado = basedatos.writableDatabase.delete("ESTUDIANTE", "ID=?", arrayOf(idSeleccionado.toString()))
        if(resultado == 0){
            AlertDialog.Builder(this)
                .setMessage("ERROR NO SE BORRO")
                .show()
        }else{
            Toast.makeText(this, "SE BORRO CON EXITO", Toast.LENGTH_LONG). show()
            mostrarTodos()
        }
    }

    //----------------------------
    override fun onRestart() {
        super.onRestart()
        mostrarTodos()
    }

}