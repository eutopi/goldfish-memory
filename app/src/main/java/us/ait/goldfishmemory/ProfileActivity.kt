package us.ait.goldfishmemory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import us.ait.goldfishmemory.data.Player

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        txtUsername.text = FirebaseAuth.getInstance().currentUser!!.displayName
        setPlayerStats()

    }

    private fun setPlayerStats() {
        FirebaseFirestore.getInstance().collection("players")
            .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val player = document.toObject(Player::class.java)
                    tvShortestTime.text = player!!.bestTime
                    tvGamesPlayed.text = player!!.gamesPlayed
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@ProfileActivity, "Error retrieving player", Toast.LENGTH_LONG).show()
            }
    }
}
