package com.geno1024.qqtmu.re

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "QQTang Resource Editor") {
        MenuBar {
            Menu("File") {
                Menu("New...") {}
                Menu("Open...") {}
                Menu("Save...") {}
            }
        }
        var text by remember { mutableStateOf("Hello, World!") }

        MaterialTheme {
            Button(onClick = {
                text = "Hello, Desktop!"
            }) {
                Text(text)
            }
        }
    }
}
