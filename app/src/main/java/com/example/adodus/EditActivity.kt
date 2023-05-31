package com.example.adodus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.adodus.db.Adapter
import com.example.adodus.db.DbManager
import com.example.adodus.db.IntentConstant

class EditActivity : AppCompatActivity() {

    val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getMyIntent()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
    }

    fun onClickSave(view: View){
        val edTitle = findViewById<EditText>(R.id.edTItle)
        val edDesc = findViewById<EditText>(R.id.edDesc)
        val myTitle = edTitle.text.toString()
        val myDesc = edDesc.text.toString()

        if (myTitle != "" && myDesc != ""){
            dbManager.insertInto(myTitle, myDesc)
        }

        finish()
    }

    fun getMyIntent(){
        val edTitle = findViewById<EditText>(R.id.edTItle)
        val edDesc = findViewById<EditText>(R.id.edDesc)
        val i = intent

        if(i != null){

            if(i.getStringExtra(IntentConstant.I_TITLE_KEY) != "null"){
                edTitle.setText(i.getStringExtra(IntentConstant.I_TITLE_KEY))
                edDesc.setText(i.getStringExtra(IntentConstant.I_DESC_KEY))
            }

        }

    }
}