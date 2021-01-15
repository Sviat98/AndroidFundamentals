package com.bashkevich.androidfundamentals.moviesdetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bashkevich.androidfundamentals.R
import com.bashkevich.androidfundamentals.model.RetrofitModule
import com.bashkevich.androidfundamentals.model.entity.Actor

class ActorsAdapter : RecyclerView.Adapter<ActorsViewHolder>() {

    private var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)

        return ActorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {

        val actor = actors[position]

        holder.actorImageView.load(actor.picture) {
            crossfade(true)
            transformations(RoundedCornersTransformation(15f))
        }

        holder.actorName.text = actor.name

    }

    fun bindActors(newActors: List<Actor>) {
        actors = newActors
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = actors.size
}

class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val actorImageView = itemView.findViewById<ImageView>(R.id.actor_image)

    val actorName = itemView.findViewById<TextView>(R.id.actor_name)

}
