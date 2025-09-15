package com.azabost.quest.resources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidStringResources @Inject constructor(
    @param:ApplicationContext
    private val context: Context,
): StringResources {

    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }
}