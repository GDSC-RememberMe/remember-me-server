# remember-me-server

- **프로토타입** : https://whimsical.com/remember-me-UPnSFwbvYbT2GQKpcqKG4T
- **API 문서** : https://documenter.getpostman.com/view/17088295/2s935hPS8i
- **DB 문서** : https://dbdiagram.io/d/63d50f69296d97641d7cb363

![rmdb](https://user-images.githubusercontent.com/77563814/218291131-3994fbbc-bc6d-4780-a34d-71a0d797d77a.png)


### 주요 기능
- **Spring Security + JWT 로그인/회원가입 구현** (02.05)
  - 초기 구현 - [PR](https://github.com/GDSC-RememberMe/remember-me-server/pull/1)
  - 토큰 재발급 로직 리팩토링 - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/e3e1c146671805f404e455d284b316fd42cbfd45) 
- **MemoryQuiz(사용자 추억) 저장/삭제/조회/수정 기능 구현** - [PR](https://github.com/GDSC-RememberMe/remember-me-server/pull/2/commits/f70aef299ea5263466b00b11949d0bf3ed2dc9c6#diff-ec356e0454efce7af49c5653b013b1058632ba12f02c51600e869a628d6541f1)  (02.11)
- **GCS 이미지/오디오 업로드 기능 구현** - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/pull/2/commits/f70aef299ea5263466b00b11949d0bf3ed2dc9c6#diff-ec356e0454efce7af49c5653b013b1058632ba12f02c51600e869a628d6541f1) (02.12)
- **User - Family 가족 관계 설정 기능 구현** - [PR](https://github.com/GDSC-RememberMe/remember-me-server/pull/4) (02.14)
- **Github Actions + Docker로 CI/CD 환경 구현**- [파일](https://github.com/GDSC-RememberMe/remember-me-server/blob/e69d41e19047e5f7e19d35f135d9619e28d3aab5/.github/workflows/gradle.yml) (02.16)
    - Docker 캐싱으로 속도 개선 - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/4fc08812e59e7b2603f5d87309262009c68f3f9b)
    - Gradle 의존성 캐싱하여 속도 개선 - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/6c319ccadb8ec0c88f6067bc72a237c6836a3515)
    - 📝 Github Actions CI/CD 속도 개선하기 - [글 작성](https://sooyoungh.github.io/github-actions-faster)
- **자체 예외 처리 구현** - [관련 커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/f16b7b09dbfda6b55ac495e677dca84dcf0a36ef) (02.21)
  - 📝 스프링의 예외 처리 전략, @ExceptionHandler 알아보기 - [글 작성](https://sooyoungh.github.io/exception-1)
