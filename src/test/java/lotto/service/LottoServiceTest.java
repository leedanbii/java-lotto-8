package lotto.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Arrays;
import java.util.List;
import lotto.domain.Amount;
import lotto.domain.BonusNumber;
import lotto.domain.Lotteries;
import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import lotto.generator.FixedLottoNumberGenerator;
import lotto.generator.LottoNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoServiceTest {

    private LottoService lottoService;

    @BeforeEach
    void setUp() {
        List<Integer> fixedNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        LottoNumberGenerator fixedGenerator = new FixedLottoNumberGenerator(fixedNumbers);
        lottoService = new LottoService(fixedGenerator);
    }

    @Test
    @DisplayName("Amount 생성 성공")
    void createAmountShouldReturnAmount() {
        assertThatCode(() -> Amount.of(1000)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Lotteries 구매 시 올바른 개수 발행")
    void purchaseLotteriesShouldIssueCorrectCount() {
        Amount amount = Amount.of(5000);
        Lotteries lotteries = lottoService.purchaseLotteries(amount);

        assertThat(lotteries.size()).isEqualTo(5);
        lotteries.getNumbers().forEach(nums ->
                assertThat(nums).hasSize(6)
        );
    }

    @Test
    @DisplayName("WinningLotto 생성 성공")
    void createWinningLottoShouldReturnWinningLotto() {
        Lotto winningNumbers = Lotto.fromUserInput(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = BonusNumber.fromUserInput(7);

        WinningLotto winningLotto = lottoService.createWinningLotto(winningNumbers, bonusNumber);

        assertThat(winningLotto.getWinningNumbers()).isEqualTo(winningNumbers);
        assertThat(winningLotto.getBonusNumber()).isEqualTo(bonusNumber);
    }

    @Test
    @DisplayName("로또 결과 계산 시 rankCounts와 수익률 검증")
    void calculateLottoResultShouldReturnCorrectResult() {
        Lotto winningNumbers = Lotto.fromUserInput(Arrays.asList(1,2,3,4,5,6));
        BonusNumber bonusNumber = BonusNumber.fromUserInput(7);
        WinningLotto winningLotto = WinningLotto.of(winningNumbers, bonusNumber);

        Amount amount = Amount.of(3000);

        Lotteries lotteries = lottoService.purchaseLotteries(amount);
        LottoResult result = lottoService.calculateLottoResult(winningLotto, lotteries, amount);

        assertThat(result.getRankCounts().get(Rank.FIRST)).isEqualTo(3L);
    }
}
