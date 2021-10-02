package com.example.diceroller

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SecondActivity : AppCompatActivity() {
    lateinit var linearLayoutHistory : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        linearLayoutHistory = findViewById<LinearLayout>(R.id.linear_layouy_history)

        val all_dices = AllResult.AfficheTous()

        for (dices in all_dices)
        {
            val text = TextView(this)
            text.textSize = 20f
            text.text = dices.toString()

            val card = CardView(this)
            card.addView(text)
            card.radius = 20F
            card.useCompatPadding = true

            linearLayoutHistory.addView(card)
        }

    }

    fun on_back_press(sender: View)
    {
        onBackPressed()
    }

    fun delete_All(sender: View)
    {
        AllResult.Remove_All_Res()
        linearLayoutHistory.removeAllViews()
    }
}