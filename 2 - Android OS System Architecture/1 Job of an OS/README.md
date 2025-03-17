1. Process와 Thread 관리
2. 메모리 관리
   - 프로세스에게 할당된 메모리에만 접근하도록 관리
   - 메모리가 부족할 때 사용하지 않는 프로세스를 죽여서 메모리 확보
3. 파일 관리 및 I/O 연산
   - 하드웨어와 소프트웨어 사이에서 bridge 역할
4. security and permissions
   - 카메라, GPS와 같은 자원을 관리하고, 각 애플리케이션의 자원에 대한 접근 권한을 관리함
   - 리눅스와 같은 rwx(읽기, 쓰기, 실행) 권한(안드로이드는 리눅스에 기반한 OS임)
5. Hardware Abstraction Layer(HAL)
   - 안드로이드 기기에 있는 카메라, 마이크 등은 여러 제조사로부터 제작되어 조합된다. 
   - 각 하드웨어의 동작은 하드웨어 드라이버에 의해 관리된다.
   - high level 개발자가 low level의 하드웨어가 어떻게 동작하는지 자세히 알 필요는 없다.
   - 그래서 하드웨어에 대한 접근을 추상화해서 API 형식으로 제공하는 것이 HAL이다.
   - 터치 스크린도 Hardware이다. 사용자가 화면을 누른 정보는 현재 실행 중인 앱에 전달된다(forward).
6. UI Management
   - 리눅스와 같은 터미널 기반의 OS는 UI가 없다.
   - Android 기기는 사용자와 상호작용 할 수 있는 화면(UI)이 있다.
   - UI는 rendering pipeline에 의해 화면에 그려진다.
   - UI는 multiple layer로 되어 있는데, 알림이 왔을 때 화면 상단에 pop up으로 뜨는 것이 그 예이다.
   - 앱 간에 전환도 UI Management에 속한다.
   
