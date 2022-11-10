package com.oscar.springbootstudy.base;

import com.oscar.springbootstudy.commons.Constain;
import lombok.Data;

@Data
public class BaseDto {
    protected Integer pageIndex = 1;
    protected Integer pageSize = Constain.PAGE_SIZE;
}
