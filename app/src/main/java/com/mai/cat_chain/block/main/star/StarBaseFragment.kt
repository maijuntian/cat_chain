package com.mai.cat_chain.block.main.star

import android.support.v4.view.ViewPager
import android.text.format.DateUtils
import android.view.View
import com.mai.cat_chain.MyApplication
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseFragment
import com.mai.cat_chain.block.others.CatCheatsActivity
import com.mai.cat_chain.block.rank.RankActivity
import com.mai.cat_chain.block.task.TaskActivity
import com.mai.cat_chain.javabean.NotCollectedM
import com.mai.cat_chain.javabean.Rank
import com.mai.cat_chain.javabean.User
import com.mai.cat_chain.widget.DiamondView
import kotlinx.android.synthetic.main.fragment_cat_base.*
import com.mai.cat_chain.net.schedules.SchedulerProvider
import com.mai.cat_chain.utils.MLog
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Created by maijuntian on 2018/6/15.
 */
class StarBaseFragment : BaseFragment(), StarBaseContract.View {
    override fun fail(msg: String) {
        toast(msg)
        srl_refresh.isRefreshing = false
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
        tv_time.text = "更新时间：" + DateUtils.formatDateTime(activity,
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME.or(DateUtils.FORMAT_SHOW_DATE))

        rankAdapter = RankAdapter(ranks)
        vp_rank.adapter = rankAdapter

        tv_h.visibility = View.VISIBLE
        tv_m.visibility = View.INVISIBLE

    }

    override fun collectedFail() {
        presenter.queryM()
    }

    override fun collectedMSuccess(id: String, number: Float) {

        if (MyApplication.instance!!.user != null) {
            MyApplication.instance!!.user!!.totalCoin += number
            notifyUserInfo()
        }

        notCollectedMs = notCollectedMs!!.filter { notCollectedM -> notCollectedM.id != id }

        MLog.log("是否为空-->" + notCollectedMs!!.size)
        if (notCollectedMs!!.isEmpty())
            presenter.queryM()
    }

    var notCollectedMs: List<NotCollectedM>? = null

    var ranks = mutableListOf<Rank>()

    var dvs = mutableListOf<DiamondView>()

    var rankAdapter: RankAdapter? = null

    private val presenter: StarBasePresenter by lazy {
        StarBasePresenter(StarBaseModel(), this, SchedulerProvider.getInstatnce()!!)
    }

    override fun queryMSuccess(datas: List<NotCollectedM>) {
        notCollectedMs = datas

        showMiaoZuan()
    }

    fun showMiaoZuan() {
        if (!notCollectedMs!!.isEmpty()) {
            notCollectedMs!!.forEachIndexed { index, notCollectedM ->
                if (index < 10) {
                    dvs[index].tag = notCollectedM.id + "-" + notCollectedM.coinNumber
                    dvs[index].show(notCollectedM.coinType, notCollectedM.coinNumber)
                }
            }
        }
    }


    override fun setLayoutId(): Int {
        return R.layout.fragment_cat_base
    }

    override fun onResume() {
        super.onResume()

        presenter.queryM()
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

        dvs.add(dv_1)
        dvs.add(dv_2)
        dvs.add(dv_3)
        dvs.add(dv_4)
        dvs.add(dv_5)
        dvs.add(dv_6)
        dvs.add(dv_7)
        dvs.add(dv_8)
        dvs.add(dv_9)
        dvs.add(dv_10)

        dvs.forEach { diamondView: DiamondView ->
            diamondView.setOnClickListener(dvClickListener)
        }

        tv_task.setOnClickListener {
            startActivity<TaskActivity>()
        }

        tv_cheats.setOnClickListener {
            startActivity<CatCheatsActivity>()
        }

        tv_h.setOnClickListener {
            startActivity<RankActivity>()
        }

        tv_m.setOnClickListener {
            startActivity<RankActivity>()
        }

        presenter.queryRank("h_value")

        srl_refresh.setOnRefreshListener {
            presenter.queryRank("h_value")
        }

        notifyUserInfo()
    }

    val dvClickListener: View.OnClickListener = View.OnClickListener { v: View? ->
        (v as DiamondView).hide()
        val indexStr: List<String> = v.tag.toString().split("-")
        presenter.collectedM(indexStr[0], indexStr[1].toFloat())
    }

    override fun notifyUserInfo() {
        var data: User? = MyApplication.instance!!.user ?: return
        tv_h_value.text = "H值：" + data!!.totalHValue.toString()
        tv_m_value.text = "喵钻：" + data.totalCoin.toString()
    }
}