package com.example.hypekicks

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.hypekicks.databinding.ActivityStorefrontBinding
import com.google.firebase.firestore.FirebaseFirestore

class StorefrontActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorefrontBinding
    private val db = FirebaseFirestore.getInstance()
    private val sneakerList = mutableListOf<Sneaker>()
    private lateinit var adapter: SneakerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorefrontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SneakerAdapter(this, sneakerList)
        binding.gridView.adapter = adapter

        loadData()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText ?: "")
                return true
            }
        })

        binding.adminBtn.setOnClickListener {
            startActivity(Intent(this, AdminPanelActivity::class.java))
        }

        binding.gridView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("sneaker", sneakerList[position])
            startActivity(intent)
        }
    }

    private fun loadData() {
        db.collection("sneakers").get().addOnSuccessListener {
            sneakerList.clear()
            for (doc in it) {
                val sneaker = doc.toObject(Sneaker::class.java).copy(id = doc.id)
                sneakerList.add(sneaker)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun filter(text: String) {
        val filtered = sneakerList.filter {
            it.modelName.lowercase().contains(text.lowercase())
        }
        adapter.updateList(filtered)
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()
        loadData()
    }
}