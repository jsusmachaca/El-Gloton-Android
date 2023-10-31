package com.example.elgloton.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.R
import com.example.elgloton.api.APIClientCard
import com.example.elgloton.api.adapters.FoodCardAdapter
import com.example.elgloton.api.models.dashboard.FoodCardItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Dashboard : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = dashboardView.findViewById(R.id.recyclerViewCard)
        APIClientCard.init(requireContext())


        loadFoodCardData()
        return dashboardView
    }

    private fun loadFoodCardData() {
        val apiService = APIClientCard.apiService

        apiService.getFoodCardItems(APIClientCard.getAccessToken() ?: "")
            .enqueue(object : Callback<List<FoodCardItem>> {
                override fun onResponse(call: Call<List<FoodCardItem>>, response: Response<List<FoodCardItem>>) {
                    val foodCardItems = response.body() ?: emptyList()
                    if (response.isSuccessful) {
                        val orderList = foodCardItems.flatMap { it.order }.toMutableList()

                        val foodCardAdapter = FoodCardAdapter(requireContext(), orderList)
                        recyclerView.adapter = foodCardAdapter
                    }
                }

                override fun onFailure(call: Call<List<FoodCardItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error de red", Toast.LENGTH_SHORT).show()
                }
        })
    }

}