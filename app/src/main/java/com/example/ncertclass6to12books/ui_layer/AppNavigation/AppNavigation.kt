package com.example.ncertclass6to12books.ui_layer.AppNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ncertclass6to12books.ui_layer.screens.GetAllBooks
import com.example.ncertclass6to12books.ui_layer.screens.GetAllChaptersUI
import com.example.ncertclass6to12books.ui_layer.screens.GetClassUI
import com.example.ncertclass6to12books.ui_layer.screens.GetSubjectByClassUI
import com.example.ncertclass6to12books.ui_layer.screens.PdfViewerUI

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.AllClasses
    ) {

        composable<Routes.AllClasses> {
            GetClassUI(
                navController = navController
            )
        }

        composable<Routes.BooksByCategory> {
            val data = it.toRoute<Routes.BooksByCategory>()
            GetAllBooks(
                navController = navController,
                category = data.category
            )
        }

        composable<Routes.SubjectById> {
            val data = it.toRoute<Routes.SubjectById>()
            GetSubjectByClassUI(
                navController = navController,
                subjectId = data.subjectId
            )
        }

        composable<Routes.AllChapters> {
            val data = it.toRoute<Routes.AllChapters>()
            GetAllChaptersUI(
                navController = navController,
                cId = data.cId
            )
        }

        composable<Routes.PDFViewer> {
            val data = it.toRoute<Routes.PDFViewer>()
            PdfViewerUI(
                navController = navController,
                pdfUrl = data.pdfUrl
            )
        }
    }
}