import androidx.compose.runtime.mutableStateListOf
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.post.Post
import data.post.PostDataSourceService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class AppViewModel: KMMViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Simple error
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    val posts = mutableStateListOf<Post>()

    init {
        viewModelScope.coroutineScope.launch(Dispatchers.IO) {
            fetchPosts()
        }
    }

    private suspend fun fetchPosts() {
        println("Fetching the posts...")

        try {
            _isLoading.value = true
            _error.value = null
            delay(1.seconds)
            val response = PostDataSourceService.getAll()
            posts.addAll(response)
        } catch (e: Exception) {
            e.printStackTrace()
            _error.value = e.message
        } finally {
            _isLoading.value = false
        }
    }
}