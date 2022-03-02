package lotto.view.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleInputView implements InputView {
    private static final String INPUT_PURCHASE_AMOUNT_MESSAGE = "구입금액을 입력해 주세요.";
    private static final String INPUT_MANUAL_PURCHASE_COUNTS_MESSAGE = "\n수동으로 구매할 로또 수를 입력해 주세요.";
    private static final String INPUT_MANUAL_PURCHASE_WINNING_NUMBERS_MESSAGE = "\n수동으로 구매할 번호를 입력해 주세요.";
    private static final String INPUT_WINNING_NUMBERS_MESSAGE = "지난 주 당첨 번호를 입력해 주세요.";
    private static final String INPUT_BONUS_NUMBER_MESSAGE = "보너스 볼을 입력해 주세요.";
    private static final String INPUT_RETRY_MESSAGE = " 다시 입력해주세요.";
    private static final String LOTTO_NUMBER_DELIMITER = ",";

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputPurchaseAmount() {
        System.out.println(INPUT_PURCHASE_AMOUNT_MESSAGE);
        return scanner.nextLine();
    }

    @Override
    public String inputManualPurchaseCounts() {
        System.out.println(INPUT_MANUAL_PURCHASE_COUNTS_MESSAGE);
        return scanner.nextLine();
    }

    @Override
    public List<List<String>> inputManualPurchaseWinningNumbers(final int counts) {
        System.out.println(INPUT_MANUAL_PURCHASE_WINNING_NUMBERS_MESSAGE);
        List<List<String>> manualNumbersGroup = new ArrayList<>();
        for (int i = 0; i < counts; i++) {
            manualNumbersGroup.add(lottoNumbers());
        }
        return manualNumbersGroup;
    }

    @Override
    public List<String> inputLastWeekWinningNumbers() {
        System.out.println(INPUT_WINNING_NUMBERS_MESSAGE);
        return lottoNumbers();
    }

    private List<String> lottoNumbers() {
        final String[] numbers = scanner.nextLine()
                .split(LOTTO_NUMBER_DELIMITER);
        return Arrays.stream(numbers)
                .map(String::trim)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String inputBonusNumber() {
        System.out.println(INPUT_BONUS_NUMBER_MESSAGE);
        return scanner.nextLine();
    }

    @Override
    public void printErrorMessage(final String errorMessage) {
        System.out.println(System.lineSeparator() + errorMessage.concat(INPUT_RETRY_MESSAGE));
    }
}
