package lotto.service;

import lotto.domain.LottoMatchKind;
import lotto.domain.LottoNumbers;
import lotto.domain.WinningNumbers;
import lotto.domain.generator.LottoCustomGenerator;
import lotto.domain.generator.LottoGenerator;
import lotto.domain.vo.LottoNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static lotto.domain.LottoMatchKind.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LottoServiceTest {
    private final LottoGenerator lottoGenerator = new LottoCustomGenerator();
    private final String purchaseAmount = "5000";
    private final LottoService lottoService = new LottoService(lottoGenerator, purchaseAmount);
    private final WinningNumbers winningNumbers = new WinningNumbers(
            new LottoNumbers(Arrays.asList("2", "3", "4", "5", "6", "7")), LottoNumber.from("1"));

    @Test
    @DisplayName("구매 개수를 반환한다.")
    void getCountOfLottoNumbers() {
        final int expected = 5;
        //when
        final int actual = lottoService.getCountOfLottoNumbers();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("구매한 모든 로또 숫자들을 반환한다.")
    void getLottoNumbersGroup() {
        //given
        final LottoNumbers first = new LottoNumbers(Arrays.asList("1", "2", "3", "4", "5", "6"));
        final LottoNumbers second = new LottoNumbers(Arrays.asList("2", "3", "4", "5", "6", "7"));
        final LottoNumbers third = new LottoNumbers(Arrays.asList("3", "4", "5", "6", "7", "8"));
        final LottoNumbers fourth = new LottoNumbers(Arrays.asList("4", "5", "6", "7", "8", "9"));
        final LottoNumbers fifth = new LottoNumbers(Arrays.asList("5", "6", "7", "8", "9", "10"));
        final List<LottoNumbers> expected = Arrays.asList(first, second, third, fourth, fifth);
        //when
        final List<LottoNumbers> actual = lottoService.getLottoNumbersGroup();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("당첨 결과를 반환한다.")
    void getMatchResult() {
        final Map<LottoMatchKind, Integer> actual = lottoService.getMatchResult(winningNumbers);
        assertThat(actual).containsExactly(
                entry(BLANK, 0), entry(THREE, 1), entry(FOUR, 1), entry(FIVE, 1), entry(FIVE_BONUS, 1), entry(SIX, 1));
    }

    @Test
    @DisplayName("수익률을 반환한다.")
    void getProfitRate() {
        //given
        lottoService.getMatchResult(winningNumbers);
        final double expected = 2031555000 / (double) 5000;
        //when
        final double actual = lottoService.getProfitRate();
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
