package com.dldmswo1209.todo_compose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dldmswo1209.todo_compose.model.Todo
import com.dldmswo1209.todo_compose.ui.theme.Todo_ComposeTheme
import com.dldmswo1209.todo_compose.vm.MainViewModel
import java.time.LocalDate

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
                    val viewModel = MainViewModel(LocalContext.current.applicationContext as Application)
                    NavGraph(viewModel)
                }
            }
        }
    }
}

// 네비게이션 라우트 이넘 (값을 가지는 이넘)
enum class NavRoute(val routeName: String, val description: String){
    MAIN("MAIN", "메인 화면"),
    WRITE("Write", "할 일 추가 화면"),
}

// 네비게이션 라우트 액션
class RouteAction(navHostController: NavHostController){
    // 특정 라우트로 이동
    val navTo: (NavRoute) -> (Unit) = { route->
        navHostController.navigate(route.routeName)
    }
    // 뒤로가기 이동
    val goBack: () -> (Unit) = {
        navHostController.navigateUp() // 뒤로가기
    }

//    var data = 1
//    val navToWithData : (NavRoute) -> (Unit) = { route ->
//        navHostController.navigate("${route.routeName}/${data}")
//    }
}

@Composable
fun NavGraph(vm: MainViewModel,startRoute: NavRoute = NavRoute.MAIN){

    val allTodo by vm.allTodo.observeAsState(listOf())

    val navController = rememberNavController()

    val routeAction = remember(navController) { RouteAction(navController) }

    NavHost(navController, startRoute.routeName){
        composable(NavRoute.MAIN.routeName){
            MainScreen(routeAction, allTodo)
        }
        composable(NavRoute.WRITE.routeName){
            TodoWriteScreen(routeAction, vm)
        }
    }

}



@Composable
fun MainScreen(routeAction: RouteAction, allTodo: List<Todo>){
    Surface() {
        Scaffold(
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // 화면 전환(할 일 추가 화면)
                        routeAction.navTo(NavRoute.WRITE)
                    },
                    backgroundColor = MaterialTheme.colors.surface
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_baseline_add_24), contentDescription = null)
                }
            }
        ) {
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
                    TodoListView(allTodo)
                }
            }
        }
    }
}


@Composable
fun TodoListView(allTodo: List<Todo>){
    LazyColumn(){
        items(allTodo){
            TodoItem(todo = it)
        }
    }
}

@Composable
fun TodoItem(todo: Todo){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Text(text = todo.content, style =TextStyle(Color.White, 16.sp, FontWeight.Bold))
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = todo.date, style = TextStyle(Color.White, 16.sp, FontWeight.Medium))
        Spacer(modifier = Modifier.size(8.dp))
        Divider(color = Color(0xFF303030), thickness = 2.dp)
    }
}

@Composable
fun TodoWriteScreen(routeAction: RouteAction, viewModel: MainViewModel){

    var todoInput by remember { mutableStateOf(TextFieldValue()) }
    val date = LocalDate.now().toString()

    ConstraintLayout(
        Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        val (backBtn, okBtn, dateText, textField) = createRefs()
        Row(
            Modifier
                .clickable {
                    routeAction.goBack()
                }
                .constrainAs(backBtn) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
        ) {
            Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
            Text(text = "할 일", style = TextStyle(MaterialTheme.colors.surface, 16.sp, FontWeight.Medium))
        }
        Text(
            text = "완료",
            style = TextStyle(MaterialTheme.colors.surface, 16.sp, FontWeight.Medium),
            modifier = Modifier
                .constrainAs(okBtn) {
                    top.linkTo(parent.top, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }
                .clickable {
                    // 완료 버튼 클릭!
                    // 데이터 저장 후 리스트에 데이터 보여줘야 함
                    val content = todoInput.text
                    val todo = Todo(0, content, date)
                    viewModel.insertTodo(todo)
                    routeAction.goBack()
                }
        )

        Text(
            text = date,
            style = TextStyle(Color(0xFF979797), 16.sp, FontWeight.Medium),
            modifier = Modifier
                .constrainAs(dateText){
                    top.linkTo(backBtn.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        TextField(
            value = todoInput,
            onValueChange = {todoInput = it},
            modifier = Modifier
                .constrainAs(textField) {
                    top.linkTo(dateText.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxSize(),
            textStyle = TextStyle(Color.White, 16.sp, FontWeight.Medium),
            placeholder = { Text(text = "할 일을 입력해주세요", style = TextStyle(Color(0xFF979797), 16.sp, FontWeight.Medium)) },
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Todo_ComposeTheme {
//        NavGraph()
    }
}