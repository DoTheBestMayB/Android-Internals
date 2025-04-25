## Linux Process hierarchy
- Linux에서 Process는 다른 Process를 실행할 수 있다. 이렇게 실행된 Process는 child process라고 부른다.
- 이렇게 process를 생성하는 과정에서 hierarchy가 생성된다.
- hierarchy 최상단에는 root process가 존재하는데, Android의 root process는 **Zygote**라고 부른다.
- Zygote : 접합체, 수정란 
- Android에서의 process hierarchy는 Linux와 약간은 다르다. 실행하는 Component가 별도의 process에서 실행된다면, 실행한 process가 아니라 Zygote의 child process로 실행된다.

![Image](https://github.com/user-attachments/assets/ff804643-b21c-4596-8c94-b05fe80d94a0)
