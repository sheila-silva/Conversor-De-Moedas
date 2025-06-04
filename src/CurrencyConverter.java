public class CurrencyConverter {
    private ExchangeRateService service = new ExchangeRateService();

    public double convert(Currency from, Currency to, double amount) {
        ExchangeRate rate = service.getExchangeRate(from.getCode(), to.getCode());
        return rate.apply(amount);
    }

    public ExchangeRateService getService() {
        return service;
    }
}