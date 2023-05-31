package com.example.adodus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adodus.db.Adapter
import com.example.adodus.db.DbManager

class MainActivity : AppCompatActivity() {

    val adapter = Adapter(ArrayList(), this)
    val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbManager.openDb()
        init()

    }

    override fun onResume(){
        super.onResume()
        dbManager.openDb()
        fillAdapter()
    }

    fun onClickAdd(view: View){
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    fun init(){
        val rcView = findViewById<RecyclerView>(R.id.rcView)
        rcView.layoutManager = LinearLayoutManager(this)
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(rcView)
        rcView.adapter = adapter
    }

    fun fillAdapter(){
        val tvNoElements = findViewById<TextView>(R.id.tvNoElements)
        val list = dbManager.readFrom()
        adapter.updateAdapter(list)
        if(list.size > 0){
            tvNoElements.visibility = View.GONE
        } else {
            tvNoElements.visibility = View.VISIBLE
        }

    }


    private fun getSwapMg(): ItemTouchHelper {


        return ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition, dbManager)
            }
        })
    }

}