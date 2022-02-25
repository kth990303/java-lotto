package lotto.domain.generator;

import lotto.domain.LottoNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoRandomGeneratorTest {
    @Test
    @DisplayName("숫자 값을 입력받아, 입력받은 값만큼의 로또 개수를 서로 중복없이 생성해 반환한다.")
    void generate() {
        //given
        final int numberOfGenerating = 5;
        final LottoGenerator lottoRandomGenerator = new LottoRandomGenerator();
        //when
        final List<LottoNumbers> generated = lottoRandomGenerator.generateLottoNumbersGroup(numberOfGenerating);
        final int actualDistinctCount = (int) generated.stream()
                .distinct()
                .count();
        //then
        assertThat(actualDistinctCount).isEqualTo(numberOfGenerating);
    }
}
