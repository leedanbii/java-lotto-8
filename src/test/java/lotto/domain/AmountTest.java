package lotto.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    @DisplayName("Amount 생성 성공")
    void createAmountSuccess() {
        assertThatCode(() -> Amount.of(1000)).doesNotThrowAnyException();
        assertThatCode(() -> Amount.of(50_000)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("최소 금액보다 작은 경우 예외 발생")
    void createAmountBelowMinimumThrowsException() {
        assertThatThrownBy(() -> Amount.of(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_AMOUNT_MINIMUM.formatted());
    }

    @Test
    @DisplayName("최대 금액보다 큰 경우 예외 발생")
    void createAmountAboveMaximumThrowsException() {
        assertThatThrownBy(() -> Amount.of(110_000_000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_AMOUNT_MAXIMUM.formatted());
    }

    @Test
    @DisplayName("금액이 1000원 단위가 아닐 경우 예외 발생")
    void createAmountInvalidUnitThrowsException() {
        assertThatThrownBy(() -> Amount.of(1500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_AMOUNT_UNIT.formatted());
    }

    @Test
    @DisplayName("단위와 범위를 동시에 만족하는 금액 생성")
    void createAmountMultipleCasesSuccess() {
        int[] validAmounts = {1000, 2000, 5000, 10_000, 50_000, 100_000_000};
        for (int a : validAmounts) {
            assertThatCode(() -> Amount.of(a)).doesNotThrowAnyException();
        }
    }

    @Test
    @DisplayName("예외 조합 테스트")
    void createAmountAllInvalidCases() {
        // 최소 미만 & 단위 불일치
        assertThatThrownBy(() -> Amount.of(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_AMOUNT_MINIMUM.formatted());

        // 최대 초과 & 단위 불일치
        assertThatThrownBy(() -> Amount.of(100_000_001))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_AMOUNT_UNIT.formatted());
    }
}
