package com.misolab.aistock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ParsedQuery {

    private String intent;

    @JsonProperty("stock_code")
    private String stockCode;

    @JsonProperty("stock_name")
    private String stockName;

    private String date;

    @JsonProperty("index_type")
    private String indexType;

    @JsonProperty("ranking_type")
    private String rankingType;

    @JsonProperty("analysis_type")
    private String analysisType;

    private Integer limit;

    private List<String> metrics;

    private String comparison;

    @JsonProperty("sort_order")
    private String sortOrder;
} 