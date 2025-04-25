## Android Runtime 기본 개념
- Android app 실행과 관련된 것들을 관리하는 Container라고 볼 수 있다.
- App의 명령어(Instruction)를 읽고 해석하며, 해석된 코드를 실행하는 과정 중에 모든 것을 관리한다. ex) 메모리 관리, GC
- Jetpack compose runtime : 런타임에 compose 코드 실행과 관련된 모든 것들을 관리하는 역할을 한다.
ex) Recomposition : Recomposition은 Compile 타임에 알 수 없다. 언제 recomposition 되는 지는 코드를 실행해봐야 알 수 있기 때문이다. 

## Dalvik Virtual Machine
- Dalvik Virtual Machine을 알아보기 전에 Dalvik Bytecode를 먼저 이해해보자

![Image](https://github.com/user-attachments/assets/da703801-9a9d-44e4-838c-267af6ab4832)

### Dalvik Bytecode
- JVM에서 Java 소스 코드는 Java Compiler에 의해 class 확장자인 Java Bytecode로 변환되고 클래스 로더에 의해 JVM에 로드되어 실행된다.
- Android에서 JVM을 직접 사용하지는 않는다.
- Android에서 Java Bytecode는 Dex Compiler에 의해 dex 확장자인 Dalvik Bytecode로 변환된다.
- Dalvik Bytecode는 Dalvik Virtual Machine에 의해 해석되고 실행된다.

### Dalvik Virtual Machine
- Android 4.4 이전에 기본으로 사용됐다. 그 이후에는 Android Runtime으로 대체됐다.
- Dalvik Virtual Machine은 Dalvik Bytecode의 interpretation에 의존한다.(rely on)
- Compiler는 실행 전에 모든 프로그램 코드를 기계어(machine code)로 변환한다. ex) C에서 c 코드는 Assembly -> 0, 1의 기계어 순서대로 변환됨
- Interpretation은 런타임에 코드를 한 줄씩 해석하고 실행한다. ex) Interpretation을 사용하는 대표적인 예로 Python이 있다. 
- Interpretation을 사용하는 언어는 코드에 Syntax error가 있어도, 에러가 있는 코드를 실행하지 않는 한 문제가 발생하지 않는다.
- Compile 된 코드를 사용하는 언어는 CPU가 바로 사용할 수 있는 언어로 변환된 것을 사용하기 때문에 빠르다.
- 반면 Interpretation을 사용하는 언어는 런타임에 한 줄씩 해석하고 실행해야 하기 때문에 비교적 느리다.

## Just-In-Time(JIT) Compilation
- Interpretation의 느린 실행 속도를 보완하기 위해 나온 개념
- 런타임에 동적으로 Bytecode를 Native Machine code로 변환한다.
- 하지만 여전히 Compiler 기반의 방식보다는 느리다.

## Android Runtime(ART)
- JIT으로도 부족한 실행 성능을 보완하기 위해 나온 개념
- Android Kitkat(4.4)부터 사용되기 시작했으며, Android Lollipop(5.0)에 Dalvik Virtual Machine은 Android Runtime으로 완전히 대체됐다.
- 그렇다면 ART는 완전한 Compile 방식을 사용할까? 그렇지 않다. AOT라는 Compile 방법을 사용한다.

https://www.gsmarena.com/flashback_android_44_kitkat_optimized_the_os_for_phones_with_just_512mb_of_ram-news-52805.php

![Image](https://github.com/user-attachments/assets/6526e208-e25a-42b0-9be1-c36fae5c377b)

### AHEAD OF TIME(AOT) Compilation
- App을 설치하는 과정에서 일부 DEX bytecode를 machine code로 변환한 후 디바이스의 저장 공간에 보관한다.
- 그렇다면 왜 모든 DEX bytecode를 machine code로 변환하지 않을까? 모두 변환하면 파일 크기가 매우 커지기 때문이다. 또한 설치 시간도 더 오래 걸릴 것이다.
- About 화면은 전체 사용자의 2% 만이 접근한다고 한다. 이 화면을 미리 컴파일 하는 것은 APK 크기만 늘릴 뿐이다. 

### ART는 JIT와 AOT를 모두 사용한다.
- 자주(매우) 실행되는 코드, 반드시 실행되는 코드들은 AOT에 의해 미리 컴파일된다. ex) 앱을 처음 열 때 실행되는 코드들
- loop와 같이 빈번하게 호출되는 코드들은 JIT에 의해 런타임에 컴파일된다.

## baseline Profile
- baseline profile은 class, 함수들을 목록으로 가지고 있으며, AOT 과정에서 pre-compile 된다.
- 즉, AOT compliation을 위한 rule을 정의하는 것이라 볼 수 있다.
- baseline profile을 생성하는 테스트 코드를 작성해야 하며, 빌드 과정에서 생성된 baseline profile을 src/main에 위치하면 apk 생성과정에서 적용된다.

https://developer.android.com/topic/performance/baselineprofiles/overview
