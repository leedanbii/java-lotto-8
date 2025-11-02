package lotto.view;

import java.util.List;

public class OutputView {

    private static final String PRINT_LOTTERIES_MESSAGE = "개를 구매했습니다.";
    private static final String PRINT_WINNING_RESULT_MESSAGE = "\n당첨 통계\n---";

    public void printPurchasedLotteries(int count, List<List<Integer>> lottoNumbers) {
        System.out.println("\n" + count + PRINT_LOTTERIES_MESSAGE);
        lottoNumbers.forEach(numbers -> System.out.println(numbers));
    }

    public void printWinningResult(List<String> stats) {
        System.out.println(PRINT_WINNING_RESULT_MESSAGE);
        stats.forEach(System.out::println);
    }

    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
