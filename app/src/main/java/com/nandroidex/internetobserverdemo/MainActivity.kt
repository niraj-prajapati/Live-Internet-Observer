package com.nandroidex.internetobserverdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.nandroidex.internetobserve.NetworkConnectivityObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkConnectivityObserver.internetAvailable.observe(this, Observer {
            it?.let {
                tvStatus.text = if (it) "Connected" else "Disconnected"
                tvStatus.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        if (it) R.color.connected else R.color.disconnected
                    )
                )
            }
        })
    }
}
