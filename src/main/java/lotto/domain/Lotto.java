package lotto.domain;

import java.util.List;
import lotto.generator.LottoNumberGenerator;
import lotto.message.ErrorMessage;

public class Lotto {
    private static final int LOTTO_NUMBER_MINIMUM = 1;
    private static final int LOTTO_NUMBER_MAXIMUM = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    private final List<Integer> numbers;

    private Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers.stream()
                .sorted()
                .toList();
    }

    public static Lotto of(LottoNumberGenerator generator) {
        return new Lotto(generator.generateNumbers());
    }

    public static Lotto fromUserInput(List<Integer> numbers) {
        return new Lotto(numbers);
    }

    private void validate(List<Integer> numbers) {
        validateNumberCount(numbers);
        validateNumberRange(numbers);
        validateNumberDuplicate(numbers);
    }

    private void validateNumberCount(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_COUNT_MESSAGE.formatted());
        }
    }

    private void validateNumberRange(List<Integer> numbers) {
        if (numbers.stream().anyMatch(n -> n < LOTTO_NUMBER_MINIMUM || n > LOTTO_NUMBER_MAXIMUM)) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_RANGE_MESSAGE.formatted());
        }
    }

    private void validateNumberDuplicate(List<Integer> numbers) {
        if (numbers.stream().distinct().count() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_RANGE_MESSAGE.formatted());
        }
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    public List<Integer> getNumbers() {
        return List.copyOf(numbers);
    }
}
