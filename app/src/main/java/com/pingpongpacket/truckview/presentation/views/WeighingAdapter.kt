package com.pingpongpacket.truckview.presentation.views

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.models.Weighing

class WeighingAdapter(val listener: (Weighing) -> Unit):
        RecyclerView.Adapter<WeighingAdapter.ViewHolder>() {
    val items: ArrayList<Weighing> = ArrayList<Weighing>()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int)=
            holder!!.bind(items[position], listener)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):
            ViewHolder{
        val view: WeighingView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.item_weighing_custom,
                parent, false) as WeighingView
        return ViewHolder(view)

    }

    fun setWeighingList(weighingList: ArrayList<Weighing>){
        val diffResult: DiffUtil.DiffResult = DiffUtil
                .calculateDiff(WeighingListDiffCallback(this.items, weighingList))
        this.items.clear()
        this.items.addAll(weighingList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val weighingView: WeighingView) :
            RecyclerView.ViewHolder(weighingView) {

        fun bind(item: Weighing, listener: (Weighing) -> Unit) = with(weighingView) {
            tvDate!!.text = item.date
            tvProduct!!.text = item.product
            tvWeight!!.text = item.netWeight.toString()
            setOnClickListener { listener(item) }
        }
    }


}