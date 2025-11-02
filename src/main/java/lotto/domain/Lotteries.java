package lotto.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.message.ErrorMessage;

public class Lotteries {

    private final List<Lotto> lotteries;

    public Lotteries(List<Lotto> lotteries) {
        validateSize(lotteries);
        this.lotteries = List.copyOf(lotteries);
    }

    private void validateSize(List<Lotto> lotteries) {
        if (lotteries.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_LOTTERIES_EMPTY.formatted());
        }
    }

    public Map<Rank, Long> countByRank(WinningLotto winningLotto) {
        return calculateRanks(winningLotto).stream()
                .collect(Collectors.groupingBy(rank -> rank, Collectors.counting()));
    }

    public List<Rank> calculateRanks(WinningLotto winningLotto) {
        return lotteries.stream()
                .map(lotto -> Rank.of(lotto, winningLotto))
                .filter(rank -> rank != Rank.NONE)
                .toList();
    }

    public List<List<Integer>> getNumbers() {
        return lotteries.stream()
                .map(Lotto::getNumbers)
                .toList();
    }

    public int size() {
        return lotteries.size();
    }
}