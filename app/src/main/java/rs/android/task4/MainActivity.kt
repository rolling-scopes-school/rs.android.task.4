package rs.android.task4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
//import rs.android.task4.db.DatabaseHelper
//import rs.android.task4.db.dao.Animal
import rs.android.task4.fragments.AddFragment
import rs.android.task4.fragments.AnimalsFragment
import rs.android.task4.fragments.SortFragment
import rs.android.task4.repository.Animal

class MainActivity : AppCompatActivity(), AnimalsFragment.Callbacks, SortFragment.Callbacks, AddFragment.Callbacks {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onFragmentMenuSortClick() {
        navController.navigate(R.id.action_animalsFragment_to_sortFragment)
    }

    override fun onFragmentAddClick() {
        navController.navigate(R.id.action_animalsFragment_to_addFragment)
    }

    override fun onFragmentAddToChangeClick(item: Animal) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_ANIMAL, item)
        navController.navigate(R.id.action_animalsFragment_to_addFragment, bundle)
    }

    override fun onFragmentAnimalsClick() {
        navController.navigate(R.id.action_sortFragment_to_animalsFragment)
    }

    override fun onBackFragmentAnimals() {
        navController.navigate(R.id.action_addFragment_to_animalsFragment)
    }
}
