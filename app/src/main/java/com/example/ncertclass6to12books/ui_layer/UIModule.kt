package com.example.ncertclass6to12books.ui_layer

import com.example.ncertclass6to12books.data_layer.repoImp.RepoImp
import com.example.ncertclass6to12books.domain_layer.repo.Repo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UIModule {
    @Provides
    @Singleton
    fun provideRepo(firestore: FirebaseFirestore): Repo{
        return RepoImp(firestore)
    }
}