package other;

/**
 * Strategy Pattern  */
public interface PricingStrategy {
    int calculateFee(long parkingDurationMs);
    String getStrategyName();
}
