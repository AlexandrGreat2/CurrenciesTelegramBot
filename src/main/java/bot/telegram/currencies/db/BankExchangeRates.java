package bot.telegram.currencies.db;

import bot.telegram.currencies.exchange.ExchangeRateDTO;

import java.util.Objects;

public class BankExchangeRates {
    private ExchangeRateDTO NBU;
    private ExchangeRateDTO PrivatBank;
    private ExchangeRateDTO MonoBank;

    public ExchangeRateDTO getNBU() {
        return NBU;
    }

    public void setNBU(ExchangeRateDTO NBU) {
        this.NBU = NBU;
    }

    public ExchangeRateDTO getPrivatBank() {
        return PrivatBank;
    }

    public void setPrivatBank(ExchangeRateDTO privatBank) {
        PrivatBank = privatBank;
    }

    public ExchangeRateDTO getMonoBank() {
        return MonoBank;
    }

    public void setMonoBank(ExchangeRateDTO monoBank) {
        MonoBank = monoBank;
    }

    @Override
    public String toString() {
        return "BankExchangeRates{" +
                "NBU=" + NBU +
                ", PrivatBank=" + PrivatBank +
                ", MonoBank=" + MonoBank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankExchangeRates that = (BankExchangeRates) o;
        return Objects.equals(NBU, that.NBU) &&
                Objects.equals(PrivatBank, that.PrivatBank) &&
                Objects.equals(MonoBank, that.MonoBank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NBU, PrivatBank, MonoBank);
    }
}

