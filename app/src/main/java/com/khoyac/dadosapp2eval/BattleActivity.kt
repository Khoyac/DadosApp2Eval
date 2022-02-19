package com.khoyac.dadosapp2eval

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.type.LatLng
import com.khoyac.dadosapp2eval.MainActivity.Companion.rtDb
import com.khoyac.dadosapp2eval.databinding.ActivityBattleBinding

class BattleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBattleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAds()

        val bundle : Bundle? ? = intent.extras
        val idPartida : String? = bundle?.getString("idPartida")

        rtDb.child("partida").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvIdPartida.text = snapshot.child("partidas").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun initAds() {
        val adRequest = AdRequest.Builder().build()
        //binding.banner.loadAd(adRequest)
    }


}

