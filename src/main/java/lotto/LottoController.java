package lotto;

import java.util.List;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoGenerator lottoGenerator;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoGenerator = new LottoGenerator();
    }

    public void run() {
        int purchaseAmount = readPurchaseAmountWithRetry();
        List<Lotto> lottos = purchaseAndPrintLottos(purchaseAmount);

        WinningNumbers winningNumbers = readWinningNumbersWithRetry();

        LottoResult result = calculateResult(lottos, winningNumbers);
        printResult(result, purchaseAmount);
    }

    private int readPurchaseAmountWithRetry() {
        while (true) {
            try {
                return inputView.readPurchaseAmount();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private List<Lotto> purchaseAndPrintLottos(int purchaseAmount) {
        int count = purchaseAmount / 1000;
        List<Lotto> lottos = lottoGenerator.generate(count);
        outputView.printPurchaseCount(count);
        outputView.printLottos(lottos);
        return lottos;
    }

    private WinningNumbers readWinningNumbersWithRetry() {
        while (true) {
            try {
                return readWinningNumbers();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private WinningNumbers readWinningNumbers() {
        List<Integer> numbers = inputView.readWinningNumbers();
        int bonusNumber = readBonusNumberWithRetry(numbers);
        return new WinningNumbers(numbers, bonusNumber);
    }

    private int readBonusNumberWithRetry(List<Integer> winningNumbers) {
        while (true) {
            try {
                return inputView.readBonusNumber();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private LottoResult calculateResult(List<Lotto> lottos, WinningNumbers winningNumbers) {
        LottoResult result = new LottoResult();
        result.checkWinning(lottos, winningNumbers);
        return result;
    }

    private void printResult(LottoResult result, int purchaseAmount) {
        outputView.printStatistics(result);
        double profitRate = result.calculateProfitRate(purchaseAmount);
        outputView.printProfitRate(profitRate);
    }
}