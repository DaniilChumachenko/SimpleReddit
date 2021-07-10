package com.chumachenko.simpleReddit.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chumachenko.simpleReddit.presentation.viewmodel.TopRedditViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TopRedditViewModel::class)
    internal abstract fun bindFirstViewModel(viewModel: TopRedditViewModel): ViewModel


}