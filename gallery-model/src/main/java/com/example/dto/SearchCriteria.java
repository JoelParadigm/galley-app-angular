package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchCriteria {

    private String imageTitle = "";
    private String description = "";
    private List<String> tagNames = new ArrayList<>();
}
