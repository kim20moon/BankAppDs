package com.example.bankappds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.bankappds.databinding.ExpenditureListBinding



class ExpenditureAdapter (private val pays: MutableList<Expenditure>?)
    : RecyclerView.Adapter<ExpenditureAdapter.Holder>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ExpenditureListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (pays != null ) holder.bind(pays[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }


    override fun getItemCount() = pays?.size ?:0

    class Holder(private val binding: ExpenditureListBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind( pay : Expenditure) {
            if ( pay.year == 0 && pay.month == 0 ) {
                binding.imgCategory.isVisible = true

                binding.imgCategory.setImageResource(
                    when(pay.category) {
                        Ecategory.FOOD -> R.drawable.food
                        Ecategory.FINANCE -> R.drawable.finance
                        Ecategory.SHOPPING -> R.drawable.shopping
                        Ecategory.ENTERTAIN -> R.drawable.entertainment
                        Ecategory.HOBBY -> R.drawable.hobby
                        Ecategory.HEALTH -> R.drawable.health
                        Ecategory.HOME -> R.drawable.home
                        Ecategory.ETC -> R.drawable.etc
                        else -> 0
                    }
                )
            }

            else {
                binding.imgCategory.isVisible = false
            }

            binding.txtYear.text = pay.year.toString()
            binding.txtMonth.text = pay.month.toString()
            binding.txtDay.text = pay.day.toString()
            binding.txtExpense.text = pay.expense.toString()
            binding.txtMemo.text = pay.memo
            binding.txtCategory.text = pay.category.toString()
    }
}}

