package com.tu.ecommerce.util;

import com.tu.ecommerce.dao.SystemParameterRepository;
import com.tu.ecommerce.entity.SystemParameter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyUtil {

    private final SystemParameterRepository systemParameterRepository;

    public CurrencyUtil(SystemParameterRepository systemParameterRepository) {
        this.systemParameterRepository = systemParameterRepository;
    }

    public BigDecimal calculatePrice(BigDecimal price, SystemParameter showBgnCurrencyFirstParam) {
        SystemParameter bgnEurExchangeRateParam = this.systemParameterRepository
                .findByCode(Constants.BGN_EUR_EXCHANGE_RATE_CODE);

        BigDecimal exchangeRate = new BigDecimal(bgnEurExchangeRateParam.getValue());
        if ("1".equals(showBgnCurrencyFirstParam.getValue())) {
            return price.divide(exchangeRate, 2, RoundingMode.HALF_UP);
        } else {
            return price.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
        }
    }
}
