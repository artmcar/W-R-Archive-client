package com.artmcar.wrarchive.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artmcar.wrarchive.R

@Composable
fun LoginScreen(
    onLoginClick: ()->Unit,
    onRegisterClick: ()->Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        Surface (
            color = Color.Blue,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            )
            {
                TextButton(
                    onClick = onRegisterClick,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Blue)
                ) {
                    Text("Login")
                }
                TextButton(
                    onClick = onRegisterClick,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                ) {
                    Text("Register")
                }
                //Todo Реализовать две кнопки Login и Register
            }

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text(stringResource(id = R.string.email_text_field))},
                modifier =  Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {Text(stringResource(id = R.string.password_text_field))},
                modifier =  Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.login_button))
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginClick = {},
        onRegisterClick = {}
    )
}