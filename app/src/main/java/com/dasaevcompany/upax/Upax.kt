package com.dasaevcompany.upax

import android.app.Application
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dasaevcompany.upax.viewmodel.MovieViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Upax: Application()