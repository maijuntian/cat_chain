package com.mai.cat_chain.block.record

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.View
import com.mai.cat_chain.MyApplication
import com.mai.cat_chain.R
import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.base.BaseFragment
import com.mai.cat_chain.javabean.Record
import com.mai.cat_chain.javabean.RecordList
import com.mai.cat_chain.javabean.User
import com.mai.cat_chain.net.schedules.SchedulerProvider
import com.mai.cat_chain.utils.Dateutils
import com.mai.cat_chain.utils.MLog
import com.mai.cat_chain.widget.TimePickerView
import com.mai.cat_chain.widget.XLineChartView
import com.mai.cat_chain.widget.YLineChartView
import com.mai.xmai_fast_lib.baseadapter.BaseViewHolder
import com.mai.xmai_fast_lib.baseadapter.BaseViewPagerAdapter
import kotlinx.android.synthetic.main.activity_record.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created by maijuntian on 2018/6/15.
 */
class RecordActivity : BaseActivity(), RecordContract.View {
    override fun fail(msg: String) {
        toast(msg)
    }

    var hRecordList: RecordList? = null
    var mRecordList: RecordList? = null

    var beginTime: String? = null
    var endTime: String? = null

    private val presenter: RecordPresenter by lazy {
        RecordPresenter(RecordModel(), this, SchedulerProvider.getInstatnce()!!)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_record
    }

    override fun initView() {
        var data: User? = MyApplication.instance!!.user
        if (data != null) {
            tv_username.text = data.catName
            tv_h.text = data.totalHValue.toString()
            tv_m.text = data.totalCoin.toString()
        }

        vp_chart.run {
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            rb_h.isChecked = true
                            rb_button_h.isChecked = true

                            lv_record.adapter = RecordAdapter(hRecordList!!.detail)

                        }
                        1 -> {
                            rb_zuan.isChecked = true
                            rb_button_m.isChecked = true

                            lv_record.adapter = RecordAdapter(mRecordList!!.detail)
                        }
                    }
                }

            })
            offscreenPageLimit = 2
        }

        rb_button_h.setOnClickListener {
            vp_chart.currentItem = 0
        }

        rb_button_m.setOnClickListener {
            vp_chart.currentItem = 1
        }

        iv_date.setOnClickListener {
            val beginTimes: List<String> = beginTime!!.split("-")
            val endTimes: List<String> = endTime!!.split("-")
            showDateDialog(beginTimes[0], beginTimes[1], beginTimes[2],
                    endTimes[0], endTimes[1], endTimes[2])
        }

        rb_button_h.isChecked = true

        rb_button_h.isEnabled = false
        rb_button_m.isEnabled = false
        iv_land.visibility = View.INVISIBLE

        iv_return.setOnClickListener {
            finish()
        }

        iv_land.setOnClickListener {

            MyApplication.instance!!.beginTime = beginTime
            MyApplication.instance!!.endTime = endTime
            MyApplication.instance!!.hRecordList = hRecordList
            MyApplication.instance!!.mRecordList = mRecordList

            startActivity<LandRecordActivity>()
        }

        beginTime = Dateutils.getBeforeWeekDate()
        endTime = Dateutils.getNowDate()
        starQuery()
    }

    private fun starQuery() {
        MLog.log("开始查询")
        presenter.queryHRecord(beginTime!!, endTime!!)
    }

    override fun queryHRecordSuccess(data: RecordList) {
        hRecordList = data
        presenter.queryMRecord(Dateutils.getBeforeWeekDate(), Dateutils.getNowDate())
    }

    override fun queryMRecordSuccess(data: RecordList) {
        mRecordList = data
        rb_button_h.isEnabled = true
        rb_button_m.isEnabled = true
        iv_land.visibility = View.VISIBLE

        showVpRecord()
    }

    private fun showVpRecord() {

        val datas = listOf(hRecordList!!, mRecordList!!)

        vp_chart.adapter = object : BaseViewPagerAdapter<RecordList>(datas) {


            override fun bindLayoutId(position: Int): Int {
                return R.layout.vp_item_record_chart
            }

            override fun initView(data: RecordList?, viewHolder: BaseViewHolder?) {

                viewHolder!!.setText(R.id.tv_date, "$beginTime  -  $endTime")

                val yLine: YLineChartView = viewHolder.findViewById(R.id.yLine)
                val xLine: XLineChartView = viewHolder.findViewById(R.id.xLine)

                yLine.setDatas(data!!.income, data.outcome)
                xLine.setDatas(data.income, data.outcome)
            }

        }

        lv_record.adapter = RecordAdapter(hRecordList!!.detail)
    }


    fun showDateDialog(year1: String, month1: String, day1: String,
                       year2: String, month2: String, day2: String) {
        val dialog = Dialog(this, R.style.LoadingDialog)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_date)
        dialog.window!!.attributes.gravity = Gravity.BOTTOM
        val screenWidth = windowManager.defaultDisplay.width
        dialog.window!!.attributes.width = screenWidth

        val tpv_year1: TimePickerView = dialog.findViewById(R.id.tpv_year1)
        val tpv_month1: TimePickerView = dialog.findViewById(R.id.tpv_month1)
        val tpv_day1: TimePickerView = dialog.findViewById(R.id.tpv_day1)

        val tpv_year2: TimePickerView = dialog.findViewById(R.id.tpv_year2)
        val tpv_month2: TimePickerView = dialog.findViewById(R.id.tpv_month2)
        val tpv_day2: TimePickerView = dialog.findViewById(R.id.tpv_day2)

        (dialog.findViewById(R.id.tv_confirm) as View).setOnClickListener(
                View.OnClickListener {


                    val beginTime: String = tpv_year1.selectedText + "-" + tpv_month1.selectedText + "-" + tpv_day1.selectedText
                    val endTime: String = tpv_year2.selectedText + "-" + tpv_month2.selectedText + "-" + tpv_day2.selectedText

                    if (beginTime > endTime) {
                        toast("结束日期不能小于开始日期")
                    } else {
                        dialog.dismiss()
                        this@RecordActivity.beginTime = beginTime
                        this@RecordActivity.endTime = endTime
                        starQuery()
                    }

                })

        initTimePicker(tpv_year1, tpv_month1, tpv_day1, year1, month1, day1)
        initTimePicker(tpv_year2, tpv_month2, tpv_day2, year2, month2, day2)

        dialog.show()
    }

    private fun initTimePicker(tpv_year: TimePickerView, tpv_month: TimePickerView, tpv_day: TimePickerView,
                               year: String, month: String, day: String) {
        tpv_year.setOnSelectListener { text ->
            MLog.log("选中" + text + tpv_month.selectedText)
            val dayTemp = tpv_day.selectedText
            setDayData(tpv_day, text, tpv_month.selectedText)
            val result = tpv_day.setSelected(dayTemp)
            if (result == -1) {
                tpv_day.setSelected(tpv_day.dataSize - 1)
            }
        }
        tpv_month.setOnSelectListener { text ->
            MLog.log("选中" + tpv_year.selectedText + text)
            val dayTemp = tpv_day.selectedText
            setDayData(tpv_day, tpv_year.selectedText, text)
            val result = tpv_day.setSelected(dayTemp)
            if (result == -1) {
                tpv_day.setSelected(tpv_day.dataSize - 1)
            }
        }

        initTimePickerView(tpv_year)
        initTimePickerView(tpv_month)
        initTimePickerView(tpv_day)

        setYearData(tpv_year)
        setMonthData(tpv_month)
        setDayData(tpv_day, year, month)

        tpv_year.setSelected(year)
        tpv_month.setSelected(month)
        tpv_day.setSelected(day)
    }

    private fun initTimePickerView(timePickerView: TimePickerView) {
        timePickerView.setLayerType(View.LAYER_TYPE_HARDWARE, null) // 手机硬件加速
        timePickerView.setSelectTextColor(Color.parseColor("#ffffff"))
        timePickerView.setTextColor(Color.parseColor("#5f616f"))
        timePickerView.setNotCycle(true)
        timePickerView.setTextSizeSacle(10f)
    }


    private fun setYearData(tpv_year: TimePickerView) {
        val days = ArrayList<String>()
        val minYear = Calendar.getInstance().get(Calendar.YEAR) - 2018
        for (j in 0..minYear) {
            days.add((Calendar.getInstance().get(Calendar.YEAR) + j).toString() + "")
        }
        tpv_year.setData(days)
    }

    private fun setMonthData(tpv_month: TimePickerView) {
        val days = ArrayList<String>()
        for (j in 1..12) {
            days.add(if (j < 10) "0$j" else "" + j)
        }
        tpv_month.setData(days)
    }

    private fun setDayData(tpv_day: TimePickerView, year: String, month: String) {
        val days = ArrayList<String>()
        val maxDay = getMaxDay(Integer.parseInt(year), Integer.parseInt(month))
        for (j in 1..maxDay) {
            days.add(if (j < 10) "0$j" else "" + j)
        }
        tpv_day.setData(days)
    }

    /**
     * 获取该年该月多少天
     *
     * @param year
     * @param month
     * @return
     */
    private fun getMaxDay(year: Int, month: Int): Int {
        val calendar = GregorianCalendar(year, month, 0)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}