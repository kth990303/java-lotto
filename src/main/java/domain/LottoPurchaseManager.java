package domain;

import java.util.List;

/**
 * LottoPurchaseManager.java
 * 로또 구매 로직을 담당하는 유틸리티 클래스
 *
 * @author Kimun Kim, github.com/tributetothemoon
 * @author Daeun Lee, github.com/da-nyee
 */

public class LottoPurchaseManager {
    private Money budget;

    private LottoPurchaseManager(Money money) {
        this.budget = money;
    }

    public static LottoPurchaseManager from(Money money) {
        return new LottoPurchaseManager(money);
    }

    public boolean canAfford(int quantity) {
        return this.budget.toLong() >= LottoTicket.PRICE.multiply(quantity).toLong();
    }

    public Money remainBudget() {
        return this.budget;
    }

    public LottoTicket buyAutomatically() {
        this.budget = budget.subtract(LottoTicket.PRICE);
        return LottoTicket.of(LottoNumberRepository.shuffleLottoNumbers());
    }

    public LottoTicket buyManually(List<Integer> lottoNumbers) throws IllegalArgumentException {
        LottoTicket lottoTicket = LottoTicket.valueOf(lottoNumbers);
        this.budget = budget.subtract(LottoTicket.PRICE);
        return lottoTicket;
    }
}