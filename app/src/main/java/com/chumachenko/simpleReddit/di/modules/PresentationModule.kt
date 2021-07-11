package com.chumachenko.simpleReddit.di.modules

import com.chumachenko.simpleReddit.di.ActivityBuilder
import com.chumachenko.simpleReddit.di.viewModel.ViewModelModule
import dagger.Module

@Module(
    includes = [
        ActivityBuilder::class,
        ViewModelModule::class
    ]
)
abstract class PresentationModule