package by.bsuir.vikaklybik.pmislabs.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import by.bsuir.vikaklybik.pmislabs.R
import by.bsuir.vikaklybik.pmislabs.data.home.Cloudiness
import by.bsuir.vikaklybik.pmislabs.data.home.MemorableDay
import by.bsuir.vikaklybik.pmislabs.data.home.Weather
import by.bsuir.vikaklybik.pmislabs.ui.screens.AboutScreen
import by.bsuir.vikaklybik.pmislabs.ui.screens.HomeScreen
import by.bsuir.vikaklybik.pmislabs.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun WeatherTrackerContent() {
    val navController = rememberNavController()

    WeatherAppTheme(
        darkTheme = true,
        dynamicColor = true
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() } //уведомления снизу

    Scaffold(
        topBar = {
            AppToolbar(
                modifier = Modifier
                    .fillMaxWidth(),
                toolbarTitle = stringResource(id = R.string.app_name),
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.version_name),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        )
        {
            NavHost(
                navController = navController, startDestination = "home"
            ) {
                composable("home") { HomeScreen(snackbarHostState) }
                composable("about us") { AboutScreen() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    toolbarTitle: String,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(
                text = toolbarTitle
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = { menuExpanded = !menuExpanded }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.more),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
            ) {
                val menuArray = stringArrayResource(id = R.array.menu_array)
                for (item in menuArray) {
                    DropdownMenuItem(
                        text = {
                            Text(item)
                        },
                        onClick = {
                            menuExpanded = false
                            navController.navigate(item.lowercase())
                        },
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun ListItem(description: String, image: ImageVector, title: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Icon(
                imageVector = image,
                contentDescription = null
            )
            Column(modifier = Modifier.fillMaxWidth())
            {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    memorableDays: List<MemorableDay>,
    onRemove: (MemorableDay) -> Unit,
    onAdd: (MemorableDay, Int) -> Unit,
    snackbarHostState: SnackbarHostState
){
    Box(modifier = Modifier.fillMaxSize()) {
        val sheetState = rememberModalBottomSheetState()
        var selectedMemorableDay by remember { mutableStateOf<MemorableDay?>(null) }
        var isEditing by remember { mutableStateOf(false) }
        var indexToEdit by remember { mutableStateOf(-1) }
        var textSnackbar by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()

        LazyColumn {
            itemsIndexed(memorableDays){ index, memorableDay->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ){

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(modifier = Modifier
                            .padding(bottom = 2.dp)
                            .align(alignment = Alignment.CenterHorizontally)) {
                            val iconId = if (memorableDay.weather.isRaining) R.drawable.baseline_grain_24 else R.drawable.baseline_wb_sunny_24
                            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
                            val formattedDate = memorableDay.date.format(formatter)

                            Text(
                                text = formattedDate, // Add date or header text here
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(horizontal = 2.dp),
                                color = MaterialTheme.colorScheme.primary // Header text color
                            )
                            Icon(
                                painter = painterResource(id = iconId),
                                contentDescription = "",
                            )
                        }
                        ParameterItem("Temperature: ","${memorableDay.weather.temperature}°C")
                        ParameterItem("Humidity: ","${memorableDay.weather.humidity}%")
                        ParameterItem("Cloudiness: ",memorableDay.weather.cloudiness.cloudiness)
                        Text(
                            text = "Description Text:",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary // Customize the text color
                            ),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        Text(
                            text = memorableDay.description,
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp, // Customize line height for better readability
                                color = Color.LightGray // Customize the text color
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = {
                                scope.launch {
                                    sheetState.show() // открытие модального окна
                                    isEditing = true
                                    indexToEdit = index
                                    selectedMemorableDay = memorableDay
                                    textSnackbar = "Memorable day has been added successfully!"

                                }
                                isEditing = true
                                selectedMemorableDay = memorableDay
//                            textSnackbar ="Memorable day has been edited successfully!";

                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = stringResource(R.string.edit),
                                )

                            }
                            IconButton(onClick = {
                                onRemove(memorableDay)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = stringResource(R.string.delete),
                                )

                            }
                        }
                    }
                }


            }
        }
        FloatingActionButton(
            content = {Text(text="+")},
            onClick = {
                scope.launch {
                    sheetState.show()
                    selectedMemorableDay = null
                    isEditing = false
                    textSnackbar = "Memorable day has been added successfully!"

                }
            },
            modifier = Modifier
                .padding(25.dp)
                .align(alignment = Alignment.BottomEnd))
        if(sheetState.isVisible) {
            if (isEditing) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()

                        }
                    },
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        AddMemorableDay(
                            onAdd, scope, sheetState, snackbarHostState,
                            stringResource(R.string.success_edit), selectedMemorableDay, indexToEdit
                        )
                    }
                }
            } else {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()

                        }
                    },
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        AddMemorableDay(
                            onAdd, scope, sheetState, snackbarHostState,
                            stringResource(R.string.added_success)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ParameterItem(description: String, mainText:String){
    Row{
        Text(
            text = description,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Cyan
            )
        )
        Text(
            text = mainText,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.LightGray
            )
        )
    }
}

@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemorableDay(
    onAddOrEdit: (MemorableDay, Int) -> Unit,
    scope: CoroutineScope,
    sheetState: SheetState,
    snackbarHostState: SnackbarHostState,
    textSnackbar: String,
    memorableDay: MemorableDay?= null,
    indexToEdit: Int=-1
){
    var description by remember { mutableStateOf(memorableDay?.description ?: "") }
    var temperature by remember { mutableStateOf(memorableDay?.weather?.temperature ?: 0) }
    var humidity by remember { mutableStateOf(memorableDay?.weather?.humidity ?: 0.0f) }
    var isRaining by remember { mutableStateOf(memorableDay?.weather?.isRaining ?: false) }
    var cloudiness by remember { mutableStateOf(memorableDay?.weather?.cloudiness?.cloudiness ?: Cloudiness.CLEAR.cloudiness) }
    var date by remember { mutableStateOf(memorableDay?.date ?: LocalDate.now()) }

    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )
    //var date by remember { mutableStateOf(memorableDay?.date ?: LocalDate.now()) }
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Description input
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather details inputs
        TextField(
            value = temperature.toString(),
            onValueChange = { temperature = it.toIntOrNull() ?: 0 },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Temperature") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = humidity.toString(),
            onValueChange = { humidity = it.toFloatOrNull() ?: 0.0f },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.humidity)) }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isRaining,
                onCheckedChange = { isRaining = it },
                modifier = Modifier.padding(start = 8.dp),
            )

            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = stringResource(R.string.raining)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = cloudiness,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Cloudiness.values().map { item->item.cloudiness }.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            cloudiness = item
                            expanded = false
                        }
                    )
                }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Date picker
        DatePicker(
            state = datePickerState
        )
        Spacer(modifier = Modifier.height(16.dp))
        date = Date(datePickerState.selectedDateMillis!!).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        // Add button
        Button(
            onClick = {
                val weather = Weather(temperature, humidity, isRaining, Cloudiness.valueOf(cloudiness.uppercase().replace(' ', '_')) )
                val newMemorableDay = MemorableDay(description, weather, date)
                onAddOrEdit(newMemorableDay, indexToEdit)
                scope.launch{
                    sheetState.hide()
                    snackbarHostState.showSnackbar(
                        message = textSnackbar,
                        duration = SnackbarDuration.Short
                    )
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }

}

