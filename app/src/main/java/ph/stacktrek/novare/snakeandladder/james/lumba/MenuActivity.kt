package ph.stacktrek.novare.snakeandladder.james.lumba

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ph.stacktrek.novare.snakeandladder.james.lumba.databinding.ActivityMenuBinding
import ph.stacktrek.novare.snakeandladder.james.lumba.databinding.DialoguePlayersBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.winnersButton.setOnClickListener {
            val goToWinner = Intent(applicationContext,
                WinnerActivity::class.java)
            startActivity(goToWinner)
            finish()
        }
        binding.twoPlayersButton.setOnClickListener {
            showPlayersDialogue(2).show()
        }
        binding.threePlayersButton.setOnClickListener {
            showPlayersDialogue(3).show()
        }
        binding.fourPlayersButton.setOnClickListener {
            showPlayersDialogue(4).show()
        }
    }

    private fun showPlayersDialogue(players: Int): Dialog {
        return this.let {
            val builder = AlertDialog.Builder(it)
            builder.setCancelable(false)
            val dialoguePlayersBinding: DialoguePlayersBinding =
                DialoguePlayersBinding.inflate(it.layoutInflater)

            if (players == 2) {
                dialoguePlayersBinding.playerThreeLabel.visibility = View.GONE
                dialoguePlayersBinding.playerThreeInput.visibility = View.GONE
                dialoguePlayersBinding.playerFourLabel.visibility = View.GONE
                dialoguePlayersBinding.playerFourInput.visibility = View.GONE
            }
            if (players == 3) {
                dialoguePlayersBinding.playerFourLabel.visibility = View.GONE
                dialoguePlayersBinding.playerFourInput.visibility = View.GONE
            }


            with(builder) {
                setPositiveButton("START", DialogInterface.OnClickListener { dialog, id ->
                    val goToMain = Intent(
                        applicationContext,
                        MainActivity::class.java
                    )
                    val bundle = Bundle()

                    if (players == 2) {
                        val playerone = dialoguePlayersBinding.playerOneInput.text.toString()
                        val playertwo = dialoguePlayersBinding.playerTwoInput.text.toString()

                        if (playerone != "" && playertwo != "") {
                            bundle.putString("player1", "1. $playerone")
                            bundle.putString("player2", "2. $playertwo")
                            goToMain.putExtras(bundle);

                            startActivity(goToMain)
                            finish()
                        }
                    }
                    else if (players == 3) {
                        val playerone = dialoguePlayersBinding.playerOneInput.text.toString()
                        val playertwo = dialoguePlayersBinding.playerTwoInput.text.toString()
                        val playerthree = dialoguePlayersBinding.playerThreeInput.text.toString()

                        if (playerone != "" && playertwo != "" && playerthree != "") {
                            bundle.putString("player1", "1. $playerone")
                            bundle.putString("player2", "2. $playertwo")
                            bundle.putString("player3", "3. $playerthree")
                            goToMain.putExtras(bundle);

                            startActivity(goToMain)
                            finish()
                        }
                    }
                    else if (players == 4) {
                        val playerone = dialoguePlayersBinding.playerOneInput.text.toString()
                        val playertwo = dialoguePlayersBinding.playerTwoInput.text.toString()
                        val playerthree = dialoguePlayersBinding.playerThreeInput.text.toString()
                        val playerfour = dialoguePlayersBinding.playerFourInput.text.toString()

                        if (playerone != "" && playertwo != "" && playerthree != "" && playerfour != "") {
                            bundle.putString("player1", "1. $playerone")
                            bundle.putString("player2", "2. $playertwo")
                            bundle.putString("player3", "3. $playerthree")
                            bundle.putString("player4", "4. $playerfour")
                            goToMain.putExtras(bundle);

                            startActivity(goToMain)
                            finish()
                        }
                    }
                })
                setNegativeButton("CANCEL",DialogInterface.OnClickListener { dialog, id ->

                })
                setView(dialoguePlayersBinding.root)
                create()
            }
        }
    }
}