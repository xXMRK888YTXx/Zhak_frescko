package com.xxmrk888ytxx.zhak_frescko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

val LocalInt = compositionLocalOf { -1 }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var state by remember {
                mutableIntStateOf(0)
            }

            LaunchedEffect(key1 = Unit, block = {
                while (isActive) {
                    delay(2000)
                    state += 1
                }
            })

            CompositionLocalProvider(
                LocalInt provides state
            ) {
                Column {
                    Text(text = LocalInt.current.toString())
                    CompositionLocalProvider(
                        LocalInt provides LocalInt.current + 1
                    ) {
                        AndroidView(
                            factory = {
                                ComposeView(it).apply { setContent {
                                    Column {
                                        Text(text = LocalInt.current.toString())
                                        CompositionLocalProvider(
                                            LocalInt provides LocalInt.current + 1
                                        ) {
                                            AndroidView(
                                                factory = {
                                                    ComposeView(it).apply {
                                                        setContent { Text(text = LocalInt.current.toString()) }
                                                    }
                                                }
                                            )
                                        }
                                    }
                                } }
                            }
                        )
                    }
                }
            }
        }
    }
}
