package com.chc.access.utils

import android.content.Context

object PreferenceManager {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_FIRST_OPEN = "has_launched_before"

    // 获取是否是第一次打开
    fun isFirstLaunch(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return !prefs.contains(KEY_FIRST_OPEN)
    }

    // 设置已完成首次启动标记
    fun setFirstLaunchComplete(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_FIRST_OPEN, true)
            .apply()
    }
}