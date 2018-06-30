package com.mai.cat_chain.utils

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.mai.cat_chain.MyApplication

/**
 * Created by maijuntian on 2018/6/30.
 */
object SoundUtils {

    fun playSound(rawId: Int) {
        val soundPool: SoundPool
        soundPool = if (Build.VERSION.SDK_INT >= 21) {
            val builder = SoundPool.Builder()
            //传入音频的数量
            builder.setMaxStreams(1)
            //AudioAttributes是一个封装音频各种属性的类
            val attrBuilder = AudioAttributes.Builder()
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC)
            builder.setAudioAttributes(attrBuilder.build())
            builder.build()
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            SoundPool(1, AudioManager.STREAM_SYSTEM, 5)
        }
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        soundPool.load(MyApplication.instance!!.applicationContext, rawId, 1)
        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status -> soundPool.play(1, 1f, 1f, 0, 0, 1f) }
    }
}
