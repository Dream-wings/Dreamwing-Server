package com.sbsj.dreamwing.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 봉사활동 인증 DTO
 * @author 정은지
 * @since 2024.08.03
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.03 	정은지        최초 생성
 * </pre>
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationVolunteerRequestDTO {
    private long userId;
    private long volunteerId;
    private String imageUrl;
}