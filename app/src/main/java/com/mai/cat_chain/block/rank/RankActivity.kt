package com.mai.cat_chain.block.rank

import android.support.v4.view.ViewPager
import android.text.format.DateUtils
import android.view.View
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.block.main.star.StarBaseContract
import com.mai.cat_chain.block.main.star.StarBaseModel
import com.mai.cat_chain.block.main.star.StarBasePresenter
import com.mai.cat_chain.javabean.NotCollectedM
import com.mai.cat_chain.javabean.Rank
import com.mai.cat_chain.widget.DiamondView
import com.mai.cat_chain.net.schedules.SchedulerProvider
import kotlinx.android.synthetic.main.activity_rank.*
import org.jetbrains.anko.toast

/**
 * Created by maijuntian on 2018/6/15.
 */
class RankActivity : BaseActivity(), StarBaseContract.View {
    override fun collectedMSuccess(id: String, number: Float) {
    }

    override fun fail(msg: String) {
        srl_refresh.isRefreshing = false
        toast(msg)
    }

    override fun queryMSuccess(datas: List<NotCollectedM>) {
    }

    override fun queryHRankSuccess(datas: Rank) {
        ranks.clear()
        ranks.add(0, datas)
        presenter.queryRank("coin")
    }

    override fun queryMRankSuccess(datas: Rank) {
        ranks.add(1, datas)

        showRank()
    }

    private fun showRank() {

        srl_refresh.isRefreshing = false

        rankAdapter = RankDetailAdapter(ranks)

        tv_time.text = "更新时间：" + DateUtils.formatDateTime(this,
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME.or(DateUtils.FORMAT_SHOW_DATE))

        vp_rank.adapter = rankAdapter

        tv_h.visibility = View.VISIBLE
        tv_m.visibility = View.INVISIBLE
    }

    override fun collectedFail() {
    }


    var ranks = mutableListOf<Rank>()

    var rankAdapter: RankDetailAdapter? = null

    private val presenter: StarBasePresenter by lazy {
        StarBasePresenter(StarBaseModel(), this, SchedulerProvider.getInstatnce()!!)
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_rank
    }

    override fun initView() {

        vp_rank.run {
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            rb_h.isChecked = true
                            tv_h.visibility = View.VISIBLE
                            tv_m.visibility = View.INVISIBLE

                        }
                        1 -> {
                            rb_zuan.isChecked = true
                            tv_h.visibility = View.INVISIBLE
                            tv_m.visibility = View.VISIBLE
                        }
                    }
                }

            })
            offscreenPageLimit = 2
        }
        rb_h.isChecked = true

        presenter.queryRank("h_value")

        srl_refresh.setOnRefreshListener {
            presenter.queryRank("h_value")
        }

        iv_return.setOnClickListener {
            finish()
        }
    }

}