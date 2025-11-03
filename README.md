

# 3주차 미션 - 로또

---

# 🔗 코드 구조

```jsx
└─src
    ├─main
    │  └─java
    │      └─lotto
    │          │  Application.java    # 애플리케이션 진입점
    │          │
    │          ├─controller
    │          │      LottoController.java    # 사용자 입/출력 및 서비스 호출 담당
    │          │
    │          ├─domain
    │          │  │  Amount.java    # 로또 구매금액 관리
    │          │  │  BonusNumber.java    # 보너스 번호 관리
    │          │  │  Lotteries.java    # 구매한 로또 컬렉션 관리
    │          │  │  Lotto.java    # 개별 로또 번호 관리
    │          │  │  LottoCount.java    # 로또 구매 수량 (Amount로부터 결정)
    │          │  │  LottoResult.java    # 당첨 결과 계산 및 통계
    │          │  │  Rank.java    # 당첨 등수 계산
    │          │  │  WinningLotto.java    # 당첨 로또 + 보너스 번호 관리
    │          │  │
    │          │  └─parser
    │          │          AbstractNumberParser.java    # 파서 공통 로직 추상 클래스
    │          │          AmountParser.java    # 금액 입력 파서
    │          │          BonusNumberParser.java    # 보너스 번호 파서
    │          │          WinningLottoNumbersParser.java    # 당첨 번호 파서
    │          │
    │          ├─generator
    │          │      FixedLottoNumberGenerator.java    # 테스트용 고정 번호 생성기
    │          │      LottoNumberGenerator.java    # 로또 번호 생성 인터페이스
    │          │      RandomLottoNumberGenerator.java    # 랜덤 번호 생성기
    │          │
    │          ├─message
    │          │      ErrorMessage.java    # 에러 메시지 상수 관리
    │          │
    │          ├─service
    │          │      LottoService.java    # 도메인 객체 생성 및 로직 처리
    │          │
    │          └─view
    │                  InputView.java    # 사용자 입력 처리
    │                  OutputView.java   # 사용자 출력 처리
    │
    └─test
        └─java
            └─lotto
                │  ApplicationTest.java
                │
                ├─controller
                │      LottoControllerTest.java
                │
                ├─domain
                │  │  AmountTest.java
                │  │  BonusNumberTest.java
                │  │  LotteriesTest.java
                │  │  LottoResultTest.java
                │  │  LottoTest.java
                │  │  RankTest.java
                │  │  WinningLottoTest.java
                │  │
                │  └─parser
                │          AmountParserTest.java
                │          BonusNumberParserTest.java
                │          WinningLottoNumbersParserTest.java
                │
                ├─generator
                │      RandomLottoNumberGeneratorTest.java
                │
                └─service
                        LottoServiceTest.java
```

---

# 🎯 기능 목록

## 1️⃣  로또 구매

- 사용자가 **로또 구입 금액**을 입력하면, 금액에 맞게 로또를 발행한다.
- **로또 1장의 가격은 1,000원**이다.
- 발행된 로또는 **개수와 함께 출력**되며, 각 로또의 번호는 **오름차순으로 정렬**된다.
- 한 장의 로또는 **1~45 범위의 중복되지 않는 6개의 숫자**로 구성된다.

**예외 상황**

- 구입 금액이 숫자가 아니거나 1000원 미만인 경우
- 1,000원 단위로 나누어 떨어지지 않는 경우
- 구입 금액이 100,000,000원을 넘는 경우

---

## 2️⃣ 당첨 번호 입력

- 사용자는 **쉼표(,)** 로 구분된 **6개의 숫자**를 입력한다.
- 입력된 숫자는 **중복되지 않아야 하며**, 모두 **1~45 범위 내의 정수**여야 한다.

**예외 상황**

- 숫자 이외의 문자가 포함된 경우
- 중복된 번호가 존재하는 경우
- 숫자가 1~45 범위를 벗어나는 경우
- 개수가 6개가 아닌 경우

---

## 3️⃣ 보너스 번호 입력

- 보너스 번호는 **단일 숫자**로 입력받는다.
- **당첨 번호와 중복될 수 없으며**, **1~45 범위의 정수**여야 한다.

**예외 상황**

- 숫자 이외의 문자가 입력된 경우
- 1~45 범위를 벗어나는 경우
- 당첨 번호와 중복되는 경우

---

## 4️⃣ 당첨 결과 계산

- 사용자가 구매한 모든 로또와 **당첨 번호, 보너스 번호를 비교**하여 **등수를 판정**한다.
- 등수는 다음 기준에 따른다:
  - 6개 번호 일치 → **1등 (2,000,000,000원)**
  - 5개 번호 + 보너스 번호 일치 → **2등 (30,000,000원)**
  - 5개 번호 일치 → **3등 (1,500,000원)**
  - 4개 번호 일치 → **4등 (50,000원)**
  - 3개 번호 일치 → **5등 (5,000원)**
- 각 **등수별 당첨 개수를 집계**하여 출력한다.

**예외 상황**

- 비교 대상 로또가 존재하지 않거나 null을 포함한 경우
  - 사용자 입력은 정상적으로 이루어졌지만, 시스템에서 문제가 생긴 경우 → 재입력 없이 시스템 종료

---

## 5️⃣ 수익률 계산 및 출력

- **총 당첨 금액과 구입 금액을 기준으로 수익률을 계산**한다.
- 수익률은 **소수점 둘째 자리에서 반올림**하여 **퍼센트(%)**로 출력한다.
  - 예: `62.5%`, `100.0%`, `1000000.0%`
- 당첨 내역과 함께 수익률을 출력한다.

---

## 6️⃣ 예외 처리

- 잘못된 입력이 발생하면 **`IllegalArgumentException`** 을 발생시킨다.
- 모든 에러 메시지는 **`[ERROR]`로 시작**해야 한다.
  - 예: `[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.`
- 예외 발생 시, 해당 단계부터 다시 입력을 받는다.
- 숫자 입력시 int 범위를 초과하면 에러를 발생시킨다.

---

## 7️⃣ 프로그램 실행 흐름

1. 구입 금액 입력
2. 로또 발행 및 출력
3. 당첨 번호 입력
4. 보너스 번호 입력
5. 당첨 결과 계산
6. 수익률 출력
7. 프로그램 종료

---

# 🧩 로또 구매 개수 제한

##  1. 과정별 시간 복잡도

1. **로또 발행**
  - `Randoms.pickUniqueNumbersInRange(1, 45, 6)` 호출 × n장 → O(n)
2. **출력**
  - `System.out.println()` 한 줄 출력 = 상대적으로 느림
  - 로또 발행이 많아지면 **콘솔 출력이 병목** → 실제 프로그램에서 가장 느린 부분
3. **당첨 비교 및 수익률 계산**
  - 각 로또 번호 6개와 당첨 번호 6개 + 보너스 비교 → O(n)
  - 수익률 계산 등 → negligible

> 결론: 시간 대부분이 출력에서 소모
>

---

##  2. 현실적 출력 속도 기준

- 일반적인 콘솔 출력 속도: **약 10,000~50,000 줄/초** (환경에 따라 다름)
- 로또 1줄 = `[6개 숫자 리스트]` 출력

예시:

| 로또 장수 | 예상 출력 시간 (초) |
| --- | --- |
| 10,000 | 0.2 ~ 1 |
| 100,000 | 2 ~ 10 |
| 1,000,000 | 20 ~ 100 |
- **1,000,000장**은 출력만으로 10~100초 소요 → 현실적이지 않음

---

# 📌 현실적 최대값

- **100,000장(= 100,000,000원)** → 출력 + 계산 가능
  - 메모리: 100,000 × 64B ≈ 6.4MB → 충분
  - 출력: 0.5~2초 → 허용 범위
- **그 이상**: 500,000~1,000,000장 → 출력 시간이 길어서 실제 사용자 경험 나쁨

---
