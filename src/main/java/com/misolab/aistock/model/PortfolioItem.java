package com.misolab.aistock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PortfolioItem {
    private String stockName;
    private String stockCode;
    private int quantity;
    private double purchasePrice; // 매수 평균가
    private double currentPrice; // 현재가
    private double profitOrLoss; // 손익 금액
    private double returnRate; // 수익률 (%)
} 