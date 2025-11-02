package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RankTest {

    private static Lotto createLotto(Integer... numbers) {
        return Lotto.fromUserInput(List.of(numbers));
    }

    private static WinningLotto createWinningLotto(List<Integer> winningNumbers, int bonusNumber) {
        Lotto winningLotto = Lotto.fromUserInput(winningNumbers);

        BonusNumber bonus = BonusNumber.fromUserInput(bonusNumber);

        return WinningLotto.of(winningLotto, bonus);
    }

    @ParameterizedTest(name = "[{index}] {0} → {1}")
    @MethodSource("provideLottoCases")
    @DisplayName("일치 개수와 보너스 여부에 따라 Rank를 판단")
    void shouldReturnCorrectRank(Lotto lotto, WinningLotto winningLotto, Rank expectedRank) {
        Rank result = Rank.of(lotto, winningLotto);

        assertThat(result).isEqualTo(expectedRank);
    }

    private static Stream<Arguments> provideLottoCases() {
        WinningLotto baseWinningLotto = createWinningLotto(List.of(1, 2, 3, 4, 5, 6), 7);

        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(
                        createLotto(1, 2, 3, 4, 5, 6), baseWinningLotto, Rank.FIRST),
                org.junit.jupiter.params.provider.Arguments.of(
                        createLotto(1, 2, 3, 4, 5, 7), baseWinningLotto, Rank.SECOND),
                org.junit.jupiter.params.provider.Arguments.of(
                        createLotto(1, 2, 3, 4, 5, 8), baseWinningLotto, Rank.THIRD),
                org.junit.jupiter.params.provider.Arguments.of(
                        createLotto(1, 2, 3, 4, 8, 9), baseWinningLotto, Rank.FOURTH),
                org.junit.jupiter.params.provider.Arguments.of(
                        createLotto(1, 2, 3, 8, 9, 10), baseWinningLotto, Rank.FIFTH),
                org.junit.jupiter.params.provider.Arguments.of(
                        createLotto(1, 2, 10, 11, 12, 13), baseWinningLotto, Rank.NONE)
        );
    }
}
