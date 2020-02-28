package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateRandomLottoTicketTest {
    @Test
    @DisplayName("randomLottoTicketFactory의 로또 생성이 수동 로또 티켓을 포함해서 생성한다")
    void randomLottoTicketTest() {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        lottoNumbers.add(new LottoNumber(1));
        lottoNumbers.add(new LottoNumber(2));
        lottoNumbers.add(new LottoNumber(3));
        lottoNumbers.add(new LottoNumber(4));
        lottoNumbers.add(new LottoNumber(5));
        lottoNumbers.add(new LottoNumber(6));
        LottoTicket lottoTicket = new LottoTicket(lottoNumbers);
        List<LottoTicket> lottoTicketList = new ArrayList<>();
        lottoTicketList.add(lottoTicket);
        PurchasingAmount purchasingAmount = new PurchasingAmount(10000);
        CreateRandomLottoTicket createRandomLottoTicket = new CreateRandomLottoTicket();
        List<LottoTicket> result = createRandomLottoTicket.buyLottoTickets(purchasingAmount, lottoTicketList);

        assertThat((int) result.stream().count()).isEqualTo(11);
    }
}