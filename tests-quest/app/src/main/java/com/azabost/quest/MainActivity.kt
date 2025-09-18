package com.azabost.quest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.azabost.quest.users.ui.UsersActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, UsersActivity::class.java))
        finish()
    }
}