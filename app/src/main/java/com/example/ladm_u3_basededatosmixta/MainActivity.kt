package com.example.ladm_u3_basededatosmixta

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    val basedatos = BaseDatos(this, "nuevoingreso", null, 1)
    var IDs = ArrayList<Int>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mostrarTodos()

        //boton
        registrar.setOnClickListener {
            var persona = basedatos.writableDatabase //modo lectura escritura
            var datos = ContentValues()
            val dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a"))

            datos.put("NOMBRE", nombre.text.toString())
            datos.put("ESCUELA", escuela.text.toString())
            datos.put("TELEFONO", telefono.text.toString())
            datos.put("CARRERAUNO", carrera1.text.toString())
            datos.put("CARRERADOS", carrera2.text.toString())
            datos.put("CORREO", correo.text.toString())
            datos.put("FECHA", dateTime.toString())

            var resultado = persona.insert("ESTUDIANTE", "ID", datos) //regresa -1 si no se inserto
            if(resultado== -1L){
                AlertDialog.Builder(this).setTitle("ERROR")
                    .setMessage("NO SE PUDO GUARDAR ").show()
            }else{
                Toast.makeText(this, "SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                nombre.setText("")
                escuela.setText("")
                telefono.setText("")
                carrera1.setText("")
                carrera2.setText("")
                correo.setText("")
                //mostrarTodos()
            }
        }//boton

        ver.setOnClickListener {
            val otraVentana = Intent(this, MainActivity2::class.java)
            startActivity(otraVentana)
        }


    }

    /*//se invoca cada que se cambie la BD
    fun mostrarTodos() {
        var persona = basedatos.readableDatabase
        val lista = ArrayList<String>()

        IDs.clear()
        //este es como un select
        var resultado = persona.query("ESTUDIANTE", arrayOf("*"), null, null, null, null, null)
        if (resultado.moveToFirst()) {
            //me puede regresar 1 o mas registros por eso el do while
            do {
                val data = resultado.getString(1) + "\n" + resultado.getString(2) + "\n$ " +resultado.getInt(3)+ "\n" + resultado.getString(4)+ "\n" + resultado.getString(5)+ "\n" + resultado.getString(6)+ "\n" + resultado.getString(7)
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
        val otraVentana = Intent(this, MainActivity2::class.java)
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
    }*/
}