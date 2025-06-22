package com.misolab.aistock.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketIndex {
    private String name;
    private double value;
    private double change;
    private double changePercent;
    private String description;
}