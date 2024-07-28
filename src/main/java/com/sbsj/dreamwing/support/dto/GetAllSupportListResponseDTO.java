package com.sbsj.dreamwing.support.dto;

import lombok.Data;
import java.util.Date;

/**
 * 모든 후원 리스트 조회 DTO
 * @author 임재성
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	임재성        최초 생성
 * </pre>
 */
@Data
public class GetAllSupportListResponseDTO {
    private long supportId;
    private int goalPoint;
    private int currentPoint;
    private String title;
    private Date endDate;
    private String imageUrl;
    private String dDay;
}
