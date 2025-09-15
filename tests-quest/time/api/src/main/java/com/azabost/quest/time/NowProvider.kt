package com.azabost.quest.time

interface NowProvider {
    fun now(): Long
}