package bot.telegram.currencies.exchange;

public abstract class BankService {
    /**
     * Performs a request to an external service to obtain the exchange rate from currency A to currency B
     * and returns an array with the buying and selling rates rounded to the specified accuracy.
     *
     * @param currencyA the code of currency A
     * @param currencyB the code of currency B
     * @param accuracy  the number of decimal places to round the buying and selling rates to
     * @return an array containing the buying and selling rates in the specified order
     */
    abstract double[] request(String currencyA, String currencyB, int accuracy);
    /**
     * Rounds a double value to a specified number of decimal places.
     *
     * @param number   the number to be rounded
     * @param accuracy the number of decimal places to round to
     * @return the rounded value
     */
    protected double roundDouble(double number, int accuracy){
        double multiplier = Math.pow(10,accuracy);
        return Math.round(number*multiplier)/multiplier;
    }
}
