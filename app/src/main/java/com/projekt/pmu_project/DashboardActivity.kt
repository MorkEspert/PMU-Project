package com.projekt.pmu_project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projekt.pmu_project.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProfileEdit.setOnClickListener {
            startActivity(Intent(this, ProfileEditActivity::class.java))
        }

        binding.btnViewAccount.setOnClickListener {
            startActivity(Intent(this, ViewAccountActivity::class.java))
        }

        binding.btnViewHistory.setOnClickListener {
            startActivity(Intent(this, TransactionHistoryActivity::class.java))
        }
    }
}
