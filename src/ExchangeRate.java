public class ExchangeRate {
    private Currency from;
    private Currency to;
    private double rate;

    public ExchangeRate(Currency from, Currency to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public double apply(double amount) {
        return amount * rate;
    }
}