package org.valuereporter.implemented;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Service
public class ImplementedMethodService implements QueryOperations, WriteOperations {
    @Override
    public List<ImplementedMethod> findImplementdedMethodsByName(String prefix, String name) {
        return null;
    }

    @Override
    public long addImplementedMethods(String prefix, List<ImplementedMethod> implementedMethods) {
        return 0;
    }
}
