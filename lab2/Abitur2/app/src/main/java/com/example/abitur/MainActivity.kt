package com.example.abitur

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object{
        val REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 1234
        val REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE = 4321
    }

    private lateinit var  adapter : abiturAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = abiturAdapter(applicationContext,R.layout.cellgrid)


        try{
            var abitur = intent.getSerializableExtra("item1") as Abitur
            adapter.addItem(abitur)
        }
        catch(ex:Exception){}

        val grid = findViewById<GridView>(R.id.abitursGrid)
        grid.adapter = adapter
        registerForContextMenu(grid)
        findViewById<TextView>(R.id.ascore).text = adapter.getAverageScore().toString().toEditable()

        findViewById<Button>(R.id.add).setOnClickListener{
            var intent = Intent(this, AddOrUpdate::class.java)
            intent.putExtra("item", Abitur.getNullAbitur())
            startActivity(intent)
        }

        var loadActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if(uri != null){
                    var inputString= ""

                    val permissionStatus =
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        inputString = contentResolver.openInputStream(uri)?.bufferedReader().use { it?.readText() }?: ""
                    } else {
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE
                        )
                    }
                    try {
                        for (i in Abitur.getAbiturs(inputString))
                            adapter.addItem(i)
                    } catch (e: Exception){

                    }
                }
            }
        }

        var saveActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if(uri != null){
                    val permissionStatus =
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        var outstream = contentResolver.openOutputStream(uri)?.bufferedWriter().use{ it?.write(Abitur.getJSON(adapter.getAll()))}
                    } else {
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE
                        )
                    }
                }
            }
        }


        findViewById<Button>(R.id.load).setOnClickListener{
            var fileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            fileIntent.type = "*/*"
            loadActivity.launch(fileIntent)
        }

        findViewById<Button>(R.id.save).setOnClickListener{
            var fileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            fileIntent.type = "*/*"
            saveActivity.launch(fileIntent)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?,v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.getMenuInfo() as AdapterView.AdapterContextMenuInfo
        return when(item.itemId)
        {
            R.id.edit ->
            {
                var intent = Intent(this, AddOrUpdate::class.java)
                intent.putExtra("item", adapter.getItem(info.position))
                startActivity(intent)
                true
            }
            R.id.delete ->
            {
                adapter.deleteItem(info.position)
                true
            }
            else -> return super.onContextItemSelected(item)
        }
    }


}