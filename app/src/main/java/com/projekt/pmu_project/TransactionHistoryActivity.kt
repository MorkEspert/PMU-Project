package com.projekt.pmu_project


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TransactionHistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionList: MutableList<String>

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        recyclerView = findViewById(R.id.recyclerViewTransactions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        transactionList = mutableListOf()

        val userId = auth.currentUser?.uid

        if (userId != null) {
            firestore.collection("transactions")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val transactions = documentSnapshot.data?.values
                    if (transactions != null) {
                        transactionList.addAll(transactions as List<String>)
                    }

                    transactionAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                }
        }
        transactionAdapter = TransactionAdapter(transactionList)
        recyclerView.adapter = transactionAdapter
    }
}
