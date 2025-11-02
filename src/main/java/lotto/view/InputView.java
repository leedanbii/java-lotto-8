package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String READ_AMOUNT_MESSAGE = "\n구입금액을 입력해 주세요.";
    private static final String READ_WINNING_NUMBERS_MESSAGE = "\n당첨 번호를 입력해 주세요.";
    private static final String READ_BONUS_NUMBER_MESSAGE = "\n보너스 번호를 입력해 주세요.";

    public String readAmount() {
        System.out.println(READ_AMOUNT_MESSAGE);
        return Console.readLine();
    }

    public String readWinningNumbers() {
        System.out.println(READ_WINNING_NUMBERS_MESSAGE);
        return Console.readLine();
    }

    public String readBonusNumber() {
        System.out.println(READ_BONUS_NUMBER_MESSAGE);
        return Console.readLine();
    }
}
