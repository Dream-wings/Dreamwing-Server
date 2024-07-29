package com.sbsj.dreamwing.support.dto;

import lombok.Data;
/**
 * 후원 요청 DTO
 * @author 임재성
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.29  	임재성        최초 생성
 * </pre>
 */



@Data
public class PostSupportGiveRequestDTO {
    private long userId; // 사용자의 ID
    private long supportId; // 지원 항목의 ID
    private int point; // 기부할 포인트
}