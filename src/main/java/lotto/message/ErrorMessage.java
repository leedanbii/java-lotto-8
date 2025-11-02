package lotto.message;

public enum ErrorMessage {

    ERROR_INPUT_NUMBER_EXCEEDS_MAX("숫자 입력 허용치를 초과하였습니다."),

    ERROR_INPUT_AMOUNT_REQUIRED("로또 구입 금액은 빈 값이거나 공백일 수 없습니다."),
    ERROR_INPUT_AMOUNT_NUMBER("로또 구입 금액은 숫자여야 합니다."),

    ERROR_INPUT_WINNING_LOTTO_NUMBER_REQUIRED("로또 당첨 번호는 빈 값이거나 공백일 수 없습니다."),
    ERROR_INPUT_WINNING_LOTTO_NUMBER_FORMAT("로또 당첨 번호를 양식을 지켜 입력해주세요."),

    ERROR_INPUT_BONUS_NUMBER_REQUIRED("보너스 번호는 빈 값이거나 공백일 수 없습니다."),
    ERROR_INPUT_BONUS_NUMBER_FORMAT("보너스 번호는 숫자여야 합니다."),

    ERROR_DOMAIN_AMOUNT_UNIT("로또 구입 금액은 1000원 단위여야 합니다."),
    ERROR_DOMAIN_AMOUNT_MINIMUM("로또 구입 금액은 1000원 이상이어야 합니다."),
    ERROR_DOMAIN_AMOUNT_MAXIMUM("로또 구입 금액은 100,000,000원을 초과할 수 없습니다."),

    ERROR_DOMAIN_LOTTO_NUMBER_RANGE("로또 번호는 1부터 45 사이의 숫자여야 합니다."),
    ERROR_DOMAIN_LOTTO_NUMBER_COUNT("로또 번호는 6개여야 합니다."),
    ERROR_DOMAIN_LOTTO_NUMBER_DUPLICATE("로또 번호는 서로 중복될 수 없습니다."),

    ERROR_DOMAIN_BONUS_NUMBER_RANGE("보너스 번호는 1부터 45 사이의 숫자여야 합니다."),

    ERROR_DOMAIN_WINNING_LOTTO_BONUS_DUPLICATE("보너스 번호는 당첨 번호와 중복될 수 없습니다."),

    ERROR_DOMAIN_LOTTERIES_EMPTY("로또는 하나 이상 발행되어야 합니다.");

    private static final String errorMessageTag = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String formatted() {
        return errorMessageTag + message;
    }
}
