package lotto.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import lotto.generator.FixedLottoNumberGenerator;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoControllerTest {

    private FixedLottoNumberGenerator fixedGenerator;
    private LottoService lottoService;
    private StubOutputView outputView;

    @BeforeEach
    void setUp() {
        fixedGenerator = new FixedLottoNumberGenerator(Arrays.asList(1, 2, 3, 4, 5, 6));
        lottoService = new LottoService(fixedGenerator);
        outputView = new StubOutputView();
    }

    @Test
    @DisplayName("컨트롤러 전체 흐름 - 입력/출력 정상 동작")
    void runShouldSucceed() {
        // given
        StubInputView inputView = new StubInputView(
                "5000",
                "1,2,3,4,5,6",
                "7"
        );

        LottoController controller = new LottoController(inputView, outputView, lottoService);

        // when
        controller.run();

        // then
        assertThat(outputView.purchasedLotteriesCount).isEqualTo(5);
        assertThat(outputView.printedStats).isNotEmpty();
        assertThat(outputView.printedStats.getLast()).contains("총 수익률은");
    }

    @DisplayName("컨트롤러 - 잘못된 입력 후 재입력 성공")
    @Test
    void runShouldRecoverAfterInvalidInput() {
        // given
        StubInputView inputView = new StubInputView(
                "-1000",
                "15001",
                "5000",
                "1,2,3,4,5,6",
                "6",
                "7"
        );

        LottoController controller = new LottoController(inputView, outputView, lottoService);

        // when
        controller.run();

        // then
        assertThat(outputView.printedErrors).isNotEmpty();
        assertThat(outputView.purchasedLotteriesCount).isEqualTo(5);
        assertThat(outputView.printedStats.getLast()).contains("총 수익률은");
    }


    // 테스트용 InputView Stub
    private static class StubInputView extends InputView {
        private final Queue<String> inputs = new ArrayDeque<>();

        public StubInputView(String... inputs) {
            this.inputs.addAll(Arrays.asList(inputs));
        }

        @Override
        public String readAmount() {
            return inputs.poll();
        }

        @Override
        public String readWinningNumbers() {
            return inputs.poll();
        }

        @Override
        public String readBonusNumber() {
            return inputs.poll();
        }
    }

    // 테스트용 OutputView Stub
    private static class StubOutputView extends OutputView {
        int purchasedLotteriesCount;
        List<List<Integer>> lastPrintedNumbers = new ArrayList<>();
        List<String> printedStats = new ArrayList<>();
        List<String> printedErrors = new ArrayList<>();

        @Override
        public void printPurchasedLotteries(int count, List<List<Integer>> lottoNumbers) {
            this.purchasedLotteriesCount = count;
            this.lastPrintedNumbers = lottoNumbers;
        }

        @Override
        public void printWinningResult(List<String> stats) {
            this.printedStats = stats;
        }

        @Override
        public void printError(String errorMessage) {
            printedErrors.add(errorMessage);
        }
    }
}
