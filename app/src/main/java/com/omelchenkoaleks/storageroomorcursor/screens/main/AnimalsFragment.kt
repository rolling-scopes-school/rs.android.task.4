package com.omelchenkoaleks.storageroomorcursor.screens.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omelchenkoaleks.storageroomorcursor.R
import com.omelchenkoaleks.storageroomorcursor.adapter.AnimalsAdapter
import com.omelchenkoaleks.storageroomorcursor.databinding.FragmentAnimalsBinding
import com.omelchenkoaleks.storageroomorcursor.listener.ItemClickListener
import com.omelchenkoaleks.storageroomorcursor.model.Animal

class AnimalsFragment : Fragment() {

    private var _binding: FragmentAnimalsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: AnimalsViewModel by viewModels()

    private lateinit var recycler: RecyclerView
    private var adapter: AnimalsAdapter? = null

    private var fab: FloatingActionButton? = null

    private var callBack: CallBack? = null
    private var itemClickListener: ItemClickListener? = null

    interface CallBack {
        fun openAddAnimalFragment()
        fun openSortFragment()
        fun openSwitchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = context as CallBack
        itemClickListener = context as ItemClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalsBinding.inflate(inflater, container, false)
        val view = binding.root

        fab = binding.btnAddAnimal
        recycler = binding.recyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        adapter = itemClickListener?.let { AnimalsAdapter(it) }
        recycler.adapter = adapter
        fab?.setOnClickListener {
            callBack?.openAddAnimalFragment()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val isName = preference.getBoolean(context?.getString(R.string.key_name), false)
        val isAge = preference.getBoolean(context?.getString(R.string.key_age), false)
        val isBreed = preference.getBoolean(context?.getString(R.string.key_breed), false)

        val order = when {
            isName -> "name"
            isAge -> "age"
            isBreed -> "breed"
            else -> "animals"
        }

        viewModel.sortBy(order)
        viewModel.animals.observe(viewLifecycleOwner, { animals -> animals?.let { updateUI(it) } })
    }

    private fun updateUI(animals: List<Animal>) {
        adapter?.submitList(animals)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_settings -> {
                callBack?.openSortFragment()
                true
            }
            R.id.change_database -> {
                callBack?.openSwitchFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}