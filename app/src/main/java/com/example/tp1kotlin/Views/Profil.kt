package com.example.tp1kotlin.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.tp1kotlin.R


@Composable
fun ProfileInfoSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(R.drawable.pdp),
            contentDescription = "photo de profil",
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .border(4.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Erij Ameur", fontSize = 30.sp, color = Color.Black, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Etudiante en 2ème année cycle ingénieur ISIS sous statut apprenti",
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
fun ContactAndButtonSection(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(18.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.courrier),
                contentDescription = "Email Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "erij.ameur@gmail.com", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.linkedin),
                contentDescription = "LinkedIn Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "www.linkedin.com/erijameur", fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(90.dp))

        Button(
            onClick = {
                navController.navigate(films())
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Démarrer", color = Color.White)
        }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProfileInfoSection()
                ContactAndButtonSection(navController)
            }
        }
        else -> {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileInfoSection()
                ContactAndButtonSection(navController)
            }
        }
    }
}