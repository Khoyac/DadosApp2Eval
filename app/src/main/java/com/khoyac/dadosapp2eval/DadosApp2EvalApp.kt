package com.khoyac.dadosapp2eval

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.FirebaseDatabase

class DadosApp2EvalApp:Application() {
    companion object {
        var uid: String? = null
    }

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}
