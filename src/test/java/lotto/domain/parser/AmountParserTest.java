package lotto.domain.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AmountParserTest {

    @Test
    @DisplayName("숫자 문자열 파싱 성공")
    void parserValidNumberReturnsInt() {
        int result = AmountParser.parser("1000");
        assertThat(result).isEqualTo(1000);
    }

    @Test
    @DisplayName("null 입력시 예외")
    void parserNullThrowsException() {
        assertThatThrownBy(() -> AmountParser.parser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_AMOUNT_REQUIRED.formatted());
    }

    @Test
    @DisplayName("공백 입력시 예외")
    void parseBlankThrowsException() {
        assertThatThrownBy(() -> AmountParser.parser("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_AMOUNT_REQUIRED.formatted());
    }

    @Test
    @DisplayName("숫자 외 입력시 예외")
    void parseInvalidNumberFormatThrowsException() {
        assertThatThrownBy(() -> AmountParser.parser("abc123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_AMOUNT_NUMBER.formatted());
    }

    @Test
    @DisplayName("int 범위 초과 시 예외")
    void parseNumberExceedsIntThrowsException() {
        String hugeNumber = "9999999999999999999999999";
        assertThatThrownBy(() -> AmountParser.parser(hugeNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_NUMBER_EXCEEDS_MAX.formatted());
    }
}
