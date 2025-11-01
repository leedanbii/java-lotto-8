package lotto.domain;

import lotto.message.ErrorMessage;

public class Amount {
    private static final int UNIT = 1000;
    private static final int AMOUNT_MAX = 100_000_000;
    private static final int AMOUNT_MIN = 1000;

    private final int amount;

    private Amount(int amount) {
        validate(amount);
        this.amount = amount;
    }

    public static Amount of(int amount) {
        return new Amount(amount);
    }

    private void validate(int amount) {
        validateAmountMin(amount);
        validateAmountUnit(amount);
        validateAmountMax(amount);
    }

    private void validateAmountMin(int amount) {
        if (amount < AMOUNT_MIN) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_AMOUNT_MINIMUM_MESSAGE.formatted());
        }
    }

    private void validateAmountUnit(int amount) {
        if (amount % UNIT != 0) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_AMOUNT_UNIT_MESSAGE.formatted());
        }
    }

    private void validateAmountMax(int amount) {
        if (amount > AMOUNT_MAX) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_AMOUNT_MAXIMUM_MESSAGE.formatted());
        }
    }

    public int getAmount() {
        return amount;
    }
}
