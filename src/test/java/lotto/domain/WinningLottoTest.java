package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WinningLottoTest {

    private static final List<Integer> WINNING_NUMBERS = List.of(1, 2, 3, 4, 5, 6);
    private static final int BONUS_NUMBER = 7;
    private static final int DUPLICATE_BONUS_NUMBER = 6;

    @Test
    @DisplayName("정상 입력 시 WinningLotto 생성 성공")
    void createWinningLottoSuccess() {
        Lotto lotto = Lotto.fromUserInput(WINNING_NUMBERS);

        BonusNumber bonusNumber = BonusNumber.fromUserInput(BONUS_NUMBER);

        assertThatCode(() -> WinningLotto.of(lotto, bonusNumber))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외 발생")
    void duplicateBonusNumberThrowsException() {
        Lotto lotto = Lotto.fromUserInput(WINNING_NUMBERS);

        BonusNumber duplicateBonus = BonusNumber.fromUserInput(DUPLICATE_BONUS_NUMBER);

        assertThatThrownBy(() -> WinningLotto.of(lotto, duplicateBonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_WINNING_LOTTO_BONUS_DUPLICATE.formatted());
    }

    @Test
    @DisplayName("getter가 정상적으로 당첨번호와 보너스번호를 반환")
    void gettersReturnCorrectValues() {
        Lotto lotto = Lotto.fromUserInput(WINNING_NUMBERS);
        BonusNumber bonusNumber = BonusNumber.fromUserInput(BONUS_NUMBER);

        WinningLotto winningLotto = WinningLotto.of(lotto, bonusNumber);

        assertThat(winningLotto.getWinningNumbers()).isEqualTo(lotto);
        assertThat(winningLotto.getBonusNumber()).isEqualTo(bonusNumber);
    }
}
