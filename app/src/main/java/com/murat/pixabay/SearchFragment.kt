package com.murat.pixabay

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murat.pixabay.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    var pixaAdapter = PixaAdapter()
    private var loading = true
    private var mLayoutManager: LinearLayoutManager? = null
    private var page = 1
    private var isSearch = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicker()
        setupRecycler()
        setupPagination()

    }

    private fun setupRecycler() {
        mLayoutManager = LinearLayoutManager(requireContext())
        binding.recycler.layoutManager = mLayoutManager
        binding.recycler.adapter = pixaAdapter
        binding.recycler.itemAnimator = DefaultItemAnimator()
        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL))
        binding.recycler.adapter = pixaAdapter
    }

    private fun initClicker() {

        with(binding) {
            btnSearch.setOnClickListener {
                isSearch = true
                page = 1
                doRequest()
            }
        }
    }

    private fun doRequest() {
        loading = true
        binding.loader.isVisible = true
        RetrofitService().api.searchImage(keyWord = binding.etSearch.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                    if (response.isSuccessful) {
                        response.body()?.hits?.let {
                            pixaAdapter.fillList(it, isSearch)

                            isSearch = false
                        }
                        loading = false
                        binding.loader.isVisible = false

                    }

                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    loading = false
                    binding.loader.isVisible = false
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun setupPagination() {
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisiblesItem = mLayoutManager!!.findLastVisibleItemPosition()
                if (!loading) {
                    if (lastVisiblesItem == pixaAdapter.itemCount - 1) {
                        page++
                        doRequest()
                    }
                }
            }
        })
    }


}