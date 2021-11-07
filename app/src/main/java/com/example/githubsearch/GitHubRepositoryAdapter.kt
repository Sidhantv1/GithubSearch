package com.example.githubsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.db.GithubRepoDBDataClass
import kotlinx.android.synthetic.main.item_view_github_repositories.view.*
import java.util.*

class GitHubRepositoryAdapter(val adapterOnClick: (GithubRepoDBDataClass) -> Unit) :
    RecyclerView.Adapter<GitHubRepositoryAdapter.ViewHolder>() {

    var arrayList = ArrayList<GithubRepoDBDataClass>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            itemView.apply {
                tvGithubRepository?.text = arrayList[absoluteAdapterPosition].repositoryFullName

                setOnClickListener {
                    adapterOnClick(arrayList[absoluteAdapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_github_repositories, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    fun setData(list: List<GithubRepoDBDataClass>) {
        arrayList.clear()
        arrayList.addAll(list)
        this.notifyDataSetChanged()
    }
}