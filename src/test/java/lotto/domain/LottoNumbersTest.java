package lotto.domain;

import lotto.domain.vo.LottoNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoNumbersTest {
    private static Stream<Arguments> provideOtherNumbersAndExpected() {
        return Stream.of(
                Arguments.of(Arrays.asList("1", "2", "3", "4", "5", "6"), 6),
                Arguments.of(Arrays.asList("1", "2", "3", "4", "5", "7"), 5),
                Arguments.of(Arrays.asList("1", "2", "3", "4", "7", "8"), 4),
                Arguments.of(Arrays.asList("1", "2", "3", "7", "8", "9"), 3),
                Arguments.of(Arrays.asList("1", "2", "7", "8", "9", "10"), 2),
                Arguments.of(Arrays.asList("1", "7", "8", "9", "10", "11"), 1),
                Arguments.of(Arrays.asList("7", "8", "9", "10", "11", "12"), 0)
        );
    }

    @Test
    @DisplayName("로또 번호들을 입력받아 로또 한 개가 올바르게 생성되는지 확인한다.")
    void create_lottoNumbers() {
        //given
        final List<String> correctNumberValues = Arrays.asList("1", "2", "3", "4", "5", "6");
        //when
        LottoNumbers lottoNumbers = new LottoNumbers(correctNumberValues);
        List<LottoNumber> actualLottoNumberValues = lottoNumbers.getValues();
        //then
        assertThat(actualLottoNumberValues).containsExactly(
                LottoNumber.from("1"), LottoNumber.from("2"), LottoNumber.from("3"),
                LottoNumber.from("4"), LottoNumber.from("5"), LottoNumber.from("6")
        );
    }

    @Test
    @DisplayName("로또 번호들 간에 중복이 있으면 예외를 발생시킨다.")
    void create_ExceptionByDuplicatedLottoNumbers() {
        //given
        final List<String> duplicatedNumberValues = Arrays.asList("1", "1", "2", "3", "4", "5");
        final String expectedExceptionMessage = "같은 줄 로또 번호 간에 중복이 존재합니다.";
        //when then
        assertThatThrownBy(() -> new LottoNumbers(duplicatedNumberValues))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMessage);
    }

    @Test
    @DisplayName("로또 숫자들을 오름차순으로 정렬한다.")
    void create_SortingAscending() {
        //given
        final List<String> numbers = Arrays.asList("7", "4", "5", "3", "6", "2");
        final LottoNumbers lottoNumbers = new LottoNumbers(numbers);
        final List<String> otherNumbers = Arrays.asList("2", "3", "6", "7", "4", "5");
        final LottoNumbers otherLottoNumbers = new LottoNumbers(otherNumbers);
        final LottoNumber expectedFirstLottoNumber = LottoNumber.from("2");
        //when
        final LottoNumber actualFirstLottoNumber = lottoNumbers.getValues().get(0);
        //then
        assertThat(lottoNumbers).isEqualTo(otherLottoNumbers);
        assertThat(actualFirstLottoNumber).isEqualTo(expectedFirstLottoNumber);
    }

    @Test
    @DisplayName("로또 한 줄에서의 숫자 개수가 6개가 아니면 예외를 발생시킨다.")
    void create_ExceptionByNotSixCountOfNumbers() {
        //given
        final List<String> invalidCountNumbers = Arrays.asList("1", "2", "3", "4", "5");
        final String expectedExceptionMessage = "로또 숫자는 한 줄에 6개여야 합니다.";
        //when then
        assertThatThrownBy(() -> new LottoNumbers(invalidCountNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMessage);
    }

    @ParameterizedTest
    @DisplayName("보너스 숫자를 제외하고, 당첨된 숫자의 개수를 반환한다.")
    @MethodSource("provideOtherNumbersAndExpected")
    void getMatchNumbersCount(final List<String> otherNumbers, final int expected) {
        //given
        final LottoNumbers target = new LottoNumbers(Arrays.asList("1", "2", "3", "4", "5", "6"));
        final LottoNumbers otherLottoNumbers = new LottoNumbers(otherNumbers);
        //when
        final int actual = target.getMatchCount(otherLottoNumbers);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
