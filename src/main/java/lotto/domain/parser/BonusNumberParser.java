package lotto.domain.parser;

import lotto.message.ErrorMessage;

public class BonusNumberParser {

    private BonusNumberParser() { }

    public static int parser(String rawBonusNumber) {
        validateNotBlank(rawBonusNumber);
        return parseToInt(rawBonusNumber);
    }

    private static void validateNotBlank(String rawBonusNumber) {
        if (rawBonusNumber == null || rawBonusNumber.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INPUT_BONUS_NUMBER_REQUIRED.formatted());
        }
    }

    private static int parseToInt(String rawBonusNumber) {
        try {
            return Integer.parseInt(rawBonusNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INPUT_BONUS_NUMBER_FORMAT.formatted());
        }
    }
}
