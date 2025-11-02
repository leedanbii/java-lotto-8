package lotto.domain.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lotto.message.ErrorMessage;

public class WinningLottoNumbersParser {

    private static final String DELIMITER = ",";

    private WinningLottoNumbersParser() { }

    public static List<Integer> parser(String rawWinningLottoNumbers) {
        validateNotBlank(rawWinningLottoNumbers);
        return parserToIntegerList(rawWinningLottoNumbers);
    }

    private static void validateNotBlank(String rawWinningLottoNumbers) {
        if (rawWinningLottoNumbers == null || rawWinningLottoNumbers.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_INPUT_WINNING_LOTTO_NUMBER_REQUIRED.formatted());
        }
    }

    private static List<Integer> parserToIntegerList(String rawWinningLottoNumbers) {
        return Arrays.stream(rawWinningLottoNumbers.split(DELIMITER))
                .map(String::trim)
                .map(WinningLottoNumbersParser::toInteger)
                .collect(Collectors.toList());
    }

    private static Integer toInteger(String rawWinningLottoNumbers) {
        return Integer.parseInt(rawWinningLottoNumbers);
    }

}
