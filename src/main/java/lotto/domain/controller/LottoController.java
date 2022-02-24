package lotto.domain.controller;

import lotto.domain.LottoMatchKind;
import lotto.domain.LottoNumbers;
import lotto.domain.WinningNumbers;
import lotto.domain.generator.LottoGenerator;
import lotto.domain.vo.LottoNumber;
import lotto.dto.LottoMatchKindDto;
import lotto.dto.LottoNumbersDto;
import lotto.service.LottoService;
import lotto.view.input.InputView;
import lotto.view.output.OutputView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LottoController {
    private final LottoService lottoService;
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(final LottoGenerator lottoGenerator, final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        lottoService = initializeLottoService(lottoGenerator);
    }

    private LottoService initializeLottoService(final LottoGenerator lottoGenerator) {
        try {
            final String purchaseAmountInput = inputView.inputPurchaseAmount();
            return new LottoService(lottoGenerator, purchaseAmountInput);
        } catch (final Exception e) {
            inputView.printErrorMessage(e.getMessage());
            return initializeLottoService(lottoGenerator);
        }
    }

    public void run() {
        outputView.printPurchaseCount(lottoService.getCountOfLottoNumbers());
        printLottoNumbersGroup();
        final WinningNumbers winningNumbers = generateWinningNumbers();
        printResult(winningNumbers);
    }

    private void printLottoNumbersGroup() {
        final List<LottoNumbersDto> numbersGroup =
                convertLottoNumbersGroupToDtos(lottoService.getLottoNumbersGroup());
        outputView.printLottoNumbersGroup(numbersGroup);
    }

    private List<LottoNumbersDto> convertLottoNumbersGroupToDtos(
            final List<LottoNumbers> numbersGroup) {
        return numbersGroup.stream()
                .map(LottoNumbers::getValues)
                .map(LottoNumbersDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private WinningNumbers generateWinningNumbers() {
        try {
            final LottoNumbers lastWinningNumbers = new LottoNumbers(inputView.inputLastWeekWinningNumbers());
            final LottoNumber bonusNumber = LottoNumber.from(inputView.inputBonusNumber());
            return new WinningNumbers(lastWinningNumbers, bonusNumber);
        } catch (final Exception e) {
            inputView.printErrorMessage(e.getMessage());
            return generateWinningNumbers();
        }
    }

    private void printResult(WinningNumbers winningNumbers) {
        final List<LottoMatchKindDto> results =
                convertWinningResultsToDtos(lottoService.getMatchResult(winningNumbers));
        outputView.printCountOfWinningByMatchKind(results);
        outputView.printProfitRate(lottoService.getProfitRate());
    }

    private List<LottoMatchKindDto> convertWinningResultsToDtos(final Map<LottoMatchKind, Integer> results) {
        return results.keySet().stream()
                .map(lottoMatchKind -> new LottoMatchKindDto(lottoMatchKind, results.get(lottoMatchKind)))
                .collect(Collectors.toUnmodifiableList());
    }
}
