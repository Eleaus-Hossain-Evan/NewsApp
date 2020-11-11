package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity(), NewsItemClicked, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        val adapter: NewListAdapter = NewListAdapter(fetchDate(), this)

        recycler_view.adapter = adapter
    }
    private fun fetchDate(): ArrayList<String> {
        val list = ArrayList<String>()
        for(i in 0 until 100){
            list.add("Item $i")
        }
        return list
    }

    override fun onItemClicked(item: String) {
        Toast.makeText(applicationContext, "$item is clicked",Toast.LENGTH_SHORT).show()
    }
}