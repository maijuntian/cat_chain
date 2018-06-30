package com.mai.cat_chain.block.rank

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mai.cat_chain.R
import com.mai.cat_chain.javabean.Rank
import com.mai.cat_chain.javabean.TopRank
import com.mai.xmai_fast_lib.baseadapter.BaseListViewAdapter
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder
import com.mai.xmai_fast_lib.baseadapter.BaseViewPagerAdapter
import org.jetbrains.anko.sdk25.coroutines.onScrollListener
import org.jetbrains.anko.textColor

/**
 * Created by maijuntian on 2018/6/19.
 */
class RankDetailAdapter(data: MutableList<Rank>?) : BaseViewPagerAdapter<Rank>(data) {


    override fun bindLayoutId(position: Int): Int {
        return R.layout.vp_item_rank_rank
    }

    override fun initView(data: Rank?, viewHolder: BaseViewHolder?) {
        if (viewHolder!!.position == 1) {
            viewHolder.setText(R.id.tv_tip, "喵钻")
        } else {
            viewHolder.setText(R.id.tv_tip, "H值")
        }

        var listView: ListView = viewHolder.findViewById(R.id.lv_rank)

        listView.adapter = object : BaseListViewAdapter<TopRank>(data!!.topRank) {
            override fun bindLayoutId(position: Int): Int {

                when (mData[position].ranking) {
                    1 -> return R.layout.lv_item_rank_rank_first
                    2 -> return R.layout.lv_item_rank_rank_second
                    3 -> return R.layout.lv_item_rank_rank_third
                }
                if (data!!.myRank != null && mData[position].userId == data!!.myRank.userId) {
                    return R.layout.lv_item_rank_rank_me
                }
                return R.layout.lv_item_rank_rank
            }

            override fun initView(data: TopRank?, viewHolder: BaseViewHolder?) {
                var tvName: TextView = viewHolder!!.findViewById(R.id.tv_name)
                var tvValue: TextView = viewHolder.findViewById(R.id.tv_value)

                if (data!!.ranking > 3) {
                    var tvIndex: TextView = viewHolder.findViewById(R.id.tv_index)
                    tvIndex.text = data.ranking.toString()
                }

                tvName.text = data.userName
                tvValue.text = data.score.toString()

                Glide.with(context).load(data.headImgUrl).into(viewHolder.findViewById(R.id.iv_head_icon))
            }

        }

        listView.onScrollListener {
            onScroll { _, _, _, _ ->
                var enable = false
                if (listView.getChildCount() > 0) {
                    val firstItemVisible = listView.firstVisiblePosition == 0
                    val topOfFirstItemVisible = listView.getChildAt(0).top == 0
                    enable = firstItemVisible && topOfFirstItemVisible
                }

                val srlRefresh: SwipeRefreshLayout = (viewHolder.view.context as Activity).findViewById(R.id.srl_refresh)
                srlRefresh.isEnabled = enable
            }
        }
    }
}