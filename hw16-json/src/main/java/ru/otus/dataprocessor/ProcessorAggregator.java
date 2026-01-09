package ru.otus.dataprocessor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorAggregator.class);

    /***
     * группирует выходящий список по name, при этом суммирует поля value
     * @param data - список Measurement
     * @return сгруппированный список Measurement по name и суммированным value
     */
    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> groupingMeasurement = data
                .stream()
                .collect(Collectors.groupingBy(Measurement::name,
                        Collectors.summingDouble(Measurement::value)));

        logger.info("List<Measurement> сгруппирован по name и суммированн по value в Map<String, Double>: {}", groupingMeasurement);
        return groupingMeasurement;
    }
}
