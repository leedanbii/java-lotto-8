package lotto.domain.parser;

import java.math.BigInteger;
import lotto.message.ErrorMessage;

public abstract class AbstractNumberParser {

    protected abstract ErrorMessage blankErrorMessage();
    protected abstract ErrorMessage formatErrorMessage();

    protected int parse(String rawValue) {
        validateNotBlank(rawValue);
        return parseToInt(rawValue);
    }

    protected void validateNotBlank(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new IllegalArgumentException(blankErrorMessage().formatted());
        }
    }

    private Integer parseToInt(String rawValue) {
        try {
            return new BigInteger(rawValue.trim()).intValueExact();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(formatErrorMessage().formatted());
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INPUT_NUMBER_EXCEEDS_MAX.formatted());
        }
    }
}
