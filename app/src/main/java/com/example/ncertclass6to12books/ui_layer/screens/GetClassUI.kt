package com.example.ncertclass6to12books.ui_layer.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ncertclass6to12books.ui.theme.AppColor
import com.example.ncertclass6to12books.ui_layer.AppNavigation.Routes
import com.example.ncertclass6to12books.ui_layer.AppViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetClassUI(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.getAllClassState.collectAsState()
    val data = state.value.data ?: emptyList()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllClass()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ALL CLASSES",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier.height(84.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor)
            )
        }
    ) { paddingValues ->
        when {
            state.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            state.value.error.isNotEmpty() -> {
                Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
            }
            state.value.data.isNotEmpty() -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(data) { item ->
                        item?.let {
                            AllClass(
                                title = it.cName,
                                navController = navController,
                                onItemClick = { navController.navigate(Routes.BooksByCategory(category = it.cName))}
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AllClass(
    navController: NavController,
    title: String,
    onItemClick: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(height = 85.dp, width = 0.dp)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}