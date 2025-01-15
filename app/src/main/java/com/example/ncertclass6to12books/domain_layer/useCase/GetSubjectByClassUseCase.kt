package com.example.ncertclass6to12books.domain_layer.useCase

import com.example.ncertclass6to12books.domain_layer.repo.Repo
import javax.inject.Inject

class GetSubjectByClassUseCase @Inject constructor(
    private val repo: Repo
) {
    suspend fun getSubjectByClassUseCase(subjectId: String) = repo.getSubjectByClass(subjectId = subjectId)
}