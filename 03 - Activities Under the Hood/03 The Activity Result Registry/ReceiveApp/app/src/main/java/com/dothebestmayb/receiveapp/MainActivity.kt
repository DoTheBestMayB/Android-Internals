package com.dothebestmayb.receiveapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dothebestmayb.receiveapp.ui.theme.ReceiveAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceiveAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var greeting by remember {
                        mutableStateOf<String?>(null)
                    }
                    val launcher = rememberLauncherForActivityResult(
                        // 일반적인 요청은 아래와 같이 미리 정의된 Contract를 사용한다.
//                        contract = ActivityResultContracts.GetContent(),
                        contract = ActivityResultContracts.StartActivityForResult()
                    ) {  result ->
                        if (result.resultCode == RESULT_OK) {
                            greeting = result.data?.getStringExtra("greeting")
                        }
                    }

                    // class로 정의한 contract를 사용하는 경우
                    val launcher2 = rememberLauncherForActivityResult(
                        contract = GreetMeContract,
                    ) {  result ->
                        greeting = result
                    }
                    Button(
                        onClick = {
                            // contract를 GetContent로 설정한 경우, 전달받을 데이터 mime type을 파라미터로 설정한다.
//                            launcher.launch("image/*")
                            launcher.launch(
                                // 실행할 activity AndroidManifest에 적힌 action name
                                Intent("com.dothebestmayb.ACTION_GREET_ME").apply {
                                    // explicit 하게 실행하기 위해 package name 설정
                                    `package` = "com.dothebestmayb.sendapp"
                                }
                            )
                            // class로 정의한 contract를 사용하는 경우
//                            launcher2.launch(Unit)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .wrapContentSize()
                    ) {
                        Text(text = greeting ?: "Launch Activity for result")
                    }
                }
            }
        }
    }
}

/**
 * ActivityResultContract의 타입 I, O
 * I : Input(호출 시 전달할 데이터 타입), O: Output(호출한 Activity로부터 전달받을 데이터 타입)
 *
 * 아래와 같이 Contract를 선언해서 사용할 수 있다.
 */
object GreetMeContract: ActivityResultContract<Unit, String?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent("com.dothebestmayb.ACTION_GREET_ME").apply {
            `package` = "com.dothebestmayb.sendapp"
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra("greeting")
    }
}
