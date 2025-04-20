class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val myLooper = MyLooper()

        repeat(5) {
            myLooper.enqueue(sampleRunnable(it))
        }

        // Looper가 종료되지 않는다는 것을 확인하기 위한 코드
        lifecycleScope.launch {
            delay(10_000L)
            myLooper.enqueue(sampleRunnable(5))
        }

        val viewModel = MyViewModel()

        setContent {
            ComposeStudyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 2. collectAsState에 의해 가져오는 state는 onClick MessageQueue를 처리하는 Looper와 별개다.
                    // 기본값으로 main dispatcher(내부적으로 Main Thread 사용)에서 처리된다.
                    val counter by viewModel.counter.collectAsState(
                        // Main 대신 Main.immediate를 적으면 onClick에서 출력되는 counter 값이 화면에 보이는 값과 일치하게 된다.
                        // 이러한 문제는 TextField에서도 발생하는데, 이러한 문제를 해결한 것이 TextField2다.
                        context = Dispatchers.Main.immediate,
                    )

                    Button(
                        // 1. onClick이 MessageQueue에 들어간다.
                        onClick = {
                            viewModel.increment()
                            // 클릭하면 화면에 보이는 숫자보다 1 작은 숫자를 보여준다.
                            // 왜 그런지 MessageQueue 관점에서 알아보자.
                            println("Counter: $counter")
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    ) {
                        Text(
                            text = "Counter: $counter"
                        )
                    }
                }
            }
        }
    }

    private fun sampleRunnable(index: Int): Runnable {
        return Runnable {
            println("Runnable $index started.")
            Thread.sleep(1000L)
            println("Runnable $index finished.")
        }
    }
}

// simplified ViewModel
class MyViewModel {

    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun increment() {
        _counter.update { it + 1 }
    }
}
