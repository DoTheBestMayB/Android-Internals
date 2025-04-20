## Process란?
- 특정 프로그램이 실행되는 격리된 Container라고 볼 수 있다.
- 각 프로세스는 고유의 메모리(가상 메모리) 공간을 할당받는다.
- 프로세스는 코드를 실행하기 위한 Thread를 가지고 있다.
- 안드로이드에서 프로세스는 기본적으로 Main Thread 1개를 최소한 가지고 있다.

### Process를 구성하는 6가지 요소
1. Application 코드 : 실행해야 하는 코드
2. Data Segment : global and static 변수가 저장되는 곳(프로세스의 생명주기와 동일하므로 Stack이 아닌 별도의 공간에 할당됨)
3. Heap storage : 단일 함수의 생명주기보다 오래 가는 동적 메모리 공간
   예를 들어, 아래와 같이 MainActivity의 property로 선언한 변수는 Heap storage에 저장된다.
```Kotlin
class MainActivity : ComponentActivity() {

    private var count = 0
    // ...
}
```
4. Stack : 함수의 주소, 로컬 변수, 리턴할 값이 저장된 주소 등이 저장되는 메모리 공간
5. CPU pointer(Program Counter) : 다음에 실행할 명령어 주소를 저장하는 공간
6. Resource Handles : File I/O, web socket connection와 같은 하드웨어 작업을 해주는 것

## Process vs Component
- Component : Activity, Service, Broadcast Receiver, Content Provider
- 기본적으로 Component는 앱 Process 한 개에서 공통적으로 실행된다.
- Component를 별도로 실행하고 싶다면 AndroidManifest에서 process 태그를 사용하면 된다.
- process 태그로 ":"를 지정하면 application에서 해당 component를 실행하기 위한 process를 생성해서 실행한다.
- process 태그 이름이 lower-case character로 시작하면 모든 applcation이 공통적으로 사용하는 해당 이름을 가진 process에서 실행된다.
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    // ...
    <activity
        android:process=":" /> // application에서 이 component를 실행하기 위한 process 생성

    <activity
        android:process="someName" /> // someName process에서 실행되며, 다른 application에서 someName 프로세스에서 component를 실행하도록 지정할 수 있다.
</manifest>
```

### ADB를 이용해서 process 목록 보기
- ADB(Android Debug Bridge) : terminal을 이용해 연결된 안드로이드 기기 혹은 terminal과 통신할 수 있도록 해줌
- MacOS는 terminal 탭에서 `adb shell ps -A`를 입력하면 실행 중인 프로세스 목록을 볼 수 있다.
- Window는 adb.exe 파일이 있는 위치 혹은 `C:\Users\admin\AppData\Local\Android\Sdk\platform-tools`로 이동한 후 command 창에서 실행해야 한다.

![Image](https://github.com/user-attachments/assets/129c8fd0-439f-44f1-b372-46465b1eeeed)

- `adb shell am force-stop com.dothebestmayb.composestudy`와 같은 명령어를 이용해 프로세스를 강제 종료할 수 있다.

![Image](https://github.com/user-attachments/assets/74ce8dc2-c3e3-47b3-91ee-91d4115ba484)
