package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.generator.FixedLottoNumberGenerator;
import lotto.generator.LottoNumberGenerator;
import lotto.generator.RandomLottoNumberGenerator;
import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoTest {

    private static final List<Integer> VALID_NUMBERS = List.of(1, 2, 3, 4, 5, 6);
    private static final List<Integer> INVALID_RANGE_NUMBERS = List.of(1, 2, 3, 4, 5, 46);
    private static final List<Integer> DUPLICATE_NUMBERS = List.of(1, 2, 3, 4, 5, 5);
    private static final List<Integer> TOO_FEW_NUMBERS = List.of(1, 2, 3, 4, 5);
    private static final List<Integer> TOO_MANY_NUMBERS = List.of(1, 2, 3, 4, 5, 6, 7);

    @Test
    @DisplayName("정상 입력으로 Lotto 생성 성공")
    void createLottoFromUserInput() {
        assertThatCode(() -> Lotto.fromUserInput(VALID_NUMBERS))
                .doesNotThrowAnyException();

        Lotto lotto = Lotto.fromUserInput(VALID_NUMBERS);
        assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("고정 Generator로 Lotto 생성 성공")
    void createLottoFromFixedGenerator() {
        LottoNumberGenerator generator = new FixedLottoNumberGenerator(VALID_NUMBERS);

        assertThatCode(() -> Lotto.of(generator)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("랜덤 Generator로 Lotto 생성 성공")
    void createLottoFromRandomGenerator() {
        LottoNumberGenerator generator = new RandomLottoNumberGenerator();

        assertThatCode(() -> Lotto.of(generator)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("숫자 범위 벗어나면 예외 발생")
    void invalidNumberRangeThrowException() {
        assertInvalidLotto(INVALID_RANGE_NUMBERS, ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_RANGE.formatted());
    }

    @Test
    @DisplayName("중복 숫자 있으면 예외 발생")
    void duplicateNumbersThrowException() {
        assertInvalidLotto(DUPLICATE_NUMBERS, ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_DUPLICATE.formatted());
    }

    @Test
    @DisplayName("숫자 6개 미만/초과 입력 시 예외 발생")
    void invalidNumberCountThrowException() {
        assertInvalidLotto(TOO_FEW_NUMBERS, ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_COUNT.formatted());
        assertInvalidLotto(TOO_MANY_NUMBERS, ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_COUNT.formatted());
    }

    @Test
    @DisplayName("contains 메서드 정상 동작")
    void containsNumber() {
        Lotto lotto = Lotto.fromUserInput(VALID_NUMBERS);

        assertThat(lotto.contains(3)).isTrue();

        assertThat(lotto.contains(7)).isFalse();
    }

    @Test
    @DisplayName("getNumbers로 반환된 리스트는 불변이어야 함")
    void numbersAreImmutable() {
        Lotto lotto = Lotto.fromUserInput(VALID_NUMBERS);

        List<Integer> numbers = lotto.getNumbers();

        assertThatThrownBy(() -> numbers.add(7))
                .isInstanceOf(UnsupportedOperationException.class);
    }


    private void assertInvalidLotto(List<Integer> numbers, String expectedMessage) {
        assertThatThrownBy(() -> Lotto.fromUserInput(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);

        LottoNumberGenerator generator = new FixedLottoNumberGenerator(numbers);
        assertThatThrownBy(() -> Lotto.of(generator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }
}
