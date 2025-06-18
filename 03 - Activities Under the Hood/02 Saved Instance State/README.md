## Configuration Change
- configuration change가 발생하면 activity는 파괴되고 재생성된다.
- 이 과정에서 activity의 상태가 손실될 뿐만 아니라 activity가 속한 process도 파괴된다.

## savedInstanceState
- configuration change로 인한 state 손실을 방지하기 위한 방법으로 ViewModel을 자주 사용한다.
- ViewModel 뿐만 아니라 Bundle 타입의 savedInstanceState를 사용할 수도 있다.
- Bundle은 Primitive Type 데이터를 저장할 수 있다.
- configuration change, system kill과 같은 이유로 activity가 임시로 파괴될 때 onSaveInstanceState callback 함수가 호출된다.
- onSaveInstanceState 함수에서 저장한 state는 onCreate 또는 onRestoreInstanceState callback 함수에서 복원할 수 있다.
```kotlin

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (savedInstanceState != null) {
        restoredState = savedInstanceState.getString(GAME_STATE_KEY)
    }
    // ...
}


// This callback is called only when there is a saved instance previously saved using
// onSaveInstanceState(). Some state is restored in onCreate(). Other state can optionally
// be restored here, possibly usable after onStart() has completed.
// The savedInstanceState Bundle is same as the one used in onCreate().
override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    textView.text = savedInstanceState?.getString(TEXT_VIEW_KEY)
}

// Invoked when the activity might be temporarily destroyed; save the instance state here.
override fun onSaveInstanceState(outState: Bundle?) {
    outState?.run {
        putString(GAME_STATE_KEY, gameState)
        putString(TEXT_VIEW_KEY, textView.text.toString())
    }
    // Call superclass to save any view hierarchy.
    super.onSaveInstanceState(outState)
}

// 기기 재부팅과 같은 경우에도 state를 복원해야 한다면 PersistableBundle에 저장해서 복원할 수 있다.
override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    super.onSaveInstanceState(outState, outPersistentState)
}

override fun onRestoreInstanceState(
    savedInstanceState: Bundle?,
    persistentState: PersistableBundle?
) {
    super.onRestoreInstanceState(savedInstanceState, persistentState)
}
```

## process death
- adb 명령어를 이용해 process death를 테스트할 수 있다.
- `adb shell am kill {package name}`
