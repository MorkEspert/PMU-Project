package com.projekt.pmu_project


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ViewAccountActivity : AppCompatActivity() {

    private lateinit var tvAccountInfo: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_account)

        tvAccountInfo = findViewById(R.id.tvAccountInfo)

        auth = FirebaseAuth.getInstance()


        val currentUser = auth.currentUser
        val email = currentUser?.email
        val displayName = currentUser?.displayName


        val accountInfo = "Email: $email\nName: $displayName"
        tvAccountInfo.text = accountInfo
    }
}
