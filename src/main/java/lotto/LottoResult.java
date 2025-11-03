package lotto;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private final Map<Rank, Integer> result;

    public LottoResult() {
        this.result = new EnumMap<>(Rank.class);
        initializeResult();
    }

    private void initializeResult() {
        for (Rank rank : Rank.values()) {
            result.put(rank, 0);
        }
    }

    public void add(Rank rank) {
        result.put(rank, result.get(rank) + 1);
    }

    public void checkWinning(List<Lotto> lottos, WinningNumbers winningNumbers) {
        for (Lotto lotto : lottos) {
            Rank rank = winningNumbers.match(lotto);
            add(rank);
        }
    }

    public int getCount(Rank rank) {
        return result.get(rank);
    }

    public long calculateTotalPrizeMoney() {
        long total = 0;
        for (Rank rank : Rank.values()) {
            if (rank.isWinning()) {
                total += (long) rank.getPrizeMoney() * result.get(rank);
            }
        }
        return total;
    }

    public double calculateProfitRate(int purchaseAmount) {
        long totalPrizeMoney = calculateTotalPrizeMoney();
        return Math.round((double) totalPrizeMoney / purchaseAmount * 1000) / 10.0;
    }
}