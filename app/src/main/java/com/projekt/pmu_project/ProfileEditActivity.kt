package com.projekt.pmu_project


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etName: EditText
    private lateinit var etSurname: EditText
    private lateinit var etOib: EditText
    private lateinit var etDateOfBirth: EditText
    private lateinit var btnSaveChanges: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        etId = findViewById(R.id.etId)
        etName = findViewById(R.id.etName)
        etSurname = findViewById(R.id.etSurname)
        etOib = findViewById(R.id.etOib)
        etDateOfBirth = findViewById(R.id.etDateOfBirth)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("users").child(auth.currentUser!!.uid)


        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.value as? Map<String, Any>
                if (userData != null) {
                    etId.setText(auth.currentUser!!.uid)
                    etName.setText(auth.currentUser!!.displayName)
                    etSurname.setText(userData["surname"].toString())
                    etOib.setText(userData["oib"].toString())
                    etDateOfBirth.setText(userData["dateOfBirth"].toString())
                } else {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        btnSaveChanges.setOnClickListener {
            val newName = etName.text.toString()
            val newSurname = etSurname.text.toString()
            val newOib = etOib.text.toString()
            val newDateOfBirth = etDateOfBirth.text.toString()


            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            auth.currentUser?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val userData = hashMapOf(
                            "surname" to newSurname,
                            "oib" to newOib,
                            "dateOfBirth" to newDateOfBirth
                        )
                        userRef.setValue(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Failed to update profile: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
