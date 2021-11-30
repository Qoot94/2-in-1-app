package com.example.a2_in_1_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import kotlin.random.Random

class ThirdActivity : AppCompatActivity() {

    lateinit var clMain : ConstraintLayout

    lateinit var myMessage: String
    lateinit var sharedPreferences: SharedPreferences
    //main variables linked to ui
    lateinit var myREcycle: RecyclerView
//    lateinit var main: ConstraintLayout
    lateinit var submitButton: Button
    lateinit var inputText: EditText
    lateinit var textView: TextView

    //variables needed for functionality
    val random = Random.nextInt(0, 10)
    var myNumbers = ArrayList<String>()
    var trys = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        //link var to ui
        myREcycle = findViewById<RecyclerView>(R.id.rvMain3)
        myREcycle.adapter = RVAdapterThird(myNumbers)
        myREcycle.layoutManager = LinearLayoutManager(this)

        clMain = findViewById(R.id.clMain3)
        submitButton = findViewById(R.id.btSubmit)
        inputText = findViewById(R.id.textInput)
        textView = findViewById(R.id.textView)
        textView.text = sharedPreferences.getString("myText", "Guess number between 0 and 10").toString()  //get the saved data and put in text view
        Log.d("TEST_EDITTEXT", textView.text.toString())

        submitButton.setOnClickListener {
            addNumbers()
            var insertedText = inputText.text.toString()
            Log.d("TEST_EDITTEXT", insertedText)
            with(sharedPreferences.edit()) {
                putString("myText", insertedText)
                apply()
            }
            textView.text = sharedPreferences.getString("myText", "ttt").toString()  //get the saved data and put in text view
            Toast.makeText(this,"data saved $insertedText", Toast.LENGTH_SHORT).show()
        }
    }

    //functions
    fun addNumbers() {
        try {
            val input = inputText.text.toString()
            myNumbers.add("You guessed $input")

            if (input.toInt() == random) {
                myNumbers.add("You have guessed correctly")

                Snackbar.make(clMain, "correct! congrats", BaseTransientBottomBar.LENGTH_LONG).show()
                submitButton.isEnabled = false
            } else {
                //myNumbers.add(random.toString())
                myNumbers.add("You have ${trys} guesses left")
                Snackbar.make(clMain, "wrong guess, try again", BaseTransientBottomBar.LENGTH_LONG).show()
                trys--
            }
        } catch (e: Exception) {
            print(e)
        }
        myREcycle.adapter?.notifyDataSetChanged()
        inputText.text.clear()
    }

    //main menu configuration
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_Item1 -> {
                val intent = Intent(this, SubActivity::class.java)
                startActivity(intent)
                Snackbar.make(clMain, "Hello there!", Snackbar.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_Item2 -> {
                val intent = Intent(this, ThirdActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_Item -> {
                item.setVisible(true)
                item.title ="Main Menu"
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}