package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import lotto.message.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LotteriesTest {

    private static Lotto lotto1;
    private static Lotto lotto2;
    private static Lotto lotto3;
    private static Lotto lotto4;

    @BeforeEach
    void setUp() {
        // 완전 일치
        lotto1 = Lotto.fromUserInput(List.of(1, 2, 3, 4, 5, 6));
        // 5개 + 보너스
        lotto2 = Lotto.fromUserInput(List.of(1, 2, 3, 4, 5, 7));
        // 5개만 일치
        lotto3 = Lotto.fromUserInput(List.of(1, 2, 3, 4, 5, 8));
        // 3~4개 일치
        lotto4 = Lotto.fromUserInput(List.of(1, 2, 3, 9, 10, 11));
    }

    @Test
    @DisplayName("정상 Lotto 리스트로 Lotteries 생성")
    void createLotteriesSuccess() {
        assertThatCode(() -> new Lotteries(List.of(lotto1, lotto2, lotto3, lotto4)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("getNumbers()가 올바른 Lotto 번호 리스트 반환")
    void getNumbersReturnsCorrectNumbers() {
        Lotteries lotteries = new Lotteries(List.of(lotto1, lotto2));
        List<List<Integer>> numbers = lotteries.getNumbers();

        assertThat(numbers).containsExactly(lotto1.getNumbers(), lotto2.getNumbers());
    }

    @Test
    @DisplayName("size()가 올바른 Lotto 개수 반환")
    void sizeReturnsCorrectCount() {
        Lotteries lotteries = new Lotteries(List.of(lotto1, lotto2, lotto3));
        assertThat(lotteries.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("빈 Lotto 리스트로 생성 시 예외 발생")
    void createLotteriesEmptyListThrowsException() {
        assertThatThrownBy(() -> new Lotteries(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_LOTTERIES_EMPTY.formatted());
    }

    @Test
    @DisplayName("calculateRanks()는 Rank.NONE 제외하고 반환")
    void calculateRanksExcludesNoneRank() {
        WinningLotto winningLotto = WinningLotto.of(
                Lotto.fromUserInput(List.of(1, 2, 3, 4, 5, 6)),
                BonusNumber.fromUserInput(7)
        );

        Lotteries lotteries = new Lotteries(List.of(lotto1, lotto2, lotto3, lotto4));
        List<Rank> ranks = lotteries.calculateRanks(winningLotto);

        assertThat(ranks).containsExactlyInAnyOrder(
                Rank.FIRST,   // lotto1
                Rank.SECOND,  // lotto2
                Rank.THIRD,   // lotto3
                Rank.FIFTH    // lotto4
        );
    }

    @Test
    @DisplayName("countByRank()가 Rank별 개수 계산 성공")
    void countByRankCountsCorrectly() {
        WinningLotto winningLotto = WinningLotto.of(
                Lotto.fromUserInput(List.of(1, 2, 3, 4, 5, 6)),
                BonusNumber.fromUserInput(7)
        );

        Lotteries lotteries = new Lotteries(List.of(lotto1, lotto2, lotto3, lotto4));
        Map<Rank, Long> rankCounts = lotteries.countByRank(winningLotto);

        assertThat(rankCounts.get(Rank.FIRST)).isEqualTo(1L);
        assertThat(rankCounts.get(Rank.SECOND)).isEqualTo(1L);
        assertThat(rankCounts.get(Rank.THIRD)).isEqualTo(1L);
        assertThat(rankCounts.get(Rank.FIFTH)).isEqualTo(1L);
        assertThat(rankCounts.getOrDefault(Rank.NONE, 0L)).isZero();
    }
}
