package lotto;

import java.util.Collections;
import java.util.List;

public class OutputView {

    public void printPurchaseCount(int count) {
        System.out.println();
        System.out.println(count + "개를 구매했습니다.");
    }

    public void printLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            printLotto(lotto);
        }
    }

    private void printLotto(Lotto lotto) {
        List<Integer> numbers = lotto.getNumbers();
        List<Integer> sortedNumbers = sortNumbers(numbers);
        System.out.println(sortedNumbers);
    }

    private List<Integer> sortNumbers(List<Integer> numbers) {
        List<Integer> sorted = List.copyOf(numbers);
        sorted = sorted.stream().sorted().toList();
        return sorted;
    }

    public void printStatistics(LottoResult result) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");
        printRankStatistics(result);
    }

    private void printRankStatistics(LottoResult result) {
        printRank(Rank.FIFTH, result);
        printRank(Rank.FOURTH, result);
        printRank(Rank.THIRD, result);
        printRank(Rank.SECOND, result);
        printRank(Rank.FIRST, result);
    }

    private void printRank(Rank rank, LottoResult result) {
        int count = result.getCount(rank);
        System.out.println(rank.getDescription() + " ("
                + formatMoney(rank.getPrizeMoney()) + "원) - " + count + "개");
    }

    private String formatMoney(int money) {
        return String.format("%,d", money);
    }

    public void printProfitRate(double profitRate) {
        System.out.println("총 수익률은 " + profitRate + "%입니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }
}