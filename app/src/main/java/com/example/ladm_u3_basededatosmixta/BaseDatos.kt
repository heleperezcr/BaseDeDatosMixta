package com.example.ladm_u3_basededatosmixta

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        /*
       Se ejecuta cuando de "EJECUTA x  PRIMERA VEZ" la app
       dentro del celular del cliente y construye la
       base de datos y las tablas
       */

        db.execSQL("CREATE TABLE ESTUDIANTE(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(400), ESCUELA VARCHAR(300), TELEFONO VARCHAR(400), CARRERAUNO VARCHAR(400), CARRERADOS VARCHAR(400), CORREO VARCHAR(400), FECHA VARCHAR(400))")


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        /*
       Se ejecuta SI y SOLO SI hay un cambio de version
       La version utiliza los numeros naturales (1,2,3...)
       y se espera que a un cambio de version uses un numero mayor
       que el actual
       */
    }

}