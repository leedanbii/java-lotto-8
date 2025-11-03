package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lotto.message.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoResultTest {

    private static final int TOTAL_PURCHASE = 10_000;
    private static final int SMALL_PURCHASE = 8_000;

    private static final int FIRST_PRIZE = 2_000_000_000;
    private static final int THIRD_PRIZE = 1_500_000;
    private static final int FIFTH_PRIZE = 5_000;

    private static final int PERCENT_MULTIPLIER = 10000;
    private static final double ROUND_DIVISOR = 100.0;

    private static final String SECOND_MESSAGE = "5개 일치, 보너스 볼 일치 (30,000,000원) - 1개";
    private static final String FOURTH_MESSAGE = "4개 일치 (50,000원) - 2개";
    private static final String WINNING_RATE_PREFIX = "총 수익률은 ";
    private static final String WINNING_RATE_SUFFIX = "%입니다.";

    @Test
    @DisplayName("당첨금액 합계 계산 성공")
    void calculateTotalPrizeShouldReturnSumOfEachRankPrize() {
        // given
        Map<Rank, Long> rankCounts = new EnumMap<>(Rank.class);
        rankCounts.put(Rank.FIRST, 1L);   // 2,000,000,000원
        rankCounts.put(Rank.THIRD, 2L);   // 1,500,000원 * 2 = 3,000,000원
        rankCounts.put(Rank.FIFTH, 3L);   // 5,000원 * 3 = 15,000원

        // when
        LottoResult result = new LottoResult(rankCounts, TOTAL_PURCHASE);

        // then
        double expectedRate = Math.round(
                (double) (FIRST_PRIZE + (THIRD_PRIZE * 2) + (FIFTH_PRIZE * 3))
                        / TOTAL_PURCHASE * PERCENT_MULTIPLIER
        ) / ROUND_DIVISOR;

        assertThat(result.calculateWinningRate()).isEqualTo(expectedRate);
    }

    @Test
    @DisplayName("수익률은 소수점 둘째 자리에서 반올림")
    void calculateWinningRateShouldRoundToTwoDecimalPlaces() {
        // given
        Map<Rank, Long> rankCounts = Map.of(Rank.FIFTH, 1L); // 5,000원

        // when
        LottoResult result = new LottoResult(rankCounts, SMALL_PURCHASE);

        // then
        assertThat(result.calculateWinningRate()).isEqualTo(62.5); // (5000 / 8000) * 100 = 62.5%
    }

    @Test
    @DisplayName("통계 문자열이 올바른 형식으로 반환")
    void getFormattedStatisticsShouldReturnFormattedStrings() {
        // given
        Map<Rank, Long> rankCounts = new EnumMap<>(Rank.class);
        rankCounts.put(Rank.SECOND, 1L);
        rankCounts.put(Rank.FOURTH, 2L);

        LottoResult result = new LottoResult(rankCounts, TOTAL_PURCHASE);

        // when
        List<String> stats = result.getFormattedStatistics();

        // then
        assertThat(stats).anySatisfy(line ->
                assertThat(line).contains(SECOND_MESSAGE)
        );
        assertThat(stats).anySatisfy(line ->
                assertThat(line).contains(FOURTH_MESSAGE)
        );
        assertThat(stats.get(stats.size() - 1))
                .startsWith(WINNING_RATE_PREFIX)
                .endsWith(WINNING_RATE_SUFFIX);
    }

    @Test
    @DisplayName("rankCounts에 null 값이 있으면 예외 발생")
    void constructorShouldThrowExceptionWhenRankCountsContainsNullValue() {
        // given
        Map<Rank, Long> rankCounts = new EnumMap<>(Rank.class);
        rankCounts.put(Rank.FIRST, 1L);

        // when
        rankCounts.put(Rank.THIRD, null);

        // then
        assertThatThrownBy(() -> new LottoResult(rankCounts, TOTAL_PURCHASE))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(ErrorMessage.ERROR_DOMAIN_LOTTO_RESULT_RANK_COUNTS_REQUIRED.formatted());
    }
}