## System Service
- `getSystemService<T>()`와 같은 함수를 이용해서 System Service를 얻을 수 있다.
- 예를 들어 위치 정보를 얻어야 한다면, `getSystemService<LocationManager>()`를 호출하면 된다.

## XxxManager를 직접 사용하는 대신 SystemService 함수를 통해 얻는 이유
- XxxManager는 OS와 관련된 기능을 제공한다. ex) Hardware와 상호작용하는 SensorManager
- 그래서 안전을 위해 XxxManager를 직접 사용할 수 없고, Xxx Manager는 다른 Process(System Server라고 불림)에 존재하며, IPC(Inter Process Communication)를 통해 요청해야 한다.
- 이렇게 구성하면 System Service들에 대한 권한은 사용하는 App이 아니라 OS에 있기 때문에, 오남용 등이 발생하면 요청을 거부할 수 있다.
- 
