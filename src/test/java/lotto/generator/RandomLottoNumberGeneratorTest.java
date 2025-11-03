package lotto.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RandomLottoNumberGeneratorTest {

    private static final int LOTTO_NUMBER_COUNT = 6;
    private static final int LOTTO_NUMBER_MINIMUM = 1;
    private static final int LOTTO_NUMBER_MAXIMUM = 45;

    @Test
    @DisplayName("랜덤 로또 번호 생성 시 크기와 범위 확인")
    void generateNumbersShouldBeValid() {
        RandomLottoNumberGenerator generator = new RandomLottoNumberGenerator();
        List<Integer> numbers = generator.generateNumbers();

        assertThat(numbers).hasSize(LOTTO_NUMBER_COUNT);
        assertThat(numbers).allMatch(n -> n >= LOTTO_NUMBER_MINIMUM && n <= LOTTO_NUMBER_MAXIMUM);
        assertThat(numbers).doesNotHaveDuplicates();
    }

    @RepeatedTest(10)
    @DisplayName("랜덤 생성 시 반복해도 조건 유지")
    void generateNumbersRepeatedlyShouldBeValid() {
        RandomLottoNumberGenerator generator = new RandomLottoNumberGenerator();
        List<Integer> numbers = generator.generateNumbers();

        assertThat(numbers).hasSize(LOTTO_NUMBER_COUNT);
        assertThat(numbers).allMatch(n -> n >= LOTTO_NUMBER_MINIMUM && n <= LOTTO_NUMBER_MAXIMUM);
        assertThat(numbers).doesNotHaveDuplicates();
    }
}
