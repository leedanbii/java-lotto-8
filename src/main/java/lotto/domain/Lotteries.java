package lotto.domain;

import java.util.List;
import lotto.message.ErrorMessage;

public class Lotteries {

    private final List<Lotto> lotteries;

    public Lotteries(List<Lotto> lotteries) {
        validateSize(lotteries);
        this.lotteries = List.copyOf(lotteries);
    }

    private void validateSize(List<Lotto> lotteries) {
        if (lotteries.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.ERROR_DOMAIN_LOTTERIES_EMPTY_MESSAGE.formatted());
        }
    }

    public int size() {
        return lotteries.size();
    }

    public List<Lotto> getLotteries() {
        return List.copyOf(lotteries);
    }

}