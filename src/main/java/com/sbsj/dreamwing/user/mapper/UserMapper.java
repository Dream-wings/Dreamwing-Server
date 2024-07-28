package com.sbsj.dreamwing.user.mapper;

import com.sbsj.dreamwing.user.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

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
 * </pre>
 */
@Mapper
public interface UserMapper {
    int insertUser(UserVO userVO);
}
