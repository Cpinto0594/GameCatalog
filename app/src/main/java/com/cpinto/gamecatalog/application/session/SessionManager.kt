package com.cpinto.gamecatalog.application.session

import android.content.Context

object SessionManager {

    const val FIRST_TIME_APP = "FIRST_TIME_APP"
    const val isFirstTimeApplication = "isFirstTimeApplication"

    fun setFistTimeApplication(value: Boolean, context: Context): Boolean {
        val editor = context.getSharedPreferences(FIRST_TIME_APP, Context.MODE_PRIVATE).edit()
        editor.putBoolean(isFirstTimeApplication, value)
        editor.apply()
        return value
    }

    fun isFirstTimeApplication(context: Context): Boolean =
        context.getSharedPreferences(FIRST_TIME_APP, Context.MODE_PRIVATE).getBoolean(
            isFirstTimeApplication, true
        )


}