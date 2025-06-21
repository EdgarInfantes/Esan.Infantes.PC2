package com.example.esaninfantespc2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.example.esaninfantespc2.viewmodel.UserSessionViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.esaninfantespc2.navigation.NavigationWrapper
import com.example.esaninfantespc2.util.SplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private lateinit var userSessionViewModel: UserSessionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userSessionViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[UserSessionViewModel::class.java]

        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            MainAppContent(userSessionViewModel)
        }
    }
}

@Composable
fun MainAppContent(
    userSessionViewModel: UserSessionViewModel
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(Color.White, darkIcons = true)
    }

    var startDestination by remember { mutableStateOf<String?>(null) }

    if (startDestination == null) {
        SplashScreen {
            val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
            startDestination = if (isLoggedIn) "home" else "login"
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            NavigationWrapper(
                startDestination = startDestination!!,
                userSessionViewModel = userSessionViewModel
            )
        }
    }
}
