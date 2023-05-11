# remember-me-server

#### 목차
1. [프로토타입 및 User Flow](#1-프로토타입-및-user-flow)
2. [주요 기능](#2-주요-기능)
3. [API/DB 설계](#3-apidb-설계)
4. [CI/CD 구조](#4-cicd-구조)


### 1. 프로토타입 및 User Flow
- **프로토타입**
  - **보호자용** : https://whimsical.com/remember-me-UPnSFwbvYbT2GQKpcqKG4T
  - **환자용** : https://whimsical.com/remember-me-2SNA8nAJUnp2nxYE7xyUuT
- **User Flow(Figma)** : https://www.figma.com/file/cc5XGoWgdD4IaN3fyC1VmT/Remember-Me---User-Flow?node-id=0%3A1&t=Hozllejxl5KK1p7o-0

<p align="center">
<a href="https://www.figma.com/file/cc5XGoWgdD4IaN3fyC1VmT/Remember-Me---User-Flow?node-id=0%3A1&t=Hozllejxl5KK1p7o-0"><img src="https://user-images.githubusercontent.com/77563814/228806067-9e966b9c-6831-4766-8874-14fd7950812e.png"/></a>
</p>


### 2. 주요 기능
- **사용자 검색 기능 구현**
    - 📝 DB 인덱싱 알아보기(클러스터형, 보조, 커버링) - [글 작성](https://sooyoungh.github.io/Index-MySQL)
    - 📝 인덱스 생성하여 조회 속도 개선하기 - [글 작성](https://sooyoungh.github.io/covering-index)
- **Github Actions + Docker로 CI/CD 환경 구현**- [파일](https://github.com/GDSC-RememberMe/remember-me-server/blob/e69d41e19047e5f7e19d35f135d9619e28d3aab5/.github/workflows/gradle.yml)
    - Docker 캐싱으로 속도 개선 - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/4fc08812e59e7b2603f5d87309262009c68f3f9b)
    - Gradle 의존성 캐싱하여 속도 개선 - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/6c319ccadb8ec0c88f6067bc72a237c6836a3515)
    - 📝 Github Actions CI/CD 속도 개선하기 - [글 작성](https://sooyoungh.github.io/github-actions-faster)
- **게시글 조회/저장/수정 기능 구현** - [관련 커밋](https://github.com/GDSC-RememberMe/remember-me-server/pull/5/commits/800819898517ab28fed37e4d6250c5d080ed3213)
    - 게시글 조회 시, JPA N+1 문제 해결 - [관련 커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/51f02b7aa51231a4c83d1cf71c8dcfb0e67e4869)
    - 📝 JPA N+1 문제 상황별 해결하기(fetch join, Batch Size, DTO) - [글 작성](https://sooyoungh.github.io/JPA-N+1)
- **FCM 푸시 알림 기능(추억 회상 알림) 비동기 구현**
    - 📝 FCM 푸시 알림 기능 비동기 구현 - [글 작성](https://sooyoungh.github.io/FCM)
- **Spring Security + JWT 로그인/회원가입 구현**
  - 초기 구현 - [PR](https://github.com/GDSC-RememberMe/remember-me-server/pull/1)
  - 토큰 재발급 로직 리팩토링 - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/e3e1c146671805f404e455d284b316fd42cbfd45) 
  - 📝 JWT 알아보기 - [글 작성](https://sooyoungh.github.io/JWT-1)
- **MemoryQuiz(사용자 추억) 저장/삭제/조회/수정 기능 구현** - [PR](https://github.com/GDSC-RememberMe/remember-me-server/pull/2/commits/f70aef299ea5263466b00b11949d0bf3ed2dc9c6#diff-ec356e0454efce7af49c5653b013b1058632ba12f02c51600e869a628d6541f1) 
- **GCS 이미지/오디오 업로드 기능 구현** - [커밋](https://github.com/GDSC-RememberMe/remember-me-server/pull/2/commits/f70aef299ea5263466b00b11949d0bf3ed2dc9c6#diff-ec356e0454efce7af49c5653b013b1058632ba12f02c51600e869a628d6541f1)
- **User - Family 가족 관계 설정 기능 구현** - [PR](https://github.com/GDSC-RememberMe/remember-me-server/pull/4)
- **자체 예외 처리 구현** - [관련 커밋](https://github.com/GDSC-RememberMe/remember-me-server/commit/f16b7b09dbfda6b55ac495e677dca84dcf0a36ef)
    - 📝 스프링의 예외 처리 전략, @ExceptionHandler 알아보기 - [글 작성](https://sooyoungh.github.io/exception-1)
- **댓글 조회/저장/수정/삭제 기능 구현** - [관련 커밋](https://github.com/GDSC-RememberMe/remember-me-server/pull/5/commits/dbf4caad6b5bd4075aba46d0693ef35c631fe2bf)
- **해시태그 저장 기능 구현** - [관련 커밋](https://github.com/GDSC-RememberMe/remember-me-server/pull/5/commits/44642aef6a4f7a8d941e1d937b4e7e61a441a142)


### 3. API/DB 설계
- **API 문서** : https://documenter.getpostman.com/view/17088295/2s935hPS8i
- **DB Diagram** : 

![rmdb](https://user-images.githubusercontent.com/77563814/228805389-e2494593-7749-45c6-825d-f8b1f1bfe32f.png)


### 4. CI/CD 구조

>  CI/CD는 비용문제로 잠시 중단했습니다💦

![image](https://user-images.githubusercontent.com/77563814/221392126-017991a8-ee06-4595-9ebb-90e38ca5e393.png)




[⬆위로 가기](#remember-me-server)
