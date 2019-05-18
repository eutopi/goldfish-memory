package us.ait.goldfishmemory

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.new_icon_dialog.view.*
import us.ait.goldfishmemory.data.Player

class IconDialog : DialogFragment() {

    private lateinit var avatar1: ImageView
    private lateinit var avatar2: ImageView
    private lateinit var avatar3: ImageView
    private lateinit var avatar4: ImageView
    private lateinit var avatar5: ImageView
    private lateinit var avatar6: ImageView
    private lateinit var docID: String
    private lateinit var player: Player

    // for interacting with the activity
    interface ProfileHandler {
        fun updateIcon(url: String)
    }

    private lateinit var profileHandler: ProfileHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ProfileHandler) {
            profileHandler = context
        } else {
            throw RuntimeException("The activity does not implement the ProfileHandler")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        getPlayer()

        builder.setTitle("Select an icon")

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.new_icon_dialog, null
        )
        avatar1 = rootView.ivAvatar1
        avatar2 = rootView.ivAvatar2
        avatar3 = rootView.ivAvatar3
        avatar4 = rootView.ivAvatar4
        avatar5 = rootView.ivAvatar5
        avatar6 = rootView.ivAvatar6
        builder.setView(rootView)

        return builder.create()
    }

    private fun getPlayer() {
        FirebaseFirestore.getInstance().collection("players")
            .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docID = document.id
                    player = document.toObject(Player::class.java)
                }
            }
    }

    override fun onResume() {
        super.onResume()

        avatar1.setOnClickListener {
            player.icon = "https://firebasestorage.googleapis.com/v0/b/goldfishmemory-81dc4.appspot.com/o/avatar1.png?alt=media&token=bffebbdc-3080-410b-8532-315e70b7192a"
            handleIconChange(player.icon)
        }
        avatar2.setOnClickListener {
            player.icon = "https://firebasestorage.googleapis.com/v0/b/goldfishmemory-81dc4.appspot.com/o/avatar2.png?alt=media&token=def033df-56dc-4dd4-94bb-edd6a634da4e"
            handleIconChange(player.icon)
        }
        avatar3.setOnClickListener {
            player.icon = "https://firebasestorage.googleapis.com/v0/b/goldfishmemory-81dc4.appspot.com/o/avatar3.png?alt=media&token=652dfb54-035b-41a9-af4d-4c48ab40b7e7"
            handleIconChange(player.icon)
        }
        avatar4.setOnClickListener {
            player.icon = "https://firebasestorage.googleapis.com/v0/b/goldfishmemory-81dc4.appspot.com/o/avatar4.png?alt=media&token=4a6cf77f-b2c3-4f7a-aa49-54dc74d42a8f"
            handleIconChange(player.icon)
        }
        avatar5.setOnClickListener {
            player.icon = "https://firebasestorage.googleapis.com/v0/b/goldfishmemory-81dc4.appspot.com/o/avatar5.png?alt=media&token=dd73713a-3931-499c-a383-a92447a03593"
            handleIconChange(player.icon)
        }
        avatar6.setOnClickListener {
            player.icon ="https://firebasestorage.googleapis.com/v0/b/goldfishmemory-81dc4.appspot.com/o/avatar6.png?alt=media&token=8435c49f-cd99-45bd-8528-8aeea60faec6"
            handleIconChange(player.icon)
        }
    }

    private fun handleIconChange(url: String) {
        FirebaseFirestore.getInstance().collection("players").document(docID)
            .set(player)
        profileHandler.updateIcon(url)
        dialog.dismiss()
    }

}