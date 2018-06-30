package com.mai.cat_chain.block.record

import android.annotation.SuppressLint
import android.support.v4.view.ViewPager
import com.mai.cat_chain.MyApplication
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.javabean.RecordList
import com.mai.cat_chain.javabean.User
import com.mai.cat_chain.net.schedules.SchedulerProvider
import com.mai.cat_chain.utils.Dateutils
import com.mai.cat_chain.widget.XLineChartLandView
import com.mai.cat_chain.widget.XLineChartView
import com.mai.cat_chain.widget.YLineChartLandView
import com.mai.cat_chain.widget.YLineChartView
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder
import com.mai.xmai_fast_lib.baseadapter.BaseViewPagerAdapter
import kotlinx.android.synthetic.main.activity_record_land.*

/**
 * Created by maijuntian on 2018/6/15.
 */
class LandRecordActivity : BaseActivity() {

    var hRecordList: RecordList? = null
    var mRecordList: RecordList? = null


    override fun setLayoutId(): Int {
        return R.layout.activity_record_land
    }

    override fun initView() {

        tv_date.text = MyApplication.instance!!.beginTime + "  -  " + MyApplication.instance!!.endTime

        hRecordList = MyApplication.instance!!.hRecordList
        mRecordList = MyApplication.instance!!.mRecordList

        vp_chart.run {
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            rb_button_h.isChecked = true
                        }
                        1 -> {
                            rb_button_m.isChecked = true
                        }
                    }
                }

            })
            offscreenPageLimit = 2
        }
        iv_return.setOnClickListener {
            finish()
        }

        rb_button_h.setOnClickListener {
            vp_chart.currentItem = 0
        }

        rb_button_m.setOnClickListener {
            vp_chart.currentItem = 1
        }

        rb_button_h.isChecked = true

        showVpRecord()
    }

    private fun showVpRecord() {

        val datas = listOf(hRecordList!!, mRecordList!!)

        vp_chart.adapter = object : BaseViewPagerAdapter<RecordList>(datas) {


            override fun bindLayoutId(position: Int): Int {
                return R.layout.vp_item_record_land_chart
            }

            override fun initView(data: RecordList?, viewHolder: BaseViewHolder?) {

                val yLine: YLineChartLandView = viewHolder!!.findViewById(R.id.yLine)
                val xLine: XLineChartLandView = viewHolder.findViewById(R.id.xLine)

                yLine.setDatas(data!!.income, data.outcome)
                xLine.setDatas(data!!.income, data.outcome)
            }

        }
    }

}