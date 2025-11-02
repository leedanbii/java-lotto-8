package lotto.domain;

import lotto.message.ErrorMessage;

public class WinningLotto {

    private final Lotto winningNumbers;
    private final BonusNumber bonusNumber;

    private WinningLotto(Lotto winningNumbers, BonusNumber bonusNumber) {
        this.winningNumbers = winningNumbers;
        validateBonusNumber(bonusNumber);
        this.bonusNumber = bonusNumber;
    }

    public static WinningLotto of(Lotto winningNumbers, BonusNumber bonusNumber) {
        return new WinningLotto(winningNumbers, bonusNumber);
    }

    private void validateBonusNumber (BonusNumber bonusNumber) {
        if (winningNumbers.contains(bonusNumber.getNumber())) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_WINNING_LOTTO_BONUS_DUPLICATE.formatted());
        }
    }

    public Lotto getWinningNumbers() {
        return winningNumbers;
    }

    public BonusNumber getBonusNumber() {
        return bonusNumber;
    }
}
