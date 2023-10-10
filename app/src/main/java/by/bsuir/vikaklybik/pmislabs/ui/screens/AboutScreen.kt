package by.bsuir.vikaklybik.pmislabs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.vikaklybik.pmislabs.MainActivity
import by.bsuir.vikaklybik.pmislabs.R
import by.bsuir.vikaklybik.pmislabs.ui.components.ListItem

@Composable
fun AboutScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.profile_photo_foreground),
                    contentDescription = stringResource(R.string.author_name),
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(30.dp))
                        .align(Alignment.Center)
                        .size(180.dp)
                )
            }
        }
        item {
            Text(
                text = stringResource(R.string.about_weatherapp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    brush = Brush.linearGradient(
                        colors = listOf(MaterialTheme.colorScheme.primary, Color.Cyan, MaterialTheme.colorScheme.primary)
                    )
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        item {
            Text(
                text = stringResource(R.string.description),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            )
        }
        item {
            val titlesInList = stringArrayResource(id = R.array.titles_in_list)
            val description = stringArrayResource(id = R.array.description_in_list)
            for(i in MainActivity.aboutAppImages.indices) {
                ListItem(description = description[i] , image = MainActivity.aboutAppImages[i] , title = titlesInList[i])
            }
        }
    }
}