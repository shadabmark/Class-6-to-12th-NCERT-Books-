package com.example.ncertclass6to12books.data_layer.repoImp

import com.example.ncertclass6to12books.common.ALLBOOKS
import com.example.ncertclass6to12books.common.ALLBOOKSCHAPTERS
import com.example.ncertclass6to12books.common.CHOOSEYOURCLASS
import com.example.ncertclass6to12books.common.CHOOSEYOURSUBJECT
import com.example.ncertclass6to12books.common.ResultState
import com.example.ncertclass6to12books.domain_layer.dataModel.AllBooks
import com.example.ncertclass6to12books.domain_layer.dataModel.AllBooksChapters
import com.example.ncertclass6to12books.domain_layer.dataModel.ChooseYourClass
import com.example.ncertclass6to12books.domain_layer.dataModel.ChooseYourSubject
import com.example.ncertclass6to12books.domain_layer.repo.Repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImp @Inject constructor(
    private val firestore: FirebaseFirestore
) : Repo {

    override suspend fun getAllClasses(): Flow<ResultState<List<ChooseYourClass>>> = callbackFlow {
        trySend(ResultState.Loading)

        firestore.collection(CHOOSEYOURCLASS)
            .orderBy("classNo", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { it ->
                val data = it.documents.mapNotNull {
                    it.toObject(ChooseYourClass::class.java)
                }
                trySend(ResultState.Success(data))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }

        awaitClose { close() }
    }

    override suspend fun getAllSubject(category: String): Flow<ResultState<List<ChooseYourSubject>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firestore.collection(CHOOSEYOURSUBJECT)
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener { it ->
                    val data = it.documents.mapNotNull {
                        it.toObject(ChooseYourSubject::class.java)
                    }
                    trySend(ResultState.Success(data))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }

            awaitClose { close() }
        }

    override suspend fun getSubjectByClass(subjectId: String): Flow<ResultState<List<AllBooks>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firestore.collection(ALLBOOKS)
                .whereEqualTo("subjectId", subjectId)
                .get()
                .addOnSuccessListener { it ->
                    val data = it.documents.mapNotNull {
                        it.toObject(AllBooks::class.java)
                    }
                    trySend(ResultState.Success(data))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }

            awaitClose { close() }
        }

    override suspend fun getAllBooksChapters(cId: String): Flow<ResultState<List<AllBooksChapters>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firestore.collection(ALLBOOKSCHAPTERS)
                .whereEqualTo("cId", cId)
                .orderBy("chapterNumber", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { it ->
                    val data = it.documents.mapNotNull {
                        it.toObject(AllBooksChapters::class.java)
                    }
                    trySend(ResultState.Success(data))
                }.addOnFailureListener {
                    trySend(ResultState.Error(error = "Unknown error"))
                }

            awaitClose { close() }
        }
}