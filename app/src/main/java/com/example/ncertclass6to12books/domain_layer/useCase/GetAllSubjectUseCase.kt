package com.example.ncertclass6to12books.domain_layer.useCase

import com.example.ncertclass6to12books.domain_layer.repo.Repo
import javax.inject.Inject

class GetAllSubjectUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend fun getAllSubjectUseCase(category: String) = repo.getAllSubject(category = category)
}