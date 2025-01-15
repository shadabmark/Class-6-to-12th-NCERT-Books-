package com.example.ncertclass6to12books.domain_layer.repo

import com.example.ncertclass6to12books.common.ResultState
import com.example.ncertclass6to12books.domain_layer.dataModel.AllBooks
import com.example.ncertclass6to12books.domain_layer.dataModel.AllBooksChapters
import com.example.ncertclass6to12books.domain_layer.dataModel.ChooseYourClass
import com.example.ncertclass6to12books.domain_layer.dataModel.ChooseYourSubject
import kotlinx.coroutines.flow.Flow

interface Repo {

    suspend fun getAllClasses(): Flow<ResultState<List<ChooseYourClass>>>

    suspend fun getAllSubject(category: String): Flow<ResultState<List<ChooseYourSubject>>>

    suspend fun getSubjectByClass(subjectId: String): Flow<ResultState<List<AllBooks>>>

    suspend fun getAllBooksChapters(cId: String): Flow<ResultState<List<AllBooksChapters>>>
}