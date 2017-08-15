package com.pingpongpacket.truckview.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.pingpongpacket.truckview.R

class WeighingView: FrameLayout {
    @BindView(R.id.ll_row)
    @JvmField var row: View? = null
    @BindView(R.id.tv_date)
    @JvmField var tvDate: TextView? = null
    @BindView(R.id.tv_product)
    @JvmField var tvProduct: TextView? = null
    @BindView(R.id.tv_weight)
    @JvmField var tvWeight: TextView? = null

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):
            super(context, attributeSet)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.item_weighing, this, true)
        ButterKnife.bind(this)
    }


}