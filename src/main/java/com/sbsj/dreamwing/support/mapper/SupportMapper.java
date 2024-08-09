package com.sbsj.dreamwing.support.mapper;

import com.sbsj.dreamwing.support.dto.GetDetailSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 후원 매퍼 인터페이스
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 메서드 추가
 * 2024.07.28   임재성        모든 후원 리스트 조회 추가
 * </pre>
 */
@Mapper
public interface SupportMapper {

    /**
     * 후원 총 횟수,금액 조회
     * @author 정은지
     * @return GetTotalSupportResponseDTO
     */
    GetTotalSupportResponseDTO selectTotalSupport();

    /**
     * 후원 리스트 조회
     * @author 정은지
     * @return List<GetSupportListResponseDTO>
     */
    List<GetSupportListResponseDTO> selectSupportList();

    //public List<GetSupportListResponseDTO> selectAllSupportList();
    // 사용자 포인트 가져오기
    int getUserPoints(@Param("userId") long userId);

    // 사용자 포인트 업데이트
    int updateUserPoints(@Param("userId") long userId, @Param("newPoints") int point);

    //후원 항목 포인트 가져오기
    int getSupportPoints(long supportId);

    // 후원 항목 포인트 업데이트
    int updateSupportPoints(@Param("supportId") long supportId, @Param("pointsToAdd") int pointsToAdd);

//    List<GetSupportListResponseDTO> selectSupportListWithPagination(
//            @Param("offset") int offset,
//            @Param("size") int size
//    );
List<GetSupportListResponseDTO> selectSupportListWithFilters(
        @Param("offset") int offset,
        @Param("size") int size,
        @Param("status") int status,
        @Param("category") int category
);
    GetDetailSupportResponseDTO selectSupportDetail(@Param("supportId") long supportId);

    int insertSupportHistory(@Param("userId") long userId,
                             @Param("supportId")long supportId,
                             @Param("pointsToAdd") int amount);

    int insertPointHistory(@Param("userId")long userId,
                           @Param("supportId")long supportId,
                           @Param("pointsToAdd")int amount,
                           @Param("title")String title,
                           @Param("type")int type);
}