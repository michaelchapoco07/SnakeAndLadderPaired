package ph.stacktrek.novare.snakeandladder.james.lumba

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.size
import ph.stacktrek.novare.snakeandladder.james.lumba.databinding.ActivityMainBinding
import ph.stacktrek.novare.snakeandladder.james.lumba.utility.PreferenceUtility
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        for (i in 0 until bundle!!.size()) {

            val textView = TextView(this)
            textView.setTextSize(20f)
            textView.setTextColor(Color.WHITE)
            textView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            textView.text = bundle!!.getString("player${i + 1}")

            binding.gameInfoLinearlayout.addView(textView)
        }

        val playersCoords = HashMap<Int, Array<Int>>()
        playersCoords[1] = arrayOf(0, 10)
        playersCoords[2] = arrayOf(0, 10)
        playersCoords[3] = arrayOf(0, 10)
        playersCoords[4] = arrayOf(0, 10)

        val snakeLadderMap = HashMap<String, Array<Int>>()
//        Snake
        snakeLadderMap["27"] = arrayOf(10, 10)
        snakeLadderMap["67"] = arrayOf(6, 10)
        snakeLadderMap["86"] = arrayOf(6, 8)
        snakeLadderMap["24"] = arrayOf(8, 9)
        snakeLadderMap["82"] = arrayOf(4, 8)
        snakeLadderMap["51"] = arrayOf(6, 5)
        snakeLadderMap["71"] = arrayOf(8, 3)
//        Ladder
        snakeLadderMap["110"] = arrayOf(8, 7)
        snakeLadderMap["410"] = arrayOf(4, 9)
        snakeLadderMap["810"] = arrayOf(10, 8)
        snakeLadderMap["18"] = arrayOf(2, 6)
        snakeLadderMap["88"] = arrayOf(6, 3)
        snakeLadderMap["106"] = arrayOf(7, 4)
        snakeLadderMap["13"] = arrayOf(2, 1)
        snakeLadderMap["103"] = arrayOf(9, 1)

        var currentPlayer = 1
        showCurrentPlayer(currentPlayer)

        binding.rollButton.setOnClickListener {

            binding.rollButton.visibility = View.GONE
            binding.endTurnButton.visibility = View.VISIBLE
            binding.diceText.visibility = View.VISIBLE

            val diceResult = rollDice()

            var newCoords = updateCoords(diceResult, playersCoords.getValue(currentPlayer))
            movePlayer(currentPlayer, playersCoords.getValue(currentPlayer), newCoords)
            playersCoords[currentPlayer] = newCoords

            if(snakeLadderMap.containsKey(
                    "${playersCoords.getValue(currentPlayer)[0]}${playersCoords
                        .getValue(currentPlayer)[1]}")) {
                newCoords = snakeLadderMap.getValue("${playersCoords
                    .getValue(currentPlayer)[0]}${playersCoords.getValue(currentPlayer)[1]}")
                movePlayer(currentPlayer, playersCoords.getValue(currentPlayer), newCoords)
                playersCoords[currentPlayer] = newCoords
            }

            if (playersCoords.getValue(currentPlayer)[0] == 10 && playersCoords
                    .getValue(currentPlayer)[1] == 1) {
                showCongratulationDialog(bundle!!.getString("player$currentPlayer").toString())
            }
        }

        binding.endTurnButton.setOnClickListener {
            binding.endTurnButton.visibility = View.GONE
            binding.rollButton.visibility = View.VISIBLE
            binding.diceText.visibility = View.GONE

            currentPlayer++
            if (currentPlayer > bundle.size()) {
                currentPlayer = 1
            }
            showCurrentPlayer(currentPlayer)
        }
    }


    fun rollDice(): Int {

        val diceImageView = findViewById<ImageView>(R.id.diceImageResId)
        val random = Random
        val diceResult = random.nextInt(6) + 1
        binding.diceText.setText(diceResult.toString())
        val diceImageResId = when (diceResult) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImageView.setImageResource(diceImageResId)
        return diceResult
    }


    fun updateCoords(diceResult: Int, playerCoords: Array<Int>): Array<Int> {

        var coords: Array<Int>
        if (playerCoords[0] == 0) {
            coords = arrayOf(diceResult, playerCoords[1])
        }
        else if (playerCoords[0] + diceResult > 10) {
            if (playerCoords[1] - 1 < 1) {
                coords = arrayOf(10, playerCoords[1])
            } else {
                coords = arrayOf(playerCoords[0] + diceResult - 10, playerCoords[1] - 1)
            }
        }
        else {
            coords = arrayOf(playerCoords[0] + diceResult, playerCoords[1])
        }

        return coords
    }


    fun movePlayer(currentPlayer: Int, pastCoords: Array<Int>, presentCoords: Array<Int>) {

        if (pastCoords[0] > 0) {
            val pastRow = binding.boardLinearLayout.getChildAt(pastCoords[1] - 1 ) as LinearLayoutCompat
            val pastTile = pastRow.getChildAt(pastCoords[0] - 1 ) as FrameLayout
            val pastCounter = pastTile.getChildAt(0) as TextView
            pastTile.removeView(pastCounter)
        }

        val presentRow = binding.boardLinearLayout.getChildAt(presentCoords[1] - 1 ) as LinearLayoutCompat
        val presentCounter = presentRow.getChildAt(presentCoords[0] - 1 ) as FrameLayout

        val textView = TextView(this)
        textView.setTextSize(35f)
        textView.setTypeface(null, Typeface.BOLD)
        textView.setTextColor(Color.rgb(255,165,0))
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.BLUE)
        textView.text = currentPlayer.toString()

        presentCounter.addView(textView)
    }


    fun showCurrentPlayer(currentPlayer: Int) {

        if (currentPlayer - 1 > 0) {
            val pastTextView = binding.gameInfoLinearlayout.getChildAt(currentPlayer - 1) as TextView
            pastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        } else {
            val pastTextView = binding.gameInfoLinearlayout.getChildAt(binding
                .gameInfoLinearlayout.size - 1) as TextView
            pastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        }

        val presentTextView = binding.gameInfoLinearlayout.getChildAt(currentPlayer) as TextView
        presentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
    }

    fun showCongratulationDialog(player: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setTitle("Winner!")
        builder.setMessage("Congratulation ${player.substring(2)}")
        builder.setPositiveButton("Congrats") { dialog, _ ->
            // Handle OK button click
            PreferenceUtility(applicationContext).apply {
                var num = 4;
                while(getStringPreferences(num.toString()) == "" && num > 0) {
                    num--
                }
                while(getStringPreferences(num.toString()) != "") {
                    saveStringPreferences((num + 1).toString(), getStringPreferences(num.toString()))
                    num--
                }

                saveStringPreferences("1", player.substring(2))
            }

            val goToWinner = Intent(
                applicationContext,
                WinnerActivity::class.java
            )
            startActivity(goToWinner)
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setTitle("Confirm exit")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                super.onBackPressed() // Call the superclass implementation

                val goToMenu = Intent(applicationContext,
                    MenuActivity::class.java)
                startActivity(goToMenu)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
}