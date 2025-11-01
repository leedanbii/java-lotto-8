package lotto.generator;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;

public class RandomLottoNumberGenerator implements LottoNumberGenerator {
    private static final int LOTTO_NUMBER_MINIMUM = 1;
    private static final int LOTTO_NUMBER_MAXIMUM = 45;
    private static final int LOTTO_NUMBER_COUNT = 6;

    @Override
    public List<Integer> generateNumbers() {
        return Randoms.pickUniqueNumbersInRange(LOTTO_NUMBER_MINIMUM, LOTTO_NUMBER_MAXIMUM, LOTTO_NUMBER_COUNT);
    }
}
