Android는 오픈 소스 프로젝트(AOSP)로 아래 공식 문서에서 AOSP 아키텍처에 대한 내용을 확인할 수 있다.
https://source.android.com/docs/core/architecture

Bottom Up 방식으로 간단하게 알아보자.

![Image](https://github.com/user-attachments/assets/e3e44e2b-dec4-43d8-aa55-c46e1d638fee)

### Linux Kernel
- Android는 Linux Kernel 위에서 동작한다.
- Android permission 시스템, 앱이라는 concept, 알림은 Linux kernel이 제공하는 기능을 활용해서 만들어진 Android 기능이다.
- 하드웨어(하드웨어 드라이버)와 직접 상호작용한다.

### System Services and Daemons
- Native daemons and libraries로도 불리며 Linux Kernel과 직접 상호작용하는 layer이다.
- daemon이 제공하는 명령어로 `logd`가 있는데 `Log.d`는 이 명령어를 사용하는 것이다. 

### HAL(Hardware Abstraction Layer)
- super low level layer(Linux kernel, System Services and Daemons)와 high level layer 사이에 있는 추상화 계층

### Android Runtime(ART)
- Java 또는 Kotlin 코드로부터 변환된 bytecode instruction을 CPU instruction으로 변환하는 역할을 한다. 

### System Services
- Locaion Manager, Window Manager, Notification Manager 등이 여기에 속한다.
- System service는 OS에 의해 관리되는데, 각 앱이 사용하는 것을 제한하고 관리한다. ex) 한 앱이 알림을 무한정 보내는 것을 방지

### Android Framework
- Android system이 제공하는 Java, Kotlin API의 집합체
- Android(Public) API와 System API로 구성되어 있다.
- Android API : Activity, Service, Context, Broadcast Receiver, Bundle 등
- System API : 일반적인 앱은 사용할 수 없고, 제조사에 의해 만들어진 앱만이 사용할 수 있다.

### Device Manufacturer Apps
- 제조사에 의해 만들어진 앱으로 System API를 사용할 수 있으며, API를 건너뛰고 Android Framework에 직접 접근해서 사용할 수 있다.
- 예를 들어 삼성에서 카메라 앱을 만들 때, Pixel에서는 제공하지 않는 기능을 커스터마이징 해서 제공하기 위해 low level API를 필요로 할 수 있는데, 이때 Framework API를 직접 사용한다.
- 이 앱들은 기기에 사전에 설치되어야 하며, 시스템 소프트 업데이트에 의해서만 업데이트 될 수 있다.

### Privileged Appss
- System API를 사용할 수 있다.
- 기기에 사전에 설치되어야 한다.
- Android Studio → Device Explorer → product → priv-app에서 목록을 확인할 수 있다. 

![Image](https://github.com/user-attachments/assets/b8327773-08e2-4cb6-9abb-b9f1c846edf1)

### Android Apps
- 일반적으로 개발하는 앱이 여기에 속하며, Android API만 사용할 수 있다.
