package com.mai.cat_chain.block.others

import com.mai.cat_chain.base.BaseActivity
import com.mai.cat_chain.R
import com.mai.cat_chain.block.task.TaskActivity
import kotlinx.android.synthetic.main.activity_cheats.*
import org.jetbrains.anko.startActivity

/**
 * Created by maijuntian on 2018/6/13.
 */
class CatCheatsActivity : BaseActivity() {


    override fun setLayoutId(): Int {
        return R.layout.activity_cheats
    }

    override fun initView() {
        bt_speed_up.setOnClickListener { v ->
            startActivity<TaskActivity>()
        }

        iv_return.setOnClickListener { v ->
            finish()
        }
    }

}