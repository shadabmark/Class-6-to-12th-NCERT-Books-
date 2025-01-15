package com.example.ncertclass6to12books.domain_layer.useCase

import com.example.ncertclass6to12books.domain_layer.repo.Repo
import javax.inject.Inject

class GetAllBooksChaptersUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend fun getAllBooksChaptersUseCase(cId: String) = repo.getAllBooksChapters(cId = cId)
}


