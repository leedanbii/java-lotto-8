package lotto.domain;

public class LottoCount {

    private static final int LOTTO_PRICE = 1000;

    private final int count;

    private LottoCount(int count) {
        this.count = count;
    }

    public static LottoCount from(Amount amount) {
        return new LottoCount(amount.getAmount() / LOTTO_PRICE);
    }

    public int getCount() {
        return count;
    }
}
