## Handler
- 실행해야 하는 Runnable을 입력 받고, 특정 쓰레드가 실행하도록 스케쥴링 하는 역할을 한다.
- 아래는 백그라운드 쓰레드에서 UI를 업데이트 하기 위해 Handler를 이용해 MainThread에게 요청하는 코드이다.

```kotlin
Handler(Looper.getMainLooper()).post {
    textView.setText("Hello World") // 예를 들어 API response를 TextView에 반영하는 경우로 볼 수 있다.
}
```
