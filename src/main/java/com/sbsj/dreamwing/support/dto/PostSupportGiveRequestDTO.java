package com.sbsj.dreamwing.support.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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


@NoArgsConstructor
@Data
public class PostSupportGiveRequestDTO {
    private long supportId; // 사용자의 ID
    private long userId; // 지원 항목의 ID
    private int amount; // 기부할 포인트
    private String title;
    private int type;

    public PostSupportGiveRequestDTO(Long supportId, Long userId, Integer amount, String title, int type) {
        this.supportId = supportId;
        this.userId = userId;
        this.amount = amount;
        this.title = title;
        this.type = type;
    }
}