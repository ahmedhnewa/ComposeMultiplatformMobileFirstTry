import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

val viewModel = AppViewModel()

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // I was unable to use dynamicColorScheme
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        val snackBarHostState = SnackbarHostState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(top = 50.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Text("My dumpiest app")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = "You clicked on floating action button",
                            actionLabel = "Ok",
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add item",
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            }
        ) { paddingValues ->
            Box(Modifier.padding(paddingValues = paddingValues)) {
                val isLoading by viewModel.isLoading.collectAsState()
                val error by viewModel.error.collectAsState()
                if (isLoading) {
                    Center { CircularProgressIndicator() }
                } else {
                    if (error != null) {
                        Center { Text("Error while get data: $error") }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(
                                count = viewModel.posts.count(),
                                itemContent = {
                                    val post = viewModel.posts[it]
                                    ListItem(
                                        headlineText = {
                                            Text(post.title)
                                        },
                                        supportingText = {
                                            Text(post.body)
                                        },
                                        modifier = Modifier.clickable {
                                            coroutineScope.launch {
                                                snackBarHostState.showSnackbar(
                                                    "Clicked on item ${it + 1} with title of ${post.title}",
                                                    null
                                                )
                                            }
                                        },
                                        trailingContent = {
                                            Text(post.userId.toString())
                                        },
                                        leadingContent = {
                                            Box(
                                                modifier = Modifier
                                                    .clip(CircleShape)
                                                    .background(MaterialTheme.colorScheme.onPrimary)
                                                    .size(35.dp)
                                            ) {
                                                Center {
                                                    Text(
                                                        text = "${it + 1}",
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Center(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(Modifier.then(modifier).fillMaxSize(), contentAlignment = Alignment.Center) {
        content()
    }
}

expect fun getPlatformName(): String