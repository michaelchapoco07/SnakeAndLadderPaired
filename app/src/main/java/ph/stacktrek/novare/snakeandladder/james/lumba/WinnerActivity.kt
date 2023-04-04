package ph.stacktrek.novare.snakeandladder.james.lumba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ph.stacktrek.novare.snakeandladder.james.lumba.adapters.WinnerAdapter
import ph.stacktrek.novare.snakeandladder.james.lumba.databinding.ActivityWinnerBinding
import ph.stacktrek.novare.snakeandladder.james.lumba.utility.PreferenceUtility

class WinnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWinnerBinding
    private lateinit var winnerAdapter: WinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityWinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        load5Winners()
    }

    private fun load5Winners() {
        var winners: Array<String?>
        PreferenceUtility(applicationContext).apply {
            var num = 5
            while(getStringPreferences(num.toString()) == "" && num > 0) {
                num--
            }
            winners = arrayOfNulls<String>(num)
            while(getStringPreferences(num.toString()) != "") {
                winners.set(num - 1, getStringPreferences(num.toString()))
                num--
            }
        }

        winnerAdapter = WinnerAdapter(winners)

        with(binding.winnersList) {
            layoutManager = LinearLayoutManager(applicationContext,
                LinearLayoutManager.VERTICAL,
                false)

            adapter = winnerAdapter
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val goToMenu = Intent(applicationContext,
            MenuActivity::class.java)
        startActivity(goToMenu)
        finish()
    }
}