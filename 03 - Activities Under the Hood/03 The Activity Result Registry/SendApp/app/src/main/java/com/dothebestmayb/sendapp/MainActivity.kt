package com.dothebestmayb.sendapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.dothebestmayb.sendapp.ui.theme.SendAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 다른 Activity에 의해 Activity가 새롭게 생성된 경우, 여기서 intent를 확인한다.
        if (intent != null) {
            handleIntent(intent)
        }

        setContent {
            SendAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // 이미 Activity가 생성된 후, 다른 Activity가 전달한 Intent를 처리하려면
    // onCreate 함수가 아닌 여기서 처리해야 한다.
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)
    }

    // 두 곳에서 intent를 처리할 수 있기 때문에 별도의 함수로 선언함
    private fun handleIntent(intent: Intent) {
        lifecycleScope.launch {
            delay(3000L)
            // 처리할 action이 맞는지 확인하기
            if (intent.action == "com.dothebestmayb.ACTION_GREET_ME") {
                setResult(
                    RESULT_OK,
                    Intent().apply {
                        putExtra("greeting", "This message will self destruct in 5 seconds.")
                    }
                )
                finish()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SendAppTheme {
        Greeting("Android")
    }
}
