package com.staffinity.recruiting.common.dto;

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class PageRequest {
    private String sortDirection = "ASC";
    private String sortBy;
    private int size = 20;
    private int page = 0;
}


