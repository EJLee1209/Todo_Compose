package com.dldmswo1209.todo_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dldmswo1209.composebasic.utils.DummyDataProvider
import com.dldmswo1209.composebasic.utils.RandomUser
import com.dldmswo1209.todo_compose.ui.theme.Todo_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Todo_ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    Scaffold() {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Text(text = "TODO", style = TextStyle(Color.White, 18.sp, FontWeight.Medium))
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                Modifier
                    .background(Color(0xFF202020), shape = RoundedCornerShape(10.dp))
                    .fillMaxSize()
            ) {
                TodoListView()
            }
        }
    }
}

@Composable
fun TodoListView(){
    LazyColumn(){
        items(DummyDataProvider.userList){
            TodoItem(randomUser = it)
        }
    }
}

@Composable
fun TodoItem(randomUser: RandomUser){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Text(text = randomUser.name, style =TextStyle(Color.White, 16.sp, FontWeight.Bold))
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = randomUser.description, style = TextStyle(Color.White, 16.sp, FontWeight.Medium))
        Spacer(modifier = Modifier.size(8.dp))
        Divider(color = Color(0xFF303030), thickness = 2.dp)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Todo_ComposeTheme {
        MainScreen()
    }
}