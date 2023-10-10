package by.bsuir.vikaklybik.pmislabs.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import by.bsuir.vikaklybik.pmislabs.data.home.HomeViewModel
import by.bsuir.vikaklybik.pmislabs.ui.components.HomeScreenContent

@Composable
fun HomeScreen(snackbarHostState: SnackbarHostState) {
    val viewModel = viewModel<HomeViewModel>();
    HomeScreenContent(
        memorableDays = viewModel.memorableDays,
        onAdd = viewModel::onClickAddOrEditMemorableDay,
        onRemove = viewModel::onClickRemoveMemorableDay,
        snackbarHostState = snackbarHostState
    )
}