package ph.stacktrek.novare.snakeandladder.james.lumba.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.snakeandladder.james.lumba.databinding.WinnerPersonBinding

class WinnerAdapter(private var winnerList: Array<String?>):
    RecyclerView.Adapter<WinnerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinnerAdapter.ViewHolder {
        val winnerPersonBinding = WinnerPersonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(winnerPersonBinding)
    }

    override fun onBindViewHolder(holder: WinnerAdapter.ViewHolder, position: Int) {
        holder.bindItems(winnerList[position])
    }

    override fun getItemCount(): Int = winnerList.size

    inner class ViewHolder(private val winnerPersonBinding: WinnerPersonBinding):
        RecyclerView.ViewHolder(winnerPersonBinding.root){

        fun bindItems(winner: String?){

            winnerPersonBinding.winnerName.text = winner
        }
    }
}