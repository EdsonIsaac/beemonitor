package io.github.edsonisaac.beemonitor.projections;

/**
 * The interface Mensuration projection.
 *
 * @author Edson Isaac
 */
public interface MensurationProjection {

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    Double getTemperature();

    /**
     * Gets humidity.
     *
     * @return the humidity
     */
    Double getHumidity();

    /**
     * Gets weight.
     *
     * @return the weight
     */
    Double getWeight();
}