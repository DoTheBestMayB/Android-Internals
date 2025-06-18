## startActivity 동작 과정
1. startActivity를 호출하면 IPC를 통해 system service를 호출한다.
2. system service는 activity manager service(최신 버전에서는 activity task manager)를 호출한다.
   - activityManager는 `getSystemService<ActivityManager>()` 코드를 통해 접근할 수 있다.
   - activityManager의 appNotResponding 함수를 통해 ANR 팝업을 띄울 수 있다.
3. launch mode에 따라 backstack에 있는 activity를 재활용하거나, 새로운 activity를 생성한다.
4. AndroidManifest에서 Activity에 대한 process를 설정하지 않는 한 호출한 activity가 속한 process에서 실행된다.
5. Activity를 새로 생성해야 한다면, activity task manager는 IPC를 통해 App에게 Activity를 생성해달라고 요청한다.
6. App은 reflection을 이용해 activity를 생성한다.

## Activity constructor
Activity는 System에 의해 생성되기 때문에 custom 생성자를 설정하지 않는다.
만약 생성자로 데이터를 반드시 전달해야 한다면 AppComponentFactory를 이용해야 한다.
AppComponentFactory는 Activity를 어떻게 생성할것인지 설정하는 클래스이다.
