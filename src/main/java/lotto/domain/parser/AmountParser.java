package lotto.domain.parser;

import lotto.message.ErrorMessage;

public class AmountParser extends AbstractNumberParser {

    private AmountParser() { }

    public static int parser(String rawAmount) {
        return new AmountParser().parse(rawAmount);
    }

    @Override
    protected ErrorMessage blankErrorMessage() {
        return ErrorMessage.ERROR_INPUT_AMOUNT_REQUIRED;
    }

    @Override
    protected ErrorMessage formatErrorMessage() {
        return ErrorMessage.ERROR_INPUT_AMOUNT_NUMBER;
    }
}
