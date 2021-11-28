package com.example.diceroller


data class User(var Date:String ?= null,var Username:String ?= null,var Dice1:Int  ?= null, var Dice2: Int ?= null, var Dice3: Int ?= null, var Dice4: Int ?= null) {
    fun User(D: String, name: String, d1: Int, d2: Int, d3: Int, d4: Int) {
        Date = D
        Username = name
        Dice1 = d1
        Dice2 = d2
        Dice3 = d3
        Dice4 = d4
    }
}
