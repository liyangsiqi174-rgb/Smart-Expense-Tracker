package com.example.smartexpensetracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {

    var name by remember {
        mutableStateOf("")
    }

    var notificationsEnabled by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Profile"
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Personal Information"
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Currency: NZD"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Notifications"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Switch(
            checked = notificationsEnabled,
            onCheckedChange = {
                notificationsEnabled = it
            }
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            onClick = {
                // Profile saving will be added later
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Profile")
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {
                // Logout will be added later
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out")
        }
    }
}