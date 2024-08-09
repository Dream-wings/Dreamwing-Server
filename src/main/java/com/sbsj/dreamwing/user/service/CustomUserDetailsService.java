package com.sbsj.dreamwing.user.service;

import com.sbsj.dreamwing.user.dto.UserDTO;
import com.sbsj.dreamwing.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * UserDetailsService interface
 * @apiNote 사용자 권한 설정 등의 기능을 제공
 * @author 정은찬
 * @since 2024.07.30
 *
 * <pre>
 * 수정일        	수정자       				    수정내용
 * ----------  ----------------    ---------------------------------
 *  2024.07.30     	정은찬        		            최초 생성
 * </pre>
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    /**
     * 사용자 ID를 통해 사용자 정보를 로드하는 메서드
     * @author 정은찬
     * @param userId
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userMapper.selectUserByUserId(Long.parseLong(userId))
                .map(user -> addAuthorities(user))
                .orElseThrow(() -> new RuntimeException(userId + "> 찾을 수 없습니다."));
    }

    /**
     * 사용자 권한을 설정하는 메서드
     * @author 정은찬
     * @param userDTO
     * @return UserDTO
     */
    private UserDTO addAuthorities(UserDTO userDTO) {

        if(!userDTO.getUserId().equals("loginId")) {
            userDTO.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        }
        else {
            userDTO.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        return userDTO;
    }
}
