package com.example.diceroller

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MutliPlayerActivity : AppCompatActivity() {
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    var myRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("message")
    private lateinit var myRef2: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mutli_player)

        val database = Firebase.database

        myRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                return
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                return
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                return
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ON", "Cancelled", databaseError.toException())
                Toast.makeText(
                    this@MutliPlayerActivity,
                    "Didn't Load Comments.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val myButton = findViewById<Button>(R.id.button)
        myButton.setOnClickListener {
            role()

        }

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()

    }

    private fun role() {
        val dice = mutableListOf<Int>()
        val Dice1 = (1..6).random()
        val Dice2 = (1..6).random()
        val Dice3 = (1..6).random()
        val Dice4 = (1..6).random()
        val timeStamp: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        val userName = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val User = User(timeStamp, userName, Dice1, Dice2, Dice3, Dice4)
        myRef.child(timeStamp).setValue(User)
    }

    private fun getUserData() {

        myRef = FirebaseDatabase.getInstance().getReference("message")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = MyAdapter(userArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }
}