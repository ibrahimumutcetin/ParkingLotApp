package other;

/* Strategy Pattern */
public class DailyPricingStrategy implements PricingStrategy {

    private static final int DAILY_RATE = 100;

    @Override
    public int calculateFee(long parkingDurationMs) {
        long days = (parkingDurationMs / 86400000) + 1;  
        return (int) (days * DAILY_RATE);
    }

    @Override
    public String getStrategyName() {
        return "Günlük (100 TL/gün)";
    }
}
