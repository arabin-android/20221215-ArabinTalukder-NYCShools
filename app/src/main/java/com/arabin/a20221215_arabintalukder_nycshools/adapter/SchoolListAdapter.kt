package com.arabin.a20221215_arabintalukder_nycshools.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arabin.a20221215_arabintalukder_nycshools.databinding.SchoolListCellBinding
import com.arabin.a20221215_arabintalukder_nycshools.ui.helper.ViewHelper
import com.arabin.roomdb.entity.SchoolDetails

/**
 * @author Arabin
 * @since 12/16/2022
 * A Recycler view adapter
 * to show all schools and its summary
 * takes a function[onItemCLied] to get callback on each item
 * */
class SchoolListAdapter(
    private val items: List<SchoolDetails>,
    private val onItemCLied: (item: SchoolDetails) -> Unit
) : RecyclerView.Adapter<SchoolListAdapter.SchoolItemViewHolder>() {

    /**
     * Creates view holder
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolItemViewHolder {
        return SchoolItemViewHolder(
            SchoolListCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Binds viewholder
     * */
    override fun onBindViewHolder(holder: SchoolItemViewHolder, position: Int) {
        holder.setDetails(items[position], onItemCLied)
    }

    /**
     * returns total items
     * */
    override fun getItemCount(): Int {
        return items.size
    }

    /**View holder that sets all attributes*/
    class SchoolItemViewHolder(private val binding: SchoolListCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setDetails(item: SchoolDetails, onItemCLied: (item: SchoolDetails) -> Unit) {
            binding.apply {
                ViewHelper.setSchoolInfo(binding, item)
                root.setOnClickListener {
                    onItemCLied.invoke(item)
                }
            }
        }
    }

}