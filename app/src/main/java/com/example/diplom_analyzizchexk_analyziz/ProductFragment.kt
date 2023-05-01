package com.example.diplom_analyzizchexk_analyziz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplom_analyzizchexk_analyziz.adapter.ProductAdapter
import com.example.diplom_analyzizchexk_analyziz.databinding.FragmentProductsBinding
import com.example.diplom_analyzizchexk_analyziz.retrofit.MainAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductFragment : Fragment() {
    private lateinit var adapter: ProductAdapter
    private lateinit var binding: FragmentProductsBinding
    private lateinit var mainApi: MainAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        initRcView()
        CoroutineScope(Dispatchers.IO).launch{
            val list=mainApi.getProduct()
            
           requireActivity().runOnUiThread {
                binding.apply {
                    adapter.submitList(list)
                  
                }
            }
        }

    }
    private fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        mainApi = retrofit.create(MainAPI::class.java)
    }

    private fun initRcView() = with(binding){
        adapter = ProductAdapter()
        reView.layoutManager = LinearLayoutManager(context)
        reView.adapter = adapter
    }


}