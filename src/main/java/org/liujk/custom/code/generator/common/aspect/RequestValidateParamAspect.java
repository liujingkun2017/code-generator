package org.liujk.custom.code.generator.common.aspect;

import org.springframework.core.Ordered;

public class RequestValidateParamAspect implements Ordered {
    @Override
    public int getOrder() {
        return 0;
    }
}
