package com.azabost.quest.resources

interface StringResources {
    fun getString(id: Int): String
    fun getString(id: Int, vararg args: Any): String
}