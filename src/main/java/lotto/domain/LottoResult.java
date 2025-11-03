package lotto.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lotto.message.ErrorMessage;

public class LottoResult {

    private final Map<Rank, Long> rankCounts;
    private final int totalPrize;
    private final int totalPurchaseAmount;

    public LottoResult(Map<Rank, Long> rankCounts, int totalPurchaseAmount) {
        validate(rankCounts);
        this.rankCounts = rankCounts;
        this.totalPurchaseAmount = totalPurchaseAmount;
        this.totalPrize = calculateTotalPrize(rankCounts);
    }

    private void validate(Map<Rank, Long> rankCounts) {
        if (rankCounts == null || rankCounts.values().stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_LOTTO_RESULT_RANK_COUNTS_REQUIRED.formatted());
        }
    }

    private int calculateTotalPrize(Map<Rank, Long> rankCounts) {
        return rankCounts.entrySet().stream()
                .mapToInt(entry-> entry.getKey().getPrize() * entry.getValue().intValue())
                .sum();
    }

    public double calculateWinningRate() {
        return Math.round((double)totalPrize / totalPurchaseAmount * 10000) / 100.0;
    }

    public List<String> getFormattedStatistics() {
        List<String> stats = new ArrayList<>();

        Arrays.stream(Rank.values())
                .filter(rank -> rank != Rank.NONE)
                .map(this::formatRankStatistic)
                .forEach(stats::add);

        stats.add(getWinningRateText());
        return stats;
    }

    private String formatRankStatistic(Rank rank) {
        long count = rankCounts.getOrDefault(rank, 0L);
        String rankText = rank.getMatchCount() + "개 일치";
        if (rank.isBonusMatch()) {
            rankText += ", 보너스 볼 일치";
        }
        String prizeText = String.format("%,d원", rank.getPrize());
        return rankText + " (" + prizeText + ") - " + count + "개";
    }

    private String getWinningRateText() {
        return String.format("총 수익률은 %.1f%%입니다.", calculateWinningRate());
    }

    public Map<Rank, Long> getRankCounts() {
        return rankCounts;
    }
}
