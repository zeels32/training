package com.azabost.quest.time

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DefaultNowProvider @Inject constructor() : NowProvider {
    override fun now(): Long = System.currentTimeMillis()
}
