package com.mai.cat_chain.block.main.star

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
import com.mai.cat_chain.utils.MLog
import com.mai.xmai_fast_lib.baseadapter.BaseListViewAdapter
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder
import com.mai.xmai_fast_lib.baseadapter.BaseViewPagerAdapter
import org.jetbrains.anko.sdk25.coroutines.onScrollListener
import org.jetbrains.anko.textColor

/**
 * Created by maijuntian on 2018/6/19.
 */
class RankAdapter(data: MutableList<Rank>?) : BaseViewPagerAdapter<Rank>(data) {

    override fun bindLayoutId(position: Int): Int {
        return R.layout.vp_item_main_rank
    }

    override fun initView(data: Rank?, viewHolder: BaseViewHolder?) {
        if (viewHolder!!.position == 1) {
            viewHolder.setText(R.id.tv_tip, "喵钻")
        } else {
            viewHolder.setText(R.id.tv_tip, "H值")
        }

        if (data!!.myRank != null) {

            var rankIndex: String
            rankIndex = if (data.myRank.ranking == null)
                "-"
            else
                data.myRank.ranking.toString()

            viewHolder.setText(R.id.tv_my_index, rankIndex)
                    .setText(R.id.tv_my_name, data.myRank.userName)
                    .setText(R.id.tv_my_level, "LV." + data.myRank.level)
                    .setText(R.id.tv_my_value, data.myRank.score.toString())
            Glide.with(viewHolder.view.context).load(data.myRank.headImgUrl).into(viewHolder.findViewById(R.id.iv_my_head_icon))
        }

        var listView: ListView = viewHolder.findViewById(R.id.lv_rank)
        listView.adapter = object : BaseListViewAdapter<TopRank>(data!!.topRank) {
            override fun bindLayoutId(position: Int): Int {
                return R.layout.lv_item_main_rank
            }

            override fun initView(data: TopRank?, viewHolder: BaseViewHolder?) {
                var tvName: TextView = viewHolder!!.findViewById(R.id.tv_name)
                var tvIndex: TextView = viewHolder.findViewById(R.id.tv_index)
                var tvLevel: TextView = viewHolder.findViewById(R.id.tv_level)
                var tvValue: TextView = viewHolder.findViewById(R.id.tv_value)

                if (viewHolder.position < 3) {
                    var color: Int = ContextCompat.getColor(context, R.color.yellow)
                    tvName.textColor = color
                    tvIndex.textColor = color
                    tvLevel.textColor = color
                    tvValue.textColor = color
                    tvIndex.setBackgroundResource(R.mipmap.main_icon_mingci)
                } else {
                    var color: Int = ContextCompat.getColor(context, R.color.blue)
                    tvName.textColor = color
                    tvIndex.textColor = color
                    tvLevel.textColor = color
                    tvValue.textColor = color
                    tvIndex.setBackgroundResource(R.mipmap.main_index_ci)
                }

                tvIndex.text = data!!.ranking.toString()
                tvName.text = data.userName
                tvLevel.text = "LV." + data.level
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