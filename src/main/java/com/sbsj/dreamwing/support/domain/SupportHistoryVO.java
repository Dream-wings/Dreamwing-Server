package com.sbsj.dreamwing.support.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SupportHistoryVO {
    private long historyId;
    private long userId;
    private long supportId;
    private int point;
    private Date createdDate;
}
