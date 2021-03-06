package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.layoutManager = LinearLayoutManager(this)
        fetchData()
        this.mAdapter = NewListAdapter(this)

        recycler_view.adapter = this.mAdapter
    }
//    private fun fetchDate() {
//        val newsURL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=cd1700429f0348e9bb8caf7471dd98a4"
//
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET,
//            newsURL,
//            null,
//                {
//                    val newJsonArray = it.getJSONArray("articles")
//                    val newsArray = ArrayList<News>()
//                    for(i in 0 until newJsonArray.length()){
//                        val newsJsonObject = newJsonArray.getJSONObject(i)
//                        val news = News(
//                            newsJsonObject.getString("title"),
//                            newsJsonObject.getString("author"),
//                            newsJsonObject.getString("url"),
//                            newsJsonObject.getString("urlToImage"),
//                        )
//                        newsArray.add(news)
//                    }
//                    this.mAdapter.updateNews(newsArray)
//                },
//                {
//                    if(it == NetworkError()) {
//                        Toast.makeText(this, "No network available $it", Toast.LENGTH_LONG).show()
//                    }else{
//                        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
//                        Log.d("VolleyError", "$it")
//                    }
//                }
//        )
//
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
//    }

    private fun fetchData() {
        //volly library
        val url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=cd1700429f0348e9bb8caf7471dd98a4"
        //making a request
        val jsonObjectRequest = object: JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener {
                    val newsJsonArray = it.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for(i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("description"),
                                newsJsonObject.getString("publishedAt"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }

                    mAdapter.updateNews(newsArray)
                },
                Response.ErrorListener {
                }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}

