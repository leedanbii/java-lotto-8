package lotto.generator;

import java.util.List;
import lotto.message.ErrorMessage;

public class FixedLottoNumberGenerator implements LottoNumberGenerator {
    private static final int LOTTO_NUMBER_COUNT = 6;

    private final List<Integer> fixedNumbers;

    public FixedLottoNumberGenerator(List<Integer> fixedNumbers) {
        if (fixedNumbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_LOTTO_NUMBER_COUNT_MESSAGE.formatted());
        }
        this.fixedNumbers = fixedNumbers;
    }

    @Override
    public List<Integer> generateNumbers() {
        return fixedNumbers;
    }
}
