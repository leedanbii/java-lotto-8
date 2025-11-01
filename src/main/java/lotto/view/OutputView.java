package lotto.view;

public class OutputView {

    public void printLotteries(String lotteries) {
        System.out.println(lotteries);
    }

    public void printResult(String result) {
        System.out.println("당첨 통계\n---" + result);
    }

    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
