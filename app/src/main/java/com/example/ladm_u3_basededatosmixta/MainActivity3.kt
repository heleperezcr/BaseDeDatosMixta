package com.example.ladm_u3_basededatosmixta

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    val basedatos = BaseDatos(this, "nuevoingreso", null, 1)
    var idSeleccionado = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        //Para recibir datos de otra ventana
        idSeleccionado = intent.extras!!.getString("idSeleccionado")!!

        val cursor =
            basedatos.readableDatabase.query("ESTUDIANTE", arrayOf("*"), "ID=?", arrayOf(idSeleccionado),null,null,null)

        if(cursor.moveToFirst()){
            act_nombre.setText(cursor.getString(1))
            act_escuela.setText(cursor.getString(2))
            act_telefono.setText(cursor.getString(3))
            act_carrera1.setText(cursor.getString(4))
            act_carrera2.setText(cursor.getString(5))
            act_correo.setText(cursor.getString(6))
            act_fecha.setText(cursor.getString(7))
        }else{
            act_nombre.setText("NO HAY DATOS")
            act_escuela.setText("NO HAY DATOS")
            act_telefono.setText("NO HAY DATOS")
            act_carrera1.setText("NO HAY DATOS")
            act_carrera2.setText("NO HAY DATOS")
            act_correo.setText("NO HAY DATOS")
            act_fecha.setText("NO HAY DATOS")

            act_nombre.isEnabled=false
            act_escuela.isEnabled=false
            act_telefono.isEnabled=false
            act_carrera1.isEnabled=false
            act_carrera2.isEnabled=false
            act_correo.isEnabled=false
            act_fecha.isEnabled=false
            actualizar.isEnabled=false
        }//if

        actualizar.setOnClickListener {
            val datos = ContentValues()
            datos.put("NOMBRE", act_nombre.text.toString())
            datos.put("ESCUELA", act_escuela.text.toString())
            datos.put("TELEFONO", act_telefono.text.toString().toString())
            datos.put("CARRERAUNO", act_carrera1.text.toString())
            datos.put("CARRERADOS", act_carrera2.text.toString())
            datos.put("CORREO", act_correo.text.toString())
            datos.put("FECHA", act_fecha.text.toString())

            val res =
                basedatos.writableDatabase.update("ESTUDIANTE", datos, "ID=?", arrayOf(idSeleccionado))
            if(res == 0){
                AlertDialog.Builder(this)
                    .setMessage("NO SE PUDO ACTUALIZAR")
                    .show()
            }else{
                Toast.makeText(this, "EXITO", Toast.LENGTH_LONG).show()
                act_nombre.isEnabled=false
                act_escuela.isEnabled=false
                act_telefono.isEnabled=false
                act_carrera1.isEnabled=false
                act_carrera2.isEnabled=false
                act_correo.isEnabled=false
                act_fecha.isEnabled=false
                actualizar.isEnabled=false
                actualizar.setText("SE ACTUALIZO CORRECTAMENTE")
            }
        }

        regresar.setOnClickListener {
            finish()
        }


    }
}