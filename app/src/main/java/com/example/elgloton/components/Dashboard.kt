package com.example.elgloton.components

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.R
import com.example.elgloton.api.APIClientCard
import com.example.elgloton.api.APIClientPay
import com.example.elgloton.api.adapters.FoodCardAdapter
import com.example.elgloton.api.models.dashboard.FoodCardItem
import com.example.elgloton.components.dialogs.DialogPay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Dashboard : Fragment() {
    private lateinit var recyclerView: RecyclerView
    lateinit var totalPayButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false)

        totalPayButton = dashboardView.findViewById(R.id.totalPayButton) // Obtener referencia al botón


        recyclerView = dashboardView.findViewById(R.id.recyclerViewCard)
        APIClientCard.init(requireContext())


        loadFoodCardData()
        return dashboardView
    }

    private fun loadFoodCardData() {
        val apiService = APIClientCard.apiService

        apiService.getFoodCardItems(APIClientCard.getAccessToken() ?: "")
            .enqueue(object : Callback<List<FoodCardItem>> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<List<FoodCardItem>>, response: Response<List<FoodCardItem>>) {
                    val foodCardItems = response.body() ?: emptyList()
                    if (response.isSuccessful) {
                        val orderList = foodCardItems.flatMap { it.order }.toMutableList()

                        // Calcular el precio total
                        val totalPrice = orderList.sumOf { it.food.price.toDouble() * it.quantity }

                        // Formatear el resultado con dos decimales
                        val formattedPrice = String.format("%.2f", totalPrice)

                        // Establecer el texto del botón con el resultado formateado
                        totalPayButton.text = "Total: S/. $formattedPrice"
                        totalPayButton.setOnClickListener {
                            val idCarro = foodCardItems.map { it.id }[0]
                            DialogPay.showNumberInputDialog(requireContext()) { enterTable ->
                                APIClientPay.init(requireContext(), idCarro, enterTable, totalPrice.toString())
                            }
                        }

                        // Imprimir el resultado en la consola de registro
                        Log.d("TotalPrice", "Total => $totalPrice")

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