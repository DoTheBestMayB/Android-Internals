## Native Code
- Android에서 Native Language인 C, C++로 작성된 코드를 실행할 수 있다. 
Native Language라고 불리는 이유는 컴파일 과정에서 CPU가 이해할 수 있는 0, 1로 변환 되기 때문이다.
- 장점1 : 매우 빠르다. 3D 게임, video rendering과 같이 연산을 많이 요구하는 작업을 할 때 매우 유리하다.
- 장점2 : C, C++로 된 기존 라이브러리를 사용할 수 있다. ex) encryption, image processing
- 장점3 : Android OS, 하드웨어에 대한 low-level API를 사용할 수 있다.
Android OS 계층에 따라 HAL 등을 통해 간접적으로 사용해야 하는 것은 동일하지만 Kotlin, Java에 비해 low-level API를 사용할 수 있다.

### .SO Files
- C, C++로 작성된 Native 라이브러리는 .So File로 컴파일 된다. SO 파일은 APK의 lib folder에 위치한다.
- .So File은 런타임에 로드되고 실행될 수 있는 shared objection(SO) 라이브러리이다.

## JNI(Java Native Interface)
- Java, Kotlin 코드와 C, C++ 코드 사이의 interaction을 위한 communication layer 역할을 한다.
- Java, Kotlin 코드에서 Native library를 요청하면, JNI가 SO 파일을 app의 process 메모리 공간에 로드해 실행할 수 있도록 준비한다.
- 이러한 과정은 Android Runtime과 무관하게 동작한다. ART가 process의 전체적인 lifecycle을 관리하지만, native code를 직접 실행하지는 않는다.
- 컴파일된 Native instruction은 JIT, AOT와도 관계 없다.
- Java, Kotlin 코드에서 Native 코드를 직접 실행할 수 없고, 개별적인 장소(Java, Kotlin 코드와 관계 없는 장소로 이해됨)에서 Native 코드가 실행되도록 함수를 요청할 뿐이다.
- 그러면 JNI가 이 작업들을 처리하며 Android CPU에서 raw CPU Instrcution(Compile 된 Native code)을 직접 실행한다.

https://developer.android.com/training/articles/perf-jni
