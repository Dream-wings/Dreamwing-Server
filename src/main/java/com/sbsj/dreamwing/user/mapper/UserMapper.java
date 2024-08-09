package com.sbsj.dreamwing.user.mapper;

import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.domain.UserVO;
import com.sbsj.dreamwing.user.domain.MyVolunteerVO;
import com.sbsj.dreamwing.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

/**
 * 회원 관련 데이터베이스 작업을 처리하는 Mapper interface
 * @apiNote 사용자 정보 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        	수정자       				    수정내용
 * ----------  ----------------    --------------------------------------------------------------------------
 *  2024.07.28     	정은찬        		최초 생성
 *  2024.07.29      정은찬               로그인 아이디 존재 여부 확인 메서드 추가
 *  2024.07.30      정은찬               loginId를 통해 회원 정보 조회 메서드 추가
 *  2024.07.31      정은찬               userId를 통해 회원 정보 조회 메서드, 회원 탈퇴 메서드 추가
 *  2024.07.31      정은찬               회원 정보 업데이트 메서드 및 포인트 내역, 후원 내역 조회 메서드 추가
 *  2024.08.03      정은찬               회원 총 후원 포인트 조회 및 봉사 활동 내역 조회 메서드 추가
 *  2024.08.04      정은찬               페이징 처리를 위해 포인트 내역, 후원 내역 조회 메서드 수정
 *  2024.08.05      정은찬               페이징 처리를 위해 봉사활동 내역 조회 메서드 수정
 * </pre>
 */
@Mapper
public interface UserMapper {
    /**
     * 회원 정보 등록
     * @author 정은찬
     * @param userVO
     * @return int
     */
    int insertUser(UserVO userVO);

    /**
     * 로그인 아이디 존재 여부 확인
     * @author 정은찬
     * @param loginId
     * @return String
     */
    String checkLoginIdExistence(String loginId);

    /**
     * loginId를 통해 회원 정보 조회
     * @author 정은찬
     * @param loginId
     * @return Optional<UserDTO>
     */
    Optional<UserDTO> selectUserByLoginId(String loginId);

    /**
     * userId를 통해 회원 정보 조회
     * @author 정은찬
     * @param userId
     * @return Optional<UserDTO>
     */
    Optional<UserDTO> selectUserByUserId(long userId);

    /**
     * 회원 탈퇴
     * @author 정은찬
     * @param userId
     * @return int
     */
    int withdraw(long userId);

    /**
     * 회원 정보 업데이트
     * @author 정은찬
     * @param userVO
     * @return int
     */
    int updateUserInfo(UserVO userVO);

    /**
     * 회원 포인트 내역 조회
     * @author 정은찬
     * @param userId
     * @param offset
     * @param size
     * @return List<MyPointVO>
     */
    List<MyPointVO> getUserPointVOList(long userId, int offset, int size);

    /**
     * 회원 후원 내역 조회
     * @author 정은찬
     * @param userId
     * @param offset
     * @param size
     * @return List<MySupportVO>
     */
    List<MySupportVO> getUserSupportVOList(long userId, int offset, int size);

    /**
     * 회원 총 후원한 포인트
     * @author 정은찬
     * @param userId
     * @return Integer
     */
    Integer selectTotalSupportPoint(long userId);

    /**
     * 회원 봉사 활동 내역 조회
     * @author 정은찬
     * @param userId
     * @param offset
     * @param size
     * @return List<MyVolunteerVO>
     */
    List<MyVolunteerVO> getUserVolunteerVOList(long userId, int offset, int size);

}
