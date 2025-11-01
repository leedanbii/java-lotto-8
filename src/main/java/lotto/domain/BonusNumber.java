package lotto.domain;

import lotto.message.ErrorMessage;

public class BonusNumber {
    private static final int LOTTO_NUMBER_MINIMUM = 1;
    private static final int LOTTO_NUMBER_MAXIMUM = 45;

    private final int number;

    private BonusNumber(int number) {
        validate(number);
        this.number = number;
    }

    public static BonusNumber fromUserInput(int number) {
        return new BonusNumber(number);
    }

    private void validate(int number) {
        validateNumberRange(number);
    }

    private void validateNumberRange(int number) {
        if (number < LOTTO_NUMBER_MINIMUM || number > LOTTO_NUMBER_MAXIMUM) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_BONUS_NUMBER_RANGE_MESSAGE.formatted());
        }
    }

    public int getNumber() {
        return number;
    }
}
