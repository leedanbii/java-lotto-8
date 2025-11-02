package lotto.domain.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BonusNumberParserTest {

    @Test
    @DisplayName("숫자 문자열 파싱 성공")
    void parseValidNumberReturnsInt() {
        int result = BonusNumberParser.parser("7");
        assertThat(result).isEqualTo(7);
    }

    @Test
    @DisplayName("null 입력시 예외")
    void parseNullThrowsException() {
        assertThatThrownBy(() -> BonusNumberParser.parser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_BONUS_NUMBER_REQUIRED.formatted());
    }

    @Test
    @DisplayName("숫자 외 입력시 예외")
    void parseInvalidNumberFormatThrowsException() {
        assertThatThrownBy(() -> BonusNumberParser.parser("a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_BONUS_NUMBER_FORMAT.formatted());
    }

    @Test
    @DisplayName("int 범위 초과 시 예외")
    void parseNumberExceedsIntThrowsException() {
        String hugeNumber = "9999999999999999999999999";
        assertThatThrownBy(() -> BonusNumberParser.parser(hugeNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_NUMBER_EXCEEDS_MAX.formatted());
    }
}
