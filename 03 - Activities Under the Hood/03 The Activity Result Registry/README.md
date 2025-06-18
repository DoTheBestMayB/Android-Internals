## 개요
- Activity에서 다른 Activity를 호출한 후, 해당 Activity에서 생성된 결과를 필요로 하는 경우가 있다.
- activity results API를 이용해 결과를 리턴받을 수 있다.

## Activity Result Registry
- android internal table이다.
- 어떤 app이 어떤 app으로부터 결과를 기다리고 있는지, result code는 무엇이고 기대하는 리턴 값은 무엇인지, 결과를 리턴받을 함수는 무엇인지 등을 관리한다. 
- 이전에는 startActivityForResult 함수를 사용했으나 deprecated 됐다.
- 최근에는 Activity Result Contract를 사용한다.

## Activity Result Contract
- 두 Activity 간에 주고 받을 데이터 관계를 설정한다.
- A Activity가 B Activity를 실행하며 전달할 input 데이터와 B Activity가 응답으로써 전달할 output 데이터를 정의한다.
- Activity Result Launcher를 함께 사용한다.

### 호출하는 Activity - ReceiveApp
MainActivity
```kotlin
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

```

### 호출되는 Activity - SendApp
AndroidManifest
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.SendApp"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name"
            android:theme="@style/Theme.SendApp">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <intent-filter>
                <action android:name="com.dothebestmayb.ACTION_GREET_ME" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
    </application>

</manifest>

```

MainActivity
```kotlin
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

```
