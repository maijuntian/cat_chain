package com.mai.cat_chain.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by maijuntian on 2018/6/11.
 */
@Suppress("UsePropertyAccessSyntax")
class XiaoBoTextView : TextView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0) {
        setIncludeFontPadding(false)
        var typeface = Typeface.createFromAsset(context!!.getAssets(), "HYXIAOBOZHEZHITIJ.TTF")
        this.setTypeface(typeface)
    }
}