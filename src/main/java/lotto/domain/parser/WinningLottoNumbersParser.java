package lotto.domain.parser;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lotto.message.ErrorMessage;

public class WinningLottoNumbersParser extends AbstractNumberParser {

    private static final String DELIMITER = ",";

    private WinningLottoNumbersParser() { }

    public static List<Integer> parser(String rawNumbers) {
        return new WinningLottoNumbersParser().parseToIntegerList(rawNumbers);
    }

    private List<Integer> parseToIntegerList(String rawNumbers) {
        validateNotBlank(rawNumbers);
        return Arrays.stream(rawNumbers.split(DELIMITER))
                .map(String::trim)
                .map(this::parse)
                .collect(Collectors.toList());
    }

    @Override
    protected ErrorMessage blankErrorMessage() {
        return ErrorMessage.ERROR_INPUT_WINNING_LOTTO_NUMBER_REQUIRED;
    }

    @Override
    protected ErrorMessage formatErrorMessage() {
        return ErrorMessage.ERROR_INPUT_WINNING_LOTTO_NUMBER_FORMAT;
    }
}
