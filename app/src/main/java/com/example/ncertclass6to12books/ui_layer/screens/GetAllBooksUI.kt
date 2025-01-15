package com.example.ncertclass6to12books.ui_layer.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ncertclass6to12books.ui.theme.AppColor
import com.example.ncertclass6to12books.ui_layer.AppNavigation.Routes
import com.example.ncertclass6to12books.ui_layer.AppViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllBooks(
    viewModel: AppViewModel = hiltViewModel(),
    navController: NavController,
    category: String
) {
    val state = viewModel.getAllSubjectState.collectAsState()
    val data = state.value.data
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllSubject(category)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "NCERT BOOKS",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                },
                modifier = Modifier.height(84.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor)
            )
        }
    ) {
        when {
            state.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }

            state.value.error.isNotEmpty() -> {
                Toast.makeText(context, state.value.error.toString(), Toast.LENGTH_SHORT).show()
            }

            state.value.data.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                ) {
                    items(data) { item ->
                        AllSubject(
                            subjectName = item!!.subjectName,
                            subjectImageUrl = item.subjectImageUrl,
                            subjectId = item.id,
                            onItemClick = {  navController.navigate(Routes.SubjectById(subjectId = item.id))},
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AllSubject(
    subjectName: String,
    subjectImageUrl: String,
    navController: NavController,
    subjectId: String,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .size(height = 175.dp, width = 400.dp)
            .padding(start = 12.dp, end = 0.dp, top = 12.dp, bottom = 8.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = subjectImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(height = 168.dp, width = 132.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(64.dp))
            Text(text = subjectName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}