## Main Thread
- UI를 그리고, UI와 관련된 이벤트(터치, 키보드 입력)를 처리하는 책임이 있다.
- dispatching framework에 대한 책임도 있다. ex) activity 생명주기 함수 호출, broadcast receiver의 onReceive 함수 호출, service callback 함수 호출

## Main Thread in Android
- Main Thread는 다른 Application에도 존재하지만, Android Main Thread 만의 2가지 특징이 있다.
- Looper와 MessageQueue

### Looper
- Looper는 어떤 것을 계속 반복하는 것이다.
- Looper가 어떻게 동작하는지는 MyLooper.kt 을 확인.

아래 코드는 별도의 thread에서 실행되며 Hello World!를 출력하면 Thread는 종료된다.
```kotlin
thread {
    println("Hello World!")
}
```

이처럼, MainThread가 UI를 모두 그렸고, callback 함수도 모두 처리해서 idle 상태가 됐다고 해보자.
MainThread도 일반 Thread처럼 종료될까? 그렇지 않다. 사용자의 input, UI update 등은 언제든 발생할 수 있기 때문에 준비된 상태를 유지해야 한다.
이를 위해 **MainThread keep-alive 상태를 유지해야 하는데, 이것이 Looper의 목적이다.**
MainThread를 위한 Looper를 Main Looper 라고 부른다.
모든 Thread는 각자의 Looper를 선택적으로 가질 수 있으며, Looper가 있다는 것은 Thread는 alive 상태로 유지한다는 것이다.
Looper는 Thread가 모든 것을 처리했다면, 다음에 실행할 instrcution이 오기 전까지 blocking 시킨다.
Looper는 Thread 내에서 실행되어야 하기 때문에, Thread 없이 단독으로 실행될 수 없다.

### MessageQueue
- Looper에 전달되는 instruction 들은 message queue에 전달되어 순차적으로 처리된다.
- Looper와 MessageQueue의 실제 사용 예시는 LooperExample.kt을 확인.
