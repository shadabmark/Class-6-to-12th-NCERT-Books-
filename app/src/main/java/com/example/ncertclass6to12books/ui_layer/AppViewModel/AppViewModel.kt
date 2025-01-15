package com.example.ncertclass6to12books.ui_layer.AppViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ncertclass6to12books.common.ResultState
import com.example.ncertclass6to12books.domain_layer.dataModel.AllBooks
import com.example.ncertclass6to12books.domain_layer.dataModel.AllBooksChapters
import com.example.ncertclass6to12books.domain_layer.dataModel.ChooseYourClass
import com.example.ncertclass6to12books.domain_layer.dataModel.ChooseYourSubject
import com.example.ncertclass6to12books.domain_layer.useCase.GetAllBooksChaptersUseCase
import com.example.ncertclass6to12books.domain_layer.useCase.GetAllClassUseCase
import com.example.ncertclass6to12books.domain_layer.useCase.GetAllSubjectUseCase
import com.example.ncertclass6to12books.domain_layer.useCase.GetSubjectByClassUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getAllClassUseCase: GetAllClassUseCase,
    private val getAllSubjectUseCase: GetAllSubjectUseCase,
    private val getSubjectByClassUseCase: GetSubjectByClassUseCase,
    private val getAllBooksChaptersUseCase: GetAllBooksChaptersUseCase,
) : ViewModel() {

    // State for Classes
    private val _getAllClassState = MutableStateFlow(GetClassState())
    val getAllClassState = _getAllClassState.asStateFlow()

    // State for Subjects
    private val _getAllSubjectState = MutableStateFlow(GetSubjectState())
    val getAllSubjectState = _getAllSubjectState.asStateFlow()

    // State for Specific Subjects
    private val _getSubjectByClassState = MutableStateFlow(GetSubjectByClassState())
    val getSubjectByClassState = _getSubjectByClassState.asStateFlow()

    // State for All Books
    private val _getAllBooksState = MutableStateFlow(GetAllBooksState())
    val getAllBooksState = _getAllBooksState.asStateFlow()

    fun getAllClass() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllClassUseCase.getAllClassUseCase().collectLatest {
                _getAllClassState.value = when (it) {
                    is ResultState.Loading -> GetClassState(isLoading = true)
                    is ResultState.Success -> GetClassState(data = it.data)
                    is ResultState.Error -> GetClassState(error = it.error)
                }
            }
        }
    }

    fun getAllSubject(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllSubjectUseCase.getAllSubjectUseCase(category).collectLatest {
                _getAllSubjectState.value = when (it) {
                    is ResultState.Loading -> GetSubjectState(isLoading = true)
                    is ResultState.Success -> GetSubjectState(data = it.data)
                    is ResultState.Error -> GetSubjectState(error = it.error)
                }
            }
        }
    }

    fun getSubjectByClass(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSubjectByClassUseCase.getSubjectByClassUseCase(id).collectLatest {
                _getSubjectByClassState.value = when (it) {
                    is ResultState.Loading -> GetSubjectByClassState(isLoading = true)
                    is ResultState.Success -> GetSubjectByClassState(data = it.data)
                    is ResultState.Error -> GetSubjectByClassState(error = it.error)
                }
            }
        }
    }

    fun getAllBookState(cId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllBooksChaptersUseCase.getAllBooksChaptersUseCase(cId).collectLatest {
                _getAllBooksState.value = when (it) {
                    is ResultState.Loading -> GetAllBooksState(isLoading = true)
                    is ResultState.Success -> GetAllBooksState(data = it.data)
                    is ResultState.Error -> GetAllBooksState(error = it.error)
                }
            }
        }
    }
}

data class GetClassState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<ChooseYourClass?> = emptyList()
)

data class GetSubjectState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<ChooseYourSubject?> = emptyList()
)

data class GetSubjectByClassState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<AllBooks?> = emptyList()
)

data class GetAllBooksState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<AllBooksChapters?> = emptyList()
)