package com.omelchenkoaleks.storageroomorcursor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.omelchenkoaleks.storageroomorcursor.listener.ItemClickListener
import com.omelchenkoaleks.storageroomorcursor.screens.add.AddAnimalFragment
import com.omelchenkoaleks.storageroomorcursor.screens.main.AnimalsFragment

class MainActivity : AppCompatActivity(R.layout.activity_main),
    AnimalsFragment.CallBack, AddAnimalFragment.CallBack, ItemClickListener {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = this.findNavController(R.id.fragment_container)
    }

    override fun openAddAnimalFragment() {
        navController?.navigate(R.id.action_animalsFragment_to_addAnimalFragment)
    }

    override fun openSortFragment() {
        navController?.navigate(R.id.action_animalsFragment_to_sortFragment)
    }

    override fun openSwitchFragment() {
        navController?.navigate(R.id.action_animalsFragment_to_switchFragment)
    }

    override fun openAnimalsFragment() {
        navController?.popBackStack()
    }

    override fun onItemClick(id: Int) {
        navController?.navigate(
            R.id.action_animalsFragment_to_addAnimalFragment,
            bundleOf("id" to id)
        )
    }

}