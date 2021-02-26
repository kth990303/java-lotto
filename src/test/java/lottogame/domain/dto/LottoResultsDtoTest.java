package lottogame.domain.dto;

import lottogame.domain.lotto.Money;
import lottogame.domain.statistic.Rank;
import lottogame.domain.statistic.LottoResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultsDtoTest {
    private LottoResults lottoResult;
    private LottoResultsDto lottoResultsDto;

    @BeforeEach
    void setUp() {
        List<Rank> lottoResultGroup = Arrays.asList(
                Rank.of(4, false),
                Rank.of(3, false),
                Rank.of(5, true),
                Rank.of(5, false));
        lottoResult = new LottoResults(lottoResultGroup);
        Map<Rank, Integer> statistics = lottoResult.makeStatistics();
        lottoResultsDto = new LottoResultsDto(statistics, lottoResult.makeProfit(statistics, new Money(4000)));
    }

    @DisplayName("로또 당첨 통계를 출력하기 위한 기능이 잘 수행되는 지 결과 비교")
    @Test
    void 로또_결과_계산() {
        Map<Rank, Integer> result = new LinkedHashMap<Rank, Integer>() {{
            put(Rank.FIFTH, 1);
            put(Rank.FOURTH, 1);
            put(Rank.THIRD, 1);
            put(Rank.SECOND, 1);
            put(Rank.FIRST, 0);
        }};
        assertThat(lottoResultsDto.getResults()).containsAllEntriesOf(result);
    }

    @Test
    void 로또_수익률_계산() {
        float expected = (float) (50000 + 5000 + 30000000 + 1500000) / 4000;
        assertThat(lottoResultsDto.getProfit()).isEqualTo(expected);
    }
}