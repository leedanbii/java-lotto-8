package lotto.controller;

import java.util.function.Supplier;
import lotto.domain.Amount;
import lotto.domain.BonusNumber;
import lotto.domain.Lotto;
import lotto.domain.Lotteries;
import lotto.domain.WinningLotto;
import lotto.domain.LottoResult;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;

    public LottoController(InputView inputView, OutputView outputView, LottoService lottoService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
    }

    public void run() {
        Amount amount = readInput(() -> lottoService.createAmount(inputView.readAmount()));

        Lotteries lotteries = lottoService.purchaseLotteries(amount);
        outputView.printPurchasedLotteries(lotteries.size(), lotteries.getNumbers());

        Lotto WinningNumbers = readInput(() -> lottoService
                .createWinningLottoNumbers(inputView.readWinningNumbers()));
        WinningLotto winningLotto = inputBonusNumber(WinningNumbers);

        LottoResult lottoResult = lottoService.calculateLottoResult(winningLotto, lotteries, amount);

        outputView.printWinningResult(lottoResult.getFormattedStatistics());
    }

    private <T> T readInput(Supplier<T> supplier) {
        while (true) {
            try{
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    public WinningLotto inputBonusNumber(Lotto lottoNumbers) {
        while (true) {
            try {
                BonusNumber bonusNumber = inputBonusNumberOnly();
                return buildWinningLotto(lottoNumbers, bonusNumber);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private BonusNumber inputBonusNumberOnly() {
        String rawBonusNumber = inputView.readBonusNumber();
        return lottoService.createBonusNumber(rawBonusNumber);
    }

    private WinningLotto buildWinningLotto(Lotto lottoNumbers, BonusNumber bonusNumber) {
        return lottoService.createWinningLotto(lottoNumbers, bonusNumber);
    }
}
