package com.sbsj.dreamwing.user.mapper;

import com.sbsj.dreamwing.user.domain.MyPointVO;
import com.sbsj.dreamwing.user.domain.MySupportVO;
import com.sbsj.dreamwing.user.domain.UserVO;
import com.sbsj.dreamwing.user.domain.MyVolunteerVO;
import com.sbsj.dreamwing.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 관련 데이터베이스 작업을 처리하는 Mapper interface
 * @apiNote 사용자 정보 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
 *  2024.07.28     	정은찬        		       최초 생성
 *  2024.07.29      정은찬                      로그인 아이디 존재 여부 확인 추가
 *  2024.07.30      정은찬                      loginId를 통해 회원 정보 가져오기 추가
 *  2024.07.31      정은찬                      userId를 통해 회원 정보 가져오기 추가
 *  2024.07.31      정은찬                      사용자 정보 업데이트하기 및 포인트 내역, 후원 내역 가져오기 추가
 *  2024.08.03      정은찬                      사용자 총 후원 포인트 가져오기 및 봉사 활동 내역 가져오기 추가
 *  2024.08.04      정은찬                      페이징 처리를 위해 포인트 내역, 후원 내역 가져오기 수정
 *  2024.08.05      정은찬                      페이징 처리를 위해 봉사활동 내역 가져오기 수정
 * </pre>
 */
@Mapper
public interface UserMapper {
    int insertUser(UserVO userVO);
    String checkLoginIdExistence(String loginId);
    Optional<UserDTO> selectUserByLoginId(String loginId);
    Optional<UserDTO> selectUserByUserId(long userId);
    int withdraw(long userId);
    int updateUserInfo(UserVO userVO);
    List<MyPointVO> getUserPointVOList(long userId, int offset, int size);
    List<MySupportVO> getUserSupportVOList(long userId, int offset, int size);
    int selectTotalSupportPoint(long userId);
    List<MyVolunteerVO> getUserVolunteerVOList(long userId, int offset, int size);

}
