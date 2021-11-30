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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class SubActivity : AppCompatActivity() {
    lateinit var clMain : ConstraintLayout
    lateinit var recyclerMain: RecyclerView
    lateinit var submitButton: Button
    lateinit var inputText: EditText
    lateinit var scoreText: TextView
    lateinit var sharedPreferences: SharedPreferences

    var secretPhraseLong = arrayListOf('c', 'o', 'd', 'i', 'n', 'g', 'd', 'o', 'j', 'o')
    var secretPhrase = ArrayList<String>()
    var guessedRight = ArrayList<Char>()
    var trys = 10
    var score = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        //ui connections
        val refreshButton = findViewById<Button>(R.id.btRefresh)
        clMain = findViewById(R.id.clMain2)
        submitButton = findViewById(R.id.btSubmit)
        scoreText = findViewById(R.id.tvScore)
        inputText = findViewById(R.id.textInput)

        //RecyclerView assigned to this activity with its content (secretPhrase)
        recyclerMain = findViewById(R.id.rvMain)
        recyclerMain.adapter = RVAdapterSub(secretPhrase)
        recyclerMain.layoutManager = LinearLayoutManager(this)

        //preserve data
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        //get the saved data and put in text view scoreText
        scoreText.text = sharedPreferences.getString("myScore", "Highest score: ").toString()

        //buttons interactions
        submitButton.setOnClickListener {
            lookUpChar()
        }
        refreshButton.setOnClickListener {
            recreate()
        }
    }
    //Functions
    private fun lookUpChar() {
        val input = inputText.text.toString().toCharArray()

        if (trys == 0) {
            submitButton.isEnabled = false
            Snackbar.make(
                clMain,
                "Game over! You gussed incorrectly. The secret phrase was ${secretPhraseLong.joinToString()}",
                BaseTransientBottomBar.LENGTH_LONG
            ).show()
        } else {
            for (i in input) {
                val count = secretPhraseLong.count { it.equals(i) }

                if (secretPhraseLong.contains(i)) {
                    guessedRight.add(i)
                    secretPhrase.add("found $count ${i.uppercase()}(s)")
                    finishGame()
                } else {
                    secretPhrase.add("wrong guess")
                    trys--
                    secretPhrase.add("you have $trys guesses left")
                }
                println("$guessedRight + $secretPhraseLong")
            }
        }

        recyclerMain.adapter?.notifyDataSetChanged()
        inputText.text.clear()
    }


    fun finishGame() {
        if (guessedRight.distinct().size == secretPhraseLong.distinct().size &&
            guessedRight.containsAll(secretPhraseLong)
        ) {
            Snackbar.make(
                clMain,
                "Well Done! You guessed correctly. The secret phrase was ${secretPhraseLong.joinToString()}",
                BaseTransientBottomBar.LENGTH_LONG
            ).show()
            score += trys
            print(score)
            scoreText.text = ("Highest score: " + score)
            submitButton.isEnabled = false

            //save the score locally
            var sharedScore = scoreText.text.toString()
            Log.d("TEST_EDITTEXT", sharedScore)
            with(sharedPreferences.edit()) {
                putString("myScore", sharedScore)
                apply()
            }
            scoreText.text = sharedPreferences.getString("myScore", "Highest score: ").toString()

        }
    }

    //main menue configuration
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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}