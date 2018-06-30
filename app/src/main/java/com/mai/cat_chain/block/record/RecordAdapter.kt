package com.mai.cat_chain.block.record

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.mai.cat_chain.R
import com.mai.cat_chain.javabean.Record
import com.mai.xmai_fast_lib.baseadapter.BaseListViewAdapter
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder
import org.jetbrains.anko.textColor

/**
 * Created by maijuntian on 2018/6/26.
 */
class RecordAdapter(mData: List<Record>?) : BaseListViewAdapter<Record>(mData) {
    override fun bindLayoutId(position: Int): Int {
        return R.layout.lv_item_record
    }

    override fun initView(data: Record?, viewHolder: BaseViewHolder?) {
        val tvType: TextView = viewHolder!!.findViewById(R.id.tv_type)
        val tvTime: TextView = viewHolder!!.findViewById(R.id.tv_time)
        val tvValue: TextView = viewHolder.findViewById(R.id.tv_value)

        if (data!!.coin > 0f) {
            tvType.text = "领取"
            tvType.textColor = ContextCompat.getColor(context, R.color.white)
            tvTime.textColor = ContextCompat.getColor(context, R.color.white)
            tvValue.textColor = ContextCompat.getColor(context, R.color.white)
            tvValue.text = "+" + data.coin.toString()
        } else {
            tvType.text = "支出"
            tvType.textColor = ContextCompat.getColor(context, R.color.sred)
            tvTime.textColor = ContextCompat.getColor(context, R.color.sred)
            tvValue.textColor = ContextCompat.getColor(context, R.color.sred)
            tvValue.text = data.coin.toString()
        }

        tvTime.text = data.time
    }
}
