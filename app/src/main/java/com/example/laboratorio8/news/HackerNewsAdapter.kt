package com.example.laboratorio8.news
/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.laboratorio8.databinding.NewsItemsFragmentBinding
import com.example.laboratorio8.network.HackerNewsUser

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class HackerNewsAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<HackerNewsUser, HackerNewsAdapter.NewsViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holderNews: NewsViewHolder, position: Int) {
        val hackerNewsUser = getItem(position)
        holderNews.itemView.setOnClickListener {
            onClickListener.onClick(hackerNewsUser)
        }
        holderNews.bind(hackerNewsUser)
    }

    /**
     * The RepoViewHolder constructor takes the binding variable from the associated
     * RepoItemLayout, which nicely gives it access to the full [GithubRepo] information.
     */
    class NewsViewHolder(private var binding: NewsItemsFragmentBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hackerNews: HackerNewsUser) {
            binding.hackerNews = hackerNews
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemsFragmentBinding.inflate(layoutInflater, parent, false)

                return NewsViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<HackerNewsUser>() {
        override fun areItemsTheSame(oldItem: HackerNewsUser, newItem: HackerNewsUser): Boolean {
            return oldItem.objectID == newItem.objectID
        }

        override fun areContentsTheSame(oldItem: HackerNewsUser, newItem: HackerNewsUser): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MarsProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MarsProperty]
     */
    class OnClickListener(val clickListener: (githubRepo: HackerNewsUser) -> Unit) {
        fun onClick(githubRepo: HackerNewsUser) = clickListener(githubRepo)
    }
}
