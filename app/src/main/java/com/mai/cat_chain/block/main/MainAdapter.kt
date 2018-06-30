package com.mai.cat_chain.block.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by maijuntian on 2018/6/19.
 */
class MainAdapter : FragmentPagerAdapter {


    var fragments: MutableList<Fragment>? = null

    constructor(fm: FragmentManager?, fragments: MutableList<Fragment>) : super(fm) {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment {
        return fragments!!.get(position)
    }

    override fun getCount(): Int {
        return fragments!!.size
    }


}