package lotto.domain;

public enum Rank {
    NONE(0, false, 0),
    FIFTH(3, false, 5_000),
    FOURTH(4, false, 50_000),
    THIRD(5, false, 1_500_000),
    SECOND(5, true, 30_000_000),
    FIRST(6, false, 2_000_000_000);

    private final int matchCount;
    private final boolean bonusMatch;
    private final int prize;

    Rank(int matchCount, boolean bonusMatch, int prize) {
        this.matchCount = matchCount;
        this.bonusMatch = bonusMatch;
        this.prize = prize;
    }

    public static Rank of(Lotto lotto, WinningLotto winning) {
        int count = (int) lotto.getNumbers().stream()
                .filter(winning.getWinningNumbers().getNumbers()::contains)
                .count();
        boolean bonus = lotto.getNumbers().contains(winning.getBonusNumber().getNumber());

        if (count == 6) return FIRST;
        if (count == 5 && bonus) return SECOND;
        if (count == 5) return THIRD;
        if (count == 4) return FOURTH;
        if (count == 3) return FIFTH;
        return NONE;
    }

    public int getPrize() {
        return prize;
    }
    public int getMatchCount() {
        return matchCount;
    }
    public boolean isBonusMatch() {
        return bonusMatch;
    }
}