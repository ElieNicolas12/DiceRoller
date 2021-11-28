package com.example.diceroller

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    companion object
    {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var context: Context
    }

    lateinit var NombreDeDice: TextView
    lateinit var Plusbtn: Button
    lateinit var Minusbtn: Button
    var currentDice=0
    var ListofImage : MutableList<ImageView> = mutableListOf()
    var MaxDice=4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = applicationContext
        NombreDeDice=findViewById(R.id.NbrDices)
        Plusbtn=findViewById(R.id.btnPlus)
        Minusbtn =findViewById(R.id.btnMinus)
        ListofImage.add(findViewById(R.id.Dice1))
        ListofImage.add(findViewById(R.id.Dice2))
        ListofImage.add(findViewById(R.id.Dice3))
        ListofImage.add(findViewById(R.id.Dice4))


    }

   fun FctMinus(sender: View)
   {
       var x :Int= NombreDeDice.text.toString().toInt()
       var int_button = x
       int_button -= 1
       NombreDeDice.text = int_button.toString()


       Minusbtn.isEnabled = NombreDeDice.text != "0"
       Plusbtn.isEnabled = NombreDeDice.text != "4"
   }
    fun FctPlus(sender: View)
    {
        var x :Int= NombreDeDice.text.toString().toInt()
        var int_button = x
        int_button += 1
        NombreDeDice.text = int_button.toString()


        Minusbtn.isEnabled = NombreDeDice.text != "0"
        Plusbtn.isEnabled = NombreDeDice.text != "4"
    }

    fun RollAllDices(sender: View)
    {
        val Liste: MutableList<Int> = mutableListOf()
        var X: Int= NombreDeDice.text.toString().toInt()
        for(i in 0 until X)
        {
            val Rand = (1..6).random()
            Liste.add(Rand)
            ListofImage[i].setImageResource(newDiceImage(Rand))
            ListofImage[i].isVisible = true

        }
        for(i in X until MaxDice )
        {
            ListofImage[i].isVisible=false
        }
      //  AllResult.Ajoutez_un_Res(ResultDices(Liste, X))
        PostAPI(Liste)

    }

    fun RollOneDice(sender: View)
    {
        val Liste: MutableList<Int> = mutableListOf()
        var X: Int= NombreDeDice.text.toString().toInt()
        if(X==0)
        {
            for(i in 0 until X)
            {
                ListofImage[i].isVisible = false
            }
           return
        }
        val Rand=(1..6).random()
        ListofImage[currentDice].setImageResource(newDiceImage(Rand))
        ListofImage[currentDice].isVisible=true
        currentDice++
        if(currentDice== X) { currentDice=0}
        for(i in X until MaxDice )
        {
            ListofImage[i].isVisible=false
        }
       // AllResult.Ajoutez_Un_Dice(Rand,X)
        PostAPI(Liste)
    }
    private fun newDiceImage(randum_nb: Int): Int
    {
        when(randum_nb)
        {
            1 -> return R.drawable.dice_1
            2 -> return R.drawable.dice_2
            3 -> return R.drawable.dice_3
            4 -> return R.drawable.dice_4
            5 -> return R.drawable.dice_5
            6 -> return R.drawable.dice_6
        }
        return 0
    }

    fun Openhistory(sender: View)
    {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
    fun MultiPlayer(sender: View)
    {
        val intent = Intent(this, MutliPlayerActivity::class.java)
        startActivity(intent)
    }



    private fun PostAPI(Dice : MutableList<Int>)
    {
        val url = "https://615c198ac298130017735f0a.mockapi.io/Dices"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject("{\"Dices\":${Dice}}"),
            { response -> println(response) },
            { error -> error.printStackTrace() })
        queue.add(jsonObjectRequest)

    }



}

data class ResultDices(var Dices: MutableList<Int>, var NombreDeDices: Int)
{
    override fun toString(): String
    {
        var result : String = ""
        result += "[ "
        for (item in Dices)
        {
            result += item
            result += "  "
        }
        result+="] Nombre of Dice: "+NombreDeDices

        return result
    }
}

object AllResult
{
    private val All_Result: MutableList<ResultDices> = mutableListOf()
    fun Ajoutez_un_Res(res: ResultDices)
    {
        All_Result.add(res);
    }
    fun Remove_All_Res()
    {
        All_Result.clear();
    }
    fun AfficheTous() : MutableList<ResultDices>
    {
        return All_Result;
    }
    fun Ajoutez_Un_Dice(dice: Int,NombreOfDice: Int) {
        if (All_Result.size == 0) {
            val tmp: MutableList<Int> = mutableListOf(dice)
            All_Result.add(ResultDices(tmp, NombreOfDice))
            return
        }
        var LastResult = All_Result.removeLast()
        if (LastResult.Dices.size == LastResult.NombreDeDices) {
            All_Result.add(LastResult)
            val tmp: MutableList<Int> = mutableListOf(dice)
            All_Result.add(ResultDices(tmp, NombreOfDice))
        } else {
            LastResult.Dices.add(dice)
            LastResult.NombreDeDices = NombreOfDice
            All_Result.add(LastResult)
        }
    }

    fun size(): Int
    {
        return  All_Result.size
    }
}

