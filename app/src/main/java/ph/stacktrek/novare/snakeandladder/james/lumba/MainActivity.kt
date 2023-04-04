package ph.stacktrek.novare.snakeandladder.james.lumba

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
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
        binding.playerOneText.setText(bundle!!.getString("player1"))
        binding.playerTwoText.setText(bundle!!.getString("player2"))
        binding.playerThreeText.setText(bundle!!.getString("player3"))
        binding.playerFourText.setText(bundle!!.getString("player4"))

        var player1Coords = arrayOf(0, 10)
        var player2Coords = arrayOf(0, 10)
        var player3Coords = arrayOf(0, 10)
        var player4Coords = arrayOf(0, 10)

        val snakeLadderMap = HashMap<String, Array<Int>>()
//        Snake
        snakeLadderMap.put("27", arrayOf(10, 10))
        snakeLadderMap.put("67", arrayOf(6, 10))
        snakeLadderMap.put("86", arrayOf(6, 8))
        snakeLadderMap.put("24", arrayOf(8, 9))
        snakeLadderMap.put("82", arrayOf(4, 8))
        snakeLadderMap.put("51", arrayOf(6, 5))
        snakeLadderMap.put("71", arrayOf(8, 3))
//        Ladder
        snakeLadderMap.put("110", arrayOf(8, 7))
        snakeLadderMap.put("410", arrayOf(4, 9))
        snakeLadderMap.put("810", arrayOf(10, 8))
        snakeLadderMap.put("18", arrayOf(2, 6))
        snakeLadderMap.put("88", arrayOf(6, 3))
        snakeLadderMap.put("106", arrayOf(7, 4))
        snakeLadderMap.put("13", arrayOf(2, 1))
        snakeLadderMap.put("103", arrayOf(9, 1))

        var currentPlayer = 1
        showCurrentPlayer(currentPlayer)

        PreferenceUtility(applicationContext).apply {
            println("Preference 1: ${getStringPreferences("1" )}")
            println("Preference 2: ${getStringPreferences("2" )}")
            println("Preference 3: ${getStringPreferences("3" )}")
            println("Preference 4: ${getStringPreferences("4" )}")
            println("Preference 5: ${getStringPreferences("5" )}")
        }

        binding.rollButton.setOnClickListener {

            val diceResult = rollDice()

            when (currentPlayer) {
                1 -> {
                    var newCoords = updateCoords(diceResult, player1Coords)
                    movePlayer(currentPlayer, player1Coords, newCoords)
                    player1Coords = newCoords

                    if(snakeLadderMap.containsKey("${player1Coords[0]}${player1Coords[1]}")) {
                        newCoords = snakeLadderMap.getValue("${player1Coords[0]}${player1Coords[1]}")
                        movePlayer(currentPlayer, player1Coords, newCoords)
                        player1Coords = newCoords
                    }

                    if (player1Coords[0] == 10 && player1Coords[1] == 1) {
                        showCongratulationDialog(bundle!!.getString("player1").toString())
                    }
                }
                2 -> {
                    var newCoords = updateCoords(diceResult, player2Coords)
                    movePlayer(currentPlayer, player2Coords, newCoords)
                    player2Coords = newCoords

                    if(snakeLadderMap.containsKey("${player2Coords[0]}${player2Coords[1]}")) {
                        newCoords = snakeLadderMap.getValue("${player2Coords[0]}${player2Coords[1]}")
                        movePlayer(currentPlayer, player2Coords, newCoords)
                        player2Coords = newCoords
                    }

                    if (player2Coords[0] == 10 && player2Coords[1] == 1) {
                        showCongratulationDialog(bundle!!.getString("player2").toString())
                    }
                }
                3 -> {
                    var newCoords = updateCoords(diceResult, player3Coords)
                    movePlayer(currentPlayer, player3Coords, newCoords)
                    player3Coords = newCoords

                    if(snakeLadderMap.containsKey("${player3Coords[0]}${player3Coords[1]}")) {
                        newCoords = snakeLadderMap.getValue("${player3Coords[0]}${player3Coords[1]}")
                        movePlayer(currentPlayer, player3Coords, newCoords)
                        player3Coords = newCoords
                    }

                    if (player3Coords[0] == 10 && player3Coords[1] == 1) {
                        showCongratulationDialog(bundle!!.getString("player3").toString())
                    }
                }
                4 -> {
                    var newCoords = updateCoords(diceResult, player4Coords)
                    movePlayer(currentPlayer, player4Coords, newCoords)
                    player4Coords = newCoords

                    if(snakeLadderMap.containsKey("${player4Coords[0]}${player4Coords[1]}")) {
                        newCoords = snakeLadderMap.getValue("${player4Coords[0]}${player4Coords[1]}")
                        movePlayer(currentPlayer, player4Coords, newCoords)
                        player4Coords = newCoords
                    }

                    if (player4Coords[0] == 10 && player4Coords[1] == 1) {
                        showCongratulationDialog(bundle!!.getString("player4").toString())
                    }
                }
            }

            currentPlayer++
            if (currentPlayer > bundle.size()) {
                currentPlayer = 1
            }
            showCurrentPlayer(currentPlayer)
        }
    }


    fun rollDice(): Int {

        val random = Random
        val diceResult = random.nextInt(6) + 1
        binding.diceText.setText(diceResult.toString())
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
        textView.text = currentPlayer.toString()

        presentCounter.addView(textView)
    }


    fun showCurrentPlayer(currentPlayer: Int) {

        binding.playerOneText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
        binding.playerTwoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
        binding.playerThreeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
        binding.playerFourText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

        when (currentPlayer) {
            1 -> {binding.playerOneText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)}
            2 -> {binding.playerTwoText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)}
            3 -> {binding.playerThreeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)}
            4 -> {binding.playerFourText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)}
        }
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