package com.example.ncertclass6to12books.ui_layer.AppNavigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    object AllClasses : Routes()

    @Serializable
    data class BooksByCategory(
        val category: String
    ) : Routes()

    @Serializable
    data class SubjectById(
        val subjectId: String
    ) : Routes()

    @Serializable
    data class AllChapters(
        val cId: String
    ) : Routes()

    @Serializable
    data class PDFViewer(
        val pdfUrl: String
    ) : Routes()
}