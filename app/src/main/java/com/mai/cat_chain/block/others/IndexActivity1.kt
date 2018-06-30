package com.mai.cat_chain.block.others

import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.R
import com.mai.cat_chain.block.main.MainActivity
import com.mai.cat_chain.block.task.TaskActivity
import kotlinx.android.synthetic.main.activity_cheats.*
import kotlinx.android.synthetic.main.activity_index.*
import org.jetbrains.anko.startActivity

/**
 * Created by maijuntian on 2018/6/13.
 */
class IndexActivity1 : BaseActivity() {

    var index: Int = 0
    var imgs: MutableList<Int> = mutableListOf(R.mipmap.index1_1, R.mipmap.index1_2, R.mipmap.index1_3, R.mipmap.index1_4, R.mipmap.index1_5, R.mipmap.index1_6)

    override fun setLayoutId(): Int {
        return R.layout.activity_index
    }

    override fun initView() {
        iv_img.setImageResource(imgs[index])

        iv_img.setOnClickListener {
            index++
            if (index >= imgs.size) {
                startActivity<MainActivity>()
                finish()
            } else {
                iv_img.setImageResource(imgs[index])
            }
        }
    }

}