package com.example.abitur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText

class AddOrUpdate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_update)

        var abitur = intent.getSerializableExtra("item") as Abitur

        findViewById<EditText>(R.id.surname).text = abitur.surname.toEditable()
        findViewById<EditText>(R.id.name).text = abitur.name.toEditable()
        findViewById<EditText>(R.id.middlename).text = abitur.middlename.toEditable()

        val mark = abitur.marks.toString()
        findViewById<EditText>(R.id.mark).text = mark.substring(1 , mark.length -1).toEditable()

        findViewById<Button>(R.id.refresh).setOnClickListener{
            abitur.surname = findViewById<EditText>(R.id.surname).text.toString()
            abitur.name = findViewById<EditText>(R.id.name).text.toString()
            abitur.middlename = findViewById<EditText>(R.id.middlename).text.toString()
            abitur.marks =
                findViewById<EditText>(R.id.mark).text.split(",").map{ it.trim().toInt() }

            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("item1", abitur)
            startActivity(intent)
        }



    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)