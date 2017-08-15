package com.pingpongpacket.truckview.presentation.views

import android.support.v7.util.DiffUtil
import com.pingpongpacket.truckview.models.Weighing

class WeighingListDiffCallback(val oldList: ArrayList<Weighing>?,
                               val newList: ArrayList<Weighing>?):
        DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int,
                                 newItemPosition: Int): Boolean {
        return newList!![newItemPosition] == oldList!![oldItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList!![newItemPosition] == oldList!![oldItemPosition]
    }

}