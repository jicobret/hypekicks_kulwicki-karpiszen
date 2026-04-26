package com.example.hypekicks

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.hypekicks.databinding.ActivityAdminPanelBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPanelBinding
    private val db = FirebaseFirestore.getInstance()
    private val list = mutableListOf<Sneaker>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.listView.adapter = adapter

        loadData()

        binding.addBtn.setOnClickListener {
            val sneaker = hashMapOf(
                "brand" to binding.brandEt.text.toString(),
                "modelName" to binding.modelEt.text.toString(),
                "releaseYear" to binding.yearEt.text.toString().toInt(),
                "resellPrice" to binding.priceEt.text.toString().toDouble(),
                "imageUrl" to binding.imageEt.text.toString()
            )

            db.collection("sneakers").add(sneaker).addOnSuccessListener {
                loadData()
            }
        }

        binding.listView.setOnItemLongClickListener { _, _, position, _ ->
            val id = list[position].id
            db.collection("sneakers").document(id).delete()
            loadData()
            true
        }
    }

    private fun loadData() {
        db.collection("sneakers").get().addOnSuccessListener {
            list.clear()
            val names = mutableListOf<String>()

            for (doc in it) {
                val sneaker = doc.toObject(Sneaker::class.java).copy(id = doc.id)
                list.add(sneaker)
                names.add("${sneaker.brand} ${sneaker.modelName}")
            }

            adapter.clear()
            adapter.addAll(names)
        }
    }
}