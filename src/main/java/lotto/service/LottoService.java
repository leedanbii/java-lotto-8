package lotto.service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lotto.domain.Amount;
import lotto.domain.BonusNumber;
import lotto.domain.Lotto;
import lotto.domain.LottoCount;
import lotto.domain.Lotteries;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import lotto.domain.parser.AmountParser;
import lotto.domain.parser.BonusNumberParser;
import lotto.domain.parser.WinningLottoNumbersParser;
import lotto.domain.LottoResult;
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

    public Lotto createWinningLottoNumbers(String rawWinningNumber) {
        List<Integer> winningNumbers = WinningLottoNumbersParser.parser(rawWinningNumber);
        return Lotto.fromUserInput(winningNumbers);
    }

    public BonusNumber createBonusNumber(String rawBonusNumber) {
        int bonusNumber = BonusNumberParser.parser(rawBonusNumber);
        return BonusNumber.fromUserInput(bonusNumber);
    }

    public WinningLotto createWinningLotto(Lotto winningNumber, BonusNumber bonusNumber) {
        return WinningLotto.of(winningNumber, bonusNumber);
    }

    public LottoResult calculateLottoResult(WinningLotto winningLotto, Lotteries lotteries, Amount amount) {
        Map<Rank, Long> rankCounts = lotteries.countByRank(winningLotto);
        return new LottoResult(rankCounts,amount.getAmount());
    }
}
