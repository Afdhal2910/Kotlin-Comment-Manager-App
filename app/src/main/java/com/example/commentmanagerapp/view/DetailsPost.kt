package com.example.commentmanagerapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commentmanagerapp.R
import com.example.commentmanagerapp.adapter.NewCommentAdapter
import com.example.commentmanagerapp.model.Comment
import com.example.commentmanagerapp.model.Posts
import com.example.commentmanagerapp.network.ApiClient
import kotlinx.android.synthetic.main.activity_details_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsPost : AppCompatActivity() {

    var valuePostId:Int = 0
    lateinit var myRecyclerView: RecyclerView
    var dataList = ArrayList<Comment>()
    lateinit var adapter : NewCommentAdapter
    lateinit var nameEditText: EditText
    lateinit var searchBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_post)

        getBundleData()
        getPostData()
        initUi()
        initListeners()
        setupRecyclerView()
        getCommentData()
    }

    private fun getBundleData(){
        val bundle :Bundle ?=intent.extras
        if (bundle!=null) {
            val postId = bundle.getInt("dd")
            passValuePostId(postId)
        }
    }

    private fun passValuePostId(postId: Int) {
        valuePostId = postId
    }

    //get Posts Data from Api
    private fun getPostData() {
        val call: Call<Posts> = ApiClient.getClient.getPostsData(valuePostId)
        call.enqueue(object: Callback<Posts>{
            override fun onFailure(call: Call<Posts>, t: Throwable) {
                Log.d("Fail","No Data From Api")
            }

            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                val dataModel = response!!.body()

                if(response.isSuccessful){
                    var title = dataModel!!.title
                    var body = dataModel!!.body

                    post_title.text = title
                    post_body.text = body

                }else{
                    Log.d("Fail","No Data")
                }
            }
        })
    }

    //initialize ui
    private fun initUi(){
        nameEditText = findViewById(R.id.nameEditText)
        searchBtn = findViewById(R.id.searchBtn)
    }

    //search btn function
    private fun initListeners() {
        searchBtn.setOnClickListener {
            var name = nameEditText.text.toString()
            if (!TextUtils.isEmpty(name) ) {
                adapter.getFilter().filter(name)
            } else {
              //  defaultArray()
                Toast.makeText(this, "No Results Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //setup recyclerview
    @SuppressLint("WrongConstant")
    private fun setupRecyclerView() {
        myRecyclerView = findViewById(R.id.recycler_view_comment) as RecyclerView
        var mLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        myRecyclerView.layoutManager = mLayoutManager
        var mDividerItemDecoration = DividerItemDecoration(myRecyclerView.getContext(), mLayoutManager.getOrientation())
        myRecyclerView.addItemDecoration(mDividerItemDecoration)
    }


    //call data comment from api
    private fun getCommentData() {
        val call: Call<List<Comment>> = ApiClient.getClient.getComment(valuePostId)
        call.enqueue(object: Callback<List<Comment>>{
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                dataList.addAll(response!!.body()!!)
                adapter = NewCommentAdapter(dataList,dataList)
                myRecyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })
    }
}



