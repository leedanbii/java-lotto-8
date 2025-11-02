package lotto.domain.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WinningLottoNumbersParserTest {

    @Test
    @DisplayName("정상 입력 파싱 성공")
    void parseValidNumbersReturnsList() {
        List<Integer> result = WinningLottoNumbersParser.parser("1, 5, 10, 20, 33, 45");
        assertThat(result).containsExactly(1, 5, 10, 20, 33, 45);
    }

    @Test
    @DisplayName("null 입력시 예외")
    void parseNullThrowsException() {
        assertThatThrownBy(() -> WinningLottoNumbersParser.parser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_WINNING_LOTTO_NUMBER_REQUIRED.formatted());
    }

    @Test
    @DisplayName("공백 입력시 예외")
    void parseBlankThrowsException() {
        assertThatThrownBy(() -> WinningLottoNumbersParser.parser("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_WINNING_LOTTO_NUMBER_REQUIRED.formatted());
    }

    @Test
    @DisplayName("잘못된 숫자 형식 입력시 예외")
    void parseInvalidNumberFormatThrowsException() {
        assertThatThrownBy(() -> WinningLottoNumbersParser.parser("1,2,3,a,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_WINNING_LOTTO_NUMBER_FORMAT.formatted());
    }

    @Test
    @DisplayName("int 범위 초과 입력시 예외")
    void parseNumberExceedsIntThrowsException() {
        String hugeNumber = "1,2,3,9999999999999999999999,5,6";
        assertThatThrownBy(() -> WinningLottoNumbersParser.parser(hugeNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_INPUT_NUMBER_EXCEEDS_MAX.formatted());
    }
}
