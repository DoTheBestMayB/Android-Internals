## Process Kill
- Android의 Process는 사용자의 swipe out, adb 뿐만 아니라 시스템에 의해 종료될 수 있다.
- 시스템은 메모리가 부족하거나 배터리가 부족할 경우, 자원을 많이 사용하지만 사용자에 의해 사용되지 않고 있는 프로세스가 있는 경우 강제로 종료할 수 있다.
- Android OS의 목적은 사용자에게 알리지 않고 모든 실행되고 있는 프로세스들을 관리하는 것이다.
  - 휴리스틱(heuristic) 혹은 규칙에 따라 매 순간 각 프로세스가 사용자에게 얼마나 중요한지 우선 순위를 측정한다.
  - 이것을 **Process Importance Hierarchy**라고 부른다.

## Process Importance Hierarchy
- 총 5층의 피라미드로 구성되어 있고, 아래에 위치할 수록 우선순위가 낮아진다.

### 1. Foreground Processes
- 시스템은 3가지 경우를 Foreground Process로 판단한다. 
1. 사용자에게 보이고 interaction 되고 있다. 즉, RESUMED 상태이다.
2. 현재 실행되고 있는 Broadcast Receiver를 가지고 있는 Process
3. Process that has a serive running that is currently executing one of its callbacks.

### 2. Visible Processes
- 사용자에게 보이지만 foreground 상태가 아닌 Process가 여기에 해당된다.
- 예를 들어 Permission Request Dialog 창이 뜬 경우, 뒤에 Application 화면이 보이지만 사용자와 상호작용하고 있는 상태는 아니다. 이러한 상태가 visible process에 속한다.
  - 만약 Permission Request Dialog가 보여지는 중에 Application Process가 죽으면 어떻게 될까? Application 화면이 검게 변한다.
  - 이때, Dialog 결과를 선택하면 Application Process가 새롭게 생성되며, 새롭게 생성된 Activity에 Dialog 결과가 전달된다.
- 즉 PAUSED STATE에 속하는 경우라고 볼 수 있다. (STOPPED STATE는 아님)
- 또한 Foreground Service 또한 visible processes에 속한다.
  - Foreground Service는 nofitication을 통해 동작을 제공하고 실행 중임을 알려주지만 항상 사용자와 interaction 하는 것은 아니다.  
- System Feature를 사용하는 Process도 visible Process에 속한다. ex) wallpaper

### 3. Services Processes
- Notification 없이 실행되는 Background Service가 여기에 속한다. ex) 백그라운드에서 서버와 동기화

### 4. Background Processes
- 홈버튼을 누르는 등의 이유로 앱이 최소화 될 때, background process가 된다.
- cold boot의 비용이 크기 때문에 자원이 부족하지 않는 한 바로 종료하지는 않는다. 

### 5. Empty Processes
- 현재 실행하고 있는 Component가 없는 Process가 여기에 해당한다. Component가 있어야만 Process가 존재하는 것은 아니다.
