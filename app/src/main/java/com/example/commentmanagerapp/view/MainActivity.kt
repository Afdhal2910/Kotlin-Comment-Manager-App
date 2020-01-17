package com.example.commentmanagerapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commentmanagerapp.R
import com.example.commentmanagerapp.adapter.DataAdapter
import com.example.commentmanagerapp.model.Posts
import com.example.commentmanagerapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var dataList = ArrayList<Posts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        getData()

    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)

        //setting up the adapter
        recyclerView.adapter= DataAdapter(dataList,this)
        recyclerView.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun getData() {

        val call: Call<List<Posts>> = ApiClient.getClient.getPosts()
        call.enqueue(object : Callback<List<Posts>> {
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                val dataModel = response!!.body()

                if(response.isSuccessful){
                   Log.d("Success Call Api", "tEst")

                    dataList.addAll(response!!.body()!!)

                    recyclerView.adapter!!.notifyDataSetChanged()

                }else{

                }
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
            }
        })
    }
}
