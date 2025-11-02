package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BonusNumberTest {

    @Test
    @DisplayName("정상적인 보너스 번호 생성 성공")
    void createBonusNumberSuccess() {
        assertThatCode(() -> BonusNumber.fromUserInput(1)).doesNotThrowAnyException();
        assertThatCode(() -> BonusNumber.fromUserInput(45)).doesNotThrowAnyException();
        assertThatCode(() -> BonusNumber.fromUserInput(23)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("최소 값 미만 입력 시 예외 발생")
    void createBonusNumberBelowMinimum() {
        assertThatThrownBy(() -> BonusNumber.fromUserInput(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_BONUS_NUMBER_RANGE.formatted());
    }

    @Test
    @DisplayName("최대 값 초과 입력 시 예외 발생")
    void createBonusNumberAboveMaximum() {
        assertThatThrownBy(() -> BonusNumber.fromUserInput(46))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_BONUS_NUMBER_RANGE.formatted());
    }

    @Test
    @DisplayName("경계값 테스트 - 최소 값")
    void createBonusNumberMinBoundary() {
        BonusNumber bonusNumber = BonusNumber.fromUserInput(1);
        assertThat(bonusNumber.getNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("경계값 테스트 - 최대 값")
    void createBonusNumberMaxBoundary() {
        BonusNumber bonusNumber = BonusNumber.fromUserInput(45);
        assertThat(bonusNumber.getNumber()).isEqualTo(45);
    }

    @Test
    @DisplayName("음수 입력 시 예외 발생")
    void createBonusNumberNegative() {
        assertThatThrownBy(() -> BonusNumber.fromUserInput(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_BONUS_NUMBER_RANGE.formatted());
    }
}
