package lotto.controller;

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
        Amount amount = inputAmount();
        Lotteries lotteries = lottoService.purchaseLotteries(amount);

        outputView.printPurchasedLotteries(lotteries.size(), lotteries.getNumbers());

        Lotto WinningNumbers = inputWinningNumber();
        WinningLotto winningLotto = inputBonusNumber(WinningNumbers);

        LottoResult lottoResult = lottoService.calculateLottoResult(winningLotto, lotteries, amount);

        outputView.printWinningResult(lottoResult.getFormattedStatistics());
    }

    public Amount inputAmount() {
        while (true) {
            try {
                String rawAmount = inputView.readAmount();
                return lottoService.createAmount(rawAmount);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    public Lotto inputWinningNumber() {
        while (true) {
            try {
                String rawWinningLotto = inputView.readWinningNumbers();
                return lottoService.createWinningLottoNumbers(rawWinningLotto);
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
