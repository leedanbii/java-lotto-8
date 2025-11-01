package lotto.message;

public enum ErrorMessage {

    ERROR_INPUT_AMOUNT_REQUIRED("로또 구입 금액은 빈 값이거나 공백일 수 없습니다."),
    ERROR_INPUT_AMOUNT_NUMBER_MESSAGE("로또 구입 금액은 숫자여야 합니다."),

    ERROR_DOMAIN_AMOUNT_UNIT_MESSAGE("로또 구입 금액은 1000원 단위여야 합니다."),
    ERROR_DOMAIN_AMOUNT_MINIMUM_MESSAGE("로또 구입 금액은 1000원 이상이어야 합니다."),
    ERROR_DOMAIN_AMOUNT_MAXIMUM_MESSAGE("로또 구입 금액은 100,000,000원을 초과할 수 없습니다."),

    ERROR_DOMAIN_LOTTO_NUMBER_RANGE_MESSAGE("로또 번호는 1부터 45 사이의 숫자여야 합니다."),
    ERROR_DOMAIN_LOTTO_NUMBER_COUNT_MESSAGE("로또 번호는 6개여야 합니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String formatted() {
        return "[ERROR] " + message;
    }
}
