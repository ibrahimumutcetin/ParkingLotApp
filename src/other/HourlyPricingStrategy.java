package other;

/* Strategy Pattern */
public class HourlyPricingStrategy implements PricingStrategy {

    private static final int HOURLY_RATE = 20;

    @Override
    public int calculateFee(long parkingDurationMs) {
        long hours = (parkingDurationMs / 3600000) + 1;  
        return (int) (hours * HOURLY_RATE);
    }

    @Override
    public String getStrategyName() {
        return "Saatlik (20 TL/saat)";
    }
}
