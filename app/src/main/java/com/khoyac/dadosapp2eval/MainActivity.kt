package com.khoyac.dadosapp2eval

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.khoyac.dadosapp2eval.DadosApp2EvalApp.Companion.uid
import com.khoyac.dadosapp2eval.databinding.ActivityMainBinding
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.random.Random

enum class ProviderType {
    BASIC
}
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        val rtDb = FirebaseDatabase.getInstance("https://dadosapp-d394b-default-rtdb.europe-west1.firebasedatabase.app").reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTest.setOnClickListener {
            rtDb.child("test").child("id").setValue(Random.nextInt(1, 200).toString())
            rtDb.child("test").child("nombre").setValue("Test: " + Random.nextInt(1, 200).toString())
        }

        binding.btnCreate.setOnClickListener {
            buscarPartida()
        }

        rtDb.child("test").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvId.text = snapshot.child("id").value.toString()
                binding.tvName.text = snapshot.child("nombre").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun buscarPartida() {
        rtDb.child("partidas")
            .child(binding.etIdPartida.text.toString())
            .get().addOnSuccessListener {
                if (it.value == null) {
                    val id = generatePartidaId()
                    rtDb.child("partidas")
                        .child(id)
                        .child("P1").setValue(uid)
                    rtDb.child("partidas")
                        .child(id)
                        .child("estado").setValue(0)
                    rtDb.child("partidas")
                        .child(id)
                        .child("id").setValue(id)

                } else {
                    rtDb.child("partidas")
                        .child(binding.etIdPartida.text.toString())
                        .child("P2").setValue(uid)
                    rtDb.child("partidas")
                        .child(binding.etIdPartida.text.toString())
                        .child("estado").setValue(1)
                }

                val battleIntent = Intent(this, BattleActivity::class.java).apply {
                    putExtra("idPartida", binding.etIdPartida.text.toString())
                }
                startActivity(battleIntent)

            }.addOnFailureListener{
                Toast.makeText(this, "ID VACIA", Toast.LENGTH_SHORT).show()
            }



    }

    private fun generatePartidaId(): String {
        return "ID" + Random.nextInt(1000, 9999).toString()
    }

//    private fun buscarPartida() {
//        rtDb.child("jugadores_espera").get().addOnSuccessListener {
//            if (it.childrenCount != 0L) {
//                val partidaMap: HashMap<String, String?> = hashMapOf(
//                    "jugador1" to uid,
//                    "jugador2" to it.value.toString()
//                )
//                rtDb.child("partidas").child("test").setValue(partidaMap)
//                rtDb.child("jugador_espera").child(it.value.toString()).removeValue()
//            } else {
//                rtDb.child("jugadores_espera").child(uid.toString())
//            }
//        }
//    }


}