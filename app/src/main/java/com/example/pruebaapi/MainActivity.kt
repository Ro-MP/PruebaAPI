package com.example.pruebaapi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebaapi.model.Post
import com.example.pruebaapi.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var tvJson: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvJson = findViewById(R.id.tv_json)
        getPosts()
    }

    private fun getPosts() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitApi: Repository = retrofit.create(Repository::class.java)

        val call : Call<List<Post>> = retrofitApi.getPosts()

        call.enqueue(object : Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (!response.isSuccessful) {
                    tvJson.text = "Codigo: ${response.code()}"
                    return
                }
                val postList : List<Post>? = response.body()

                postList?.map {
                    var content = ""
                    content += "${it.userId} ${it.id} ${it.title} ${it.body} \n\n"
                    tvJson.append(content)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                tvJson.text = t.message
            }

        })


    }

}
