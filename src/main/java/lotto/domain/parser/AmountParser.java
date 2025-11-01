package lotto.domain.parser;

import lotto.message.ErrorMessage;

public class AmountParser {

    public static int parser(String rawAmount) {
        validateNotBlank(rawAmount);
        return parseToInt(rawAmount);
    }

    private static void validateNotBlank(String rawAmount) {
        if (rawAmount == null || rawAmount.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INPUT_AMOUNT_REQUIRED.formatted());
        }
    }

    private static int parseToInt(String rawAmount) {
        try {
            return Integer.parseInt(rawAmount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INPUT_AMOUNT_NUMBER_MESSAGE.formatted());
        }
    }
}
