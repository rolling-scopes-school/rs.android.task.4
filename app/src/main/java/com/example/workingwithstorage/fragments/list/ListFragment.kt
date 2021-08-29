package com.example.workingwithstorage.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.workingwithstorage.R
import com.example.workingwithstorage.databinding.FragmentListBinding
import com.example.workingwithstorage.viewmodel.FilmViewModel
import kotlinx.coroutines.InternalCoroutinesApi


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = requireNotNull(_binding)
    @InternalCoroutinesApi
    private lateinit var mFilmViewModel: FilmViewModel
    private val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //RecyclerView
        binding.recycler.adapter = adapter

        //UserViewModel
        mFilmViewModel = ViewModelProvider(this).get(FilmViewModel::class.java)
        mFilmViewModel.readAllData.observe(viewLifecycleOwner, Observer {user ->
            adapter.setData(user)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}