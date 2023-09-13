package com.example.numbergame

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.numbergame.ui.theme.NumberGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGameTheme{
                NumberGameApp()
            }
        }
    }
}

@Preview
@Composable
fun NumberGameApp(){
    Element(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

@Composable
fun Element(modifier: Modifier = Modifier){
    var trueNumber by remember {
        mutableStateOf(Random.nextInt(1,100))
    }
    var count by remember{ mutableStateOf(0) }
    var guess by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var gamewon by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Guess Count: $count")
        Spacer(modifier=Modifier.height(20.dp))

        TextField(
            value = guess,
            onValueChange = { newValue ->
                guess = newValue
            },
            label = { Text("Enter your guess") }
        )

        Text(text = result)
        Spacer(modifier=Modifier.height(20.dp))
        Button(onClick = {
            if (guess.isNotEmpty()) {
                val userGuess = guess.toIntOrNull()
                if (userGuess != null) {
                    if (userGuess == trueNumber) {
                        gamewon = true
                        result = "Congratulations! You guessed the true number."
                    } else if (userGuess > trueNumber) {
                        result = "Guess lower."
                    } else {
                        result = "Guess higher."
                    }
                    count++
                } else {
                    result = "Please enter a valid number."
                }
            } else {
                result = "Please enter your guess."
            }
        }, enabled=!gamewon
        )
        {
            Text(text = "Guess")
        }
        if (gamewon) {
            Button(
                onClick = {
                    // Reset the game state
                    gamewon = false
                    guess = ""
                    result = ""
                    trueNumber = (1..100).random()
                    count = 0
                }
            ) {
                Text(text = "Play Again")
            }
        }

    }
}