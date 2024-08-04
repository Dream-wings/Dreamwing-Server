package com.sbsj.dreamwing.admin.service;

import com.sbsj.dreamwing.admin.dto.*;
import com.sbsj.dreamwing.admin.mapper.AdminMapper;
import com.sbsj.dreamwing.mission.domain.QuizVO;
import com.sbsj.dreamwing.util.S3Uploader;
import com.sbsj.dreamwing.volunteer.mapper.VolunteerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;


/**
 * 관리자 서비스 테스트
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        봉사활동 신청 승인 테스트 추가
 * 2024.07.29   정은지        봉사활동 인증 테스트 추가
 * 2024.08.04   정은지        봉사활동 신청 대기 리스트 조회 테스트 추가
 * </pre>
 */

@Slf4j
@SpringBootTest
public class AdminServiceTests {

//    @Mock
//    private AdminService service;

    @Autowired
    private AdminMapper mapper;

    @Autowired
    private S3Uploader s3Uploader;

    private AdminVolunteerRequestDTO request;

    @Autowired
    private AdminServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new AdminVolunteerRequestDTO();
        request.setTitle("환경 보호 봉사");
        request.setContent("환경 정화를 위한 봉사활동입니다.");
        request.setType(1);
        request.setCategory(2);
        request.setStartDate(LocalDateTime.of(2024, 8, 1, 9, 0));
        request.setEndDate(LocalDateTime.of(2024, 8, 1, 18, 0));
        request.setAddress("서울시 강남구");
        request.setTotalCount(100);
        request.setStatus(1);
        request.setRecruitStartDate(LocalDateTime.of(2024, 7, 20, 9, 0));
        request.setRecruitEndDate(LocalDateTime.of(2024, 7, 31, 18, 0));
        request.setLatitude("37.4979");
        request.setLongitude("127.0276");
    }






    @Test
    @DisplayName("봉사활동 신청 승인 서비스 테스트")
    public void testApproveVolunteerRequest() throws Exception {
        // given
        UpdateVolunteerStatusRequestDTO dto = new UpdateVolunteerStatusRequestDTO();
        dto.setVolunteerId(3L);
        dto.setUserId(1L);

        // when
        boolean success = service.approveVolunteerRequest(dto);

        // then
        assertTrue(success, "봉사활동 신청 승인 실패");
    }

    @Test
    @DisplayName("봉사활동 인증 포인트 부여 서비스 테스트")
    public void testVerifyVolunteerRequest() throws Exception {
        // given
        AwardVolunteerPointsRequestDTO dto = new AwardVolunteerPointsRequestDTO();
        dto.setVolunteerId(3L);
        dto.setUserId(1L);
        dto.setActivityType(1);
        dto.setActivityTitle("봉사활동");
        dto.setPoint(5000);

        // when
        boolean success = service.awardVolunteerPoints(dto);

        // then
        assertTrue(success, "봉사활동 인증 실패");
    }

//    @Test
//    @DisplayName("봉사 공고 생성 서비스 테스트")
//    public void testCreateVolunteer() {
//        // given
//        AdminVolunteerRequestDTO dto = new AdminVolunteerRequestDTO();
//        dto.setTitle("서비스 생성 봉사 공고");
//        dto.setContent("서비스 생성 봉사 공고 내용");
//        dto.setType(1);
//        dto.setCategory(2);
//        dto.setStartDate(Timestamp.valueOf("2024-08-01 09:00:00").toLocalDateTime());
//        dto.setEndDate(Timestamp.valueOf("2024-08-01 12:00:00").toLocalDateTime());
//        dto.setAddress("서비스 생성 주소");
//        dto.setTotalCount(20);
//        dto.setStatus(0);
//        dto.setRecruitStartDate(Timestamp.valueOf("2024-07-01 00:00:00").toLocalDateTime());
//        dto.setRecruitEndDate(Timestamp.valueOf("2024-07-31 23:59:59").toLocalDateTime());
//        dto.setImageUrl("https://example.com/service_image.jpg");
//        dto.setLatitude("37.5665");
//        dto.setLongitude("126.978");
//
//        // when
//        int success = service.createVolunteer(dto);
//
//        // then
//        Assertions.assertTrue(success > 0, "봉사 공고 생성 실패");
//    }

    @Test
    public void testCreateVolunteerWithImage() throws IOException {
        // 실제 파일 로드
        ClassPathResource resource = new ClassPathResource("test-image.png");
        MockMultipartFile imageFile = new MockMultipartFile("file", "test-image.jpg", "image/jpeg", resource.getInputStream());

        // Set the image file and image URL on the request
        String imageUrl = s3Uploader.uploadFile(imageFile);
        request.setImageFile(imageFile);
        request.setImageUrl(imageUrl);

        // Call the service method
        int result = service.createVolunteer(request);

        // Assert the result
        assertEquals(1, result, "봉사 공고 생성 실패");

        // Verify interactions
        // Use actual `assert` for the real database and S3
    }

    @Test
    @DisplayName("봉사 공고 수정 서비스 테스트")
    public void testUpdateVolunteer() {
        // given
        AdminVolunteerRequestDTO dto = new AdminVolunteerRequestDTO();
        dto.setVolunteerId(19L); // Assuming this ID exists
        dto.setTitle("서비스 수정 봉사 공고");
        dto.setContent("서비스 수정 봉사 공고 내용");
        dto.setType(1);
        dto.setCategory(2);
        dto.setStartDate(Timestamp.valueOf("2024-08-01 09:00:00").toLocalDateTime());
        dto.setEndDate(Timestamp.valueOf("2024-08-01 12:00:00").toLocalDateTime());
        dto.setAddress("서비스 수정 주소");
        dto.setTotalCount(30);
        dto.setStatus(1);
        dto.setRecruitStartDate(Timestamp.valueOf("2024-07-01 00:00:00").toLocalDateTime());
        dto.setRecruitEndDate(Timestamp.valueOf("2024-07-31 23:59:59").toLocalDateTime());
        dto.setImageUrl("https://dsdsdcsdcscdm/service_update_image.jpg");
        dto.setLatitude("37.567");
        dto.setLongitude("126.979");

        // when
        int success = service.updateVolunteer(dto);

        // then
        Assertions.assertTrue(success ==1, "봉사 공고 수정 실패");
    }

    @Test
    @DisplayName("봉사 공고 삭제 서비스 테스트")
    public void testDeleteVolunteer() {
        // given
        long volunteerId = 8L; // Assuming this ID exists

        // when
        int success = service.deleteVolunteer(volunteerId);

        // then
        Assertions.assertTrue(success > 0, "봉사 공고 삭제 실패");
    }

    @Test
    @DisplayName("봉사 공고 목록 조회 서비스 테스트")
    public void testGetVolunteerList() {
        // when
        List<AdminVolunteerResponseDTO> list = service.getVolunteerList();

        // then
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("봉사활동 신청 대기 리스트 조회 서비스 테스트")
    public void testGetVolunteerRequestPendingList() {
        // given
        int page = 1;
        int size = 10;

        // when
        List<VolunteerRequestPendingListResponseDTO> dto
                = mapper.selectVolunteerRequestPendingList(page, size);

        // then
        Assertions.assertNotNull(dto);
    }

    @Test
    @DisplayName("봉사활동 신청 대기 상세 조회 서비스 테스트")
    public void testGetVolunteerRequestPendingDetail() {
        // given
        int volunteerId = 1;
        int userId = 1;

        // when
        VolunteerRequestPendingDetailResponseDTO dto
                = mapper.selectVolunteerRequestPendingDetail(volunteerId, userId);

        // then
        assertThat(dto.getUserId()).isEqualTo(userId);
    }
}
