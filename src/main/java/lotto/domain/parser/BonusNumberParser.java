package lotto.domain.parser;

import java.math.BigInteger;
import lotto.message.ErrorMessage;

public class BonusNumberParser extends AbstractNumberParser {

    private BonusNumberParser() { }

    public static int parser(String rawBonusNumber) {
        return new BonusNumberParser().parse(rawBonusNumber);
    }

    @Override
    protected ErrorMessage blankErrorMessage() {
        return ErrorMessage.ERROR_INPUT_BONUS_NUMBER_REQUIRED;
    }

    @Override
    protected ErrorMessage formatErrorMessage() {
        return ErrorMessage.ERROR_INPUT_BONUS_NUMBER_FORMAT;
    }
}
