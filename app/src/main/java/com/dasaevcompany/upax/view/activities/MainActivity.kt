package com.dasaevcompany.upax.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dasaevcompany.upax.databinding.ActivityMainBinding
import com.dasaevcompany.upax.utilities.ConnectivityUtil
import com.dasaevcompany.upax.viewmodel.MovieDBViewModel
import com.dasaevcompany.upax.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val movieService : MovieViewModel by viewModels()
    private val database : MovieDBViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        observers()

    }

    private fun initData() {
        /** Create function of navigation **/
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as NavHostFragment
        NavigationUI.setupWithNavController(binding.menuNavigation, navHostFragment.navController)

        database.getMovies()
        internet()
    }

    private fun observers() {

        database.listMovie.observe(this){
            if (it.isEmpty() && internet()){
                movieService.getMovie()
            }
        }

        movieService.movies.observe(this){
            if (it.isNotEmpty()){
                database.addListMovies(it)
            }
        }
    }

    private fun internet():Boolean{
        return ConnectivityUtil().internet(this)
    }
}