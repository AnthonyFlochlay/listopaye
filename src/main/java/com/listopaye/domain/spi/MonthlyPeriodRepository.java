package com.listopaye.domain.spi;

import com.listopaye.domain.MonthlyPeriod;
import com.listopaye.domain.spi.stub.MonthlyPeriodId;

public interface MonthlyPeriodRepository {
    MonthlyPeriod getMonthlyPeriod(MonthlyPeriodId id);
}
