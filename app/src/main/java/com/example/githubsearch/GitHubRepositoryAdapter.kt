package com.example.githubsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.db.GithubRepoDBDataClass
import kotlinx.android.synthetic.main.item_view_github_repositories.view.*
import java.util.*

/**
 * Search Repository Screen Adapter to display the repositories
 */
class GitHubRepositoryAdapter(val adapterOnClick: (GithubRepoDBDataClass) -> Unit) :
    RecyclerView.Adapter<GitHubRepositoryAdapter.ViewHolder>() {

    var githubRepoArrayList = ArrayList<GithubRepoDBDataClass>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            itemView.apply {
                tvGithubRepository?.text =
                    githubRepoArrayList[absoluteAdapterPosition].repositoryFullName

                setOnClickListener {
                    adapterOnClick(githubRepoArrayList[absoluteAdapterPosition])
                }
            }
        }
    }

    /**
     * Create View Holder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_github_repositories, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = githubRepoArrayList.size

    /**
     * Bind View Holder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    /**
     * Set Data fetched from the api response to the adapter
     */
    fun setData(list: List<GithubRepoDBDataClass>) {
        githubRepoArrayList.clear()
        githubRepoArrayList.addAll(list)
        this.notifyDataSetChanged()
    }
}