package com.example.diceroller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray


class SecondActivity : AppCompatActivity()
{
    lateinit var linearLayoutHistory: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        linearLayoutHistory = findViewById<LinearLayout>(R.id.linear_layouy_history)
        val all_dices = AllResult.AfficheTous()
        get()
        Log.d("list: ", all_dices.toString())
        for (dices in all_dices)
        {
            val text = TextView(this)
            text.textSize = 20f
            text.text = dices.toString()
            val card = CardView(this)
            card.addView(text)
            card.useCompatPadding = true
            linearLayoutHistory.addView(card)
        }

    }

    fun on_back_press(sender: View) {
        onBackPressed()
    }

    fun delete_All(sender: View) {
        AllResult.Remove_All_Res()
        linearLayoutHistory.removeAllViews()
    }






    private fun get()
    {
        val list: MutableList<ResultDices> = mutableListOf()
        val queue : RequestQueue = Volley.newRequestQueue(MainActivity.context)
        val url = "https://615c198ac298130017735f0a.mockapi.io/Dices"
        val postRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                    response -> Log.d("Resp1", response.toString())
                for (i in 0 until response.length() - AllResult.size())
                {
                    val jsonObject = response.getJSONObject(i)
                    val diceArray: JSONArray = jsonObject.getJSONArray("Dices")
                    val diceMutableList: MutableList<Int> = mutableListOf()
                    for (j in 0 until diceArray.length())
                    {
                        diceMutableList.add(diceArray.get(j) as Int)
                    }
                    list.add(ResultDices(diceMutableList, diceMutableList.size))
                }

                for (dices in list)
                {
                    val text = TextView(this)
                    text.textSize = 20f
                    text.text = dices.toString()
                    val card = CardView(this)
                    card.addView(text)
                    card.useCompatPadding = true
                    linearLayoutHistory.addView(card)
                }
            },
            {
                    error -> Log.d("Error", error.toString())
            })

        queue.add(postRequest)
    }

}


