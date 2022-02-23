package lotto.service;

import lotto.domain.LottoMatchKind;
import lotto.domain.LottoNumbers;
import lotto.domain.PurchaseAmount;
import lotto.domain.TargetLottoNumbers;
import lotto.domain.generator.Generator;
import lotto.domain.vo.LottoNumber;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoService {
    private static final int LOTTO_PRICE = 1000;

    private final List<LottoNumbers> lottoNumbersGroup;
    private final PurchaseAmount purchaseAmount;
    private final Map<LottoMatchKind, Integer> matchResult;

    public LottoService(final Generator generator, final String purchaseAmount) {
        this.purchaseAmount = PurchaseAmount.fromPurchaseAmountAndLottoPrice(purchaseAmount, LOTTO_PRICE);
        lottoNumbersGroup =
                generator.generateLottoNumbersGroup(this.purchaseAmount.getCountOfLottoNumbers(LOTTO_PRICE));
        matchResult = new EnumMap<>(LottoMatchKind.class);
        initializeResult(matchResult);
    }

    private void initializeResult(final Map<LottoMatchKind, Integer> result) {
        Arrays.stream(LottoMatchKind.values())
                .forEach(lottoMatchKind -> result.put(lottoMatchKind, 0));
    }

    public Map<LottoMatchKind, Integer> getMatchResult(final List<String> targetNumbers, final String bonusNumber) {
        final LottoNumbers targetLottoNumbers = new LottoNumbers(targetNumbers);
        final LottoNumber bonusLottoNumber = LottoNumber.from(bonusNumber);
        final TargetLottoNumbers target = new TargetLottoNumbers(targetLottoNumbers, bonusLottoNumber);
        return match(target);
    }

    private Map<LottoMatchKind, Integer> match(final TargetLottoNumbers target) {
        lottoNumbersGroup.stream()
                .map(target::getLottoMatchResult)
                .forEach(lottoMatchKind -> matchResult.put(lottoMatchKind, matchResult.get(lottoMatchKind) + 1));
        return matchResult;
    }

    public double getProfitRate() {
        final long totalProfit = matchResult.keySet()
                .stream()
                .mapToLong(lottoMatchKind -> lottoMatchKind.getProfit(matchResult.get(lottoMatchKind)))
                .sum();
        return purchaseAmount.getProfitRate(totalProfit);
    }
}