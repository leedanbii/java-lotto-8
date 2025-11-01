package lotto.service;

import java.util.List;
import java.util.stream.IntStream;
import lotto.domain.Amount;
import lotto.domain.Lotto;
import lotto.domain.LottoCount;
import lotto.domain.Lotteries;
import lotto.domain.parser.AmountParser;
import lotto.generator.LottoNumberGenerator;
import lotto.generator.RandomLottoNumberGenerator;

public class LottoService {

    public Amount createAmount(String rawAmount) {
        int amount = AmountParser.parser(rawAmount);
        return Amount.of(amount);
    }

    public Lotteries purchaseLotteries(Amount amount) {
        LottoCount lottoCount = LottoCount.from(amount);
        return issueLotteries(lottoCount);
    }

    private Lotteries issueLotteries(LottoCount lottoCount) {
        LottoNumberGenerator generator = new RandomLottoNumberGenerator();

        List<Lotto> issuedLotteries = IntStream.range(0, lottoCount.getCount())
                .mapToObj(i -> Lotto.of(generator))
                .toList();
        return new Lotteries(issuedLotteries);
    }

}
