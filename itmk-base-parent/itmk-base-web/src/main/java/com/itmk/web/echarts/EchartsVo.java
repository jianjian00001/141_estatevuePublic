package com.itmk.web.echarts;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author java实战基地
 * @Version 2383404558
 */
@Data
public class EchartsVo {
    private List<String> names =  new ArrayList<>();
    private List<Integer> counts = new ArrayList<>();
}
