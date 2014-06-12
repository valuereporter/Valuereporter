package org.valuereporter.value;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Service
public class ValueMethodService implements QueryOperations, WriteOperations{
    @Override
    public List<ValuableMethod> findValuableMethods(String prefix) {
        return buildStubList();
    }

    @Override
    public List<ValuableMethod> findValuableDistribution(String prefix) {
        List<ValuableMethod> valuableMethods = buildStubList();
        ValuableMethod unused = new ValuableMethod("unused", 0);
        ValuableMethod lowUsage = new ValuableMethod("less than 5", 0);
        ValuableMethod highUsage = new ValuableMethod("HighUsage", 0);
        for (ValuableMethod valuableMethod : valuableMethods) {
            long usageCount = valuableMethod.getUsageCount();
            if (usageCount < 1) {
                unused.addUsageCount(1);
            } else if (usageCount < 5) {
                lowUsage.addUsageCount(1);
            } else {
                highUsage.addUsageCount(1);
            }
        }

        List<ValuableMethod> valuableDistribution = new ArrayList<>(3);
        valuableDistribution.add(unused);
        valuableDistribution.add(lowUsage);
        valuableDistribution.add(highUsage);
        return valuableDistribution;
    }

    private List<ValuableMethod> buildStubList() {
        List<ValuableMethod> valuableMethods = new ArrayList<>();
        valuableMethods.add(new ValuableMethod("methodInUse", 1));
        valuableMethods.add(new ValuableMethod("methodInUse2", 2));
        valuableMethods.add(new ValuableMethod("methodInUse5", 5));
        valuableMethods.add(new ValuableMethod("methodInUse10", 10));
        valuableMethods.add(new ValuableMethod("unusedMethod", 0));
        return valuableMethods;
    }
}
