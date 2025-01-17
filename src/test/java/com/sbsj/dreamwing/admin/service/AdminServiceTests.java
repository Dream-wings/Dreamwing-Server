package com.sbsj.dreamwing.admin.service;

import com.sbsj.dreamwing.admin.dto.*;
import com.sbsj.dreamwing.util.S3Uploader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Autowired
    private AdminService service;
    private S3Uploader s3Uploader;
    private AdminVolunteerRequestDTO request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new AdminVolunteerRequestDTO();
        request.setTitle("환경 보호 봉사");
        request.setContent("환경 정화를 위한 봉사활동입니다.");
        request.setType(1);
        request.setCategory(2);
//        request.setStartDate(LocalDateTime.of(2024, 8, 1, 9, 0));
//        request.setEndDate(LocalDateTime.of(2024, 8, 1, 18, 0));
        request.setAddress("서울시 강남구");
        request.setTotalCount(100);
        request.setStatus(1);
        request.setRecruitStartDate(LocalDateTime.of(2024, 7, 20, 9, 0));
        request.setRecruitEndDate(LocalDateTime.of(2024, 7, 31, 18, 0));
//        request.setLatitude("37.4979");
//        request.setLongitude("127.0276");
    }

    @Test
    @DisplayName("봉사활동 신청 승인 서비스 테스트")
    public void testApproveVolunteerRequest() throws Exception {
        // given
        UpdateVolunteerStatusRequestDTO dto = UpdateVolunteerStatusRequestDTO.builder()
                .volunteerId(3L)
                .userId(1L)
                .build();

        // when
        boolean success = service.approveVolunteerRequest(dto);

        // then
        assertTrue(success, "봉사활동 신청 승인 실패");
    }

    @Test
    @DisplayName("봉사활동 인증 포인트 부여 서비스 테스트")
    public void testVerifyVolunteerRequest() throws Exception {
        // given
        AwardVolunteerPointsRequestDTO dto =
                AwardVolunteerPointsRequestDTO.builder()
                        .volunteerId(3L)
                        .userId(1L)
                        .activityType(1)
                        .activityTitle("봉사활동")
                        .point(5000)
                        .build();

        // when
        boolean success = service.awardVolunteerPoints(dto);

        // then
        assertTrue(success, "봉사활동 인증 실패");
    }

    @Test
    @DisplayName("봉사 공고 생성 서비스 테스트")
    public void testCreateVolunteer() {
        // given
        AdminVolunteerRequestDTO dto = new AdminVolunteerRequestDTO();
        dto.setTitle("서비스 생성 봉사 공고");
        dto.setContent("서비스 생성 봉사 공고 내용");
        dto.setType(1);
        dto.setCategory(2);
        dto.setVolunteerStartDate(Timestamp.valueOf("2024-08-01 09:00:00").toLocalDateTime());
        dto.setVolunteerEndDate(Timestamp.valueOf("2024-08-01 12:00:00").toLocalDateTime());
        dto.setAddress("서비스 생성 주소");
        dto.setTotalCount(20);
        dto.setStatus(0);
        dto.setRecruitStartDate(Timestamp.valueOf("2024-07-01 00:00:00").toLocalDateTime());
        dto.setRecruitEndDate(Timestamp.valueOf("2024-07-31 23:59:59").toLocalDateTime());
        dto.setImageUrl("https://example.com/service_image.jpg");
        dto.setLatitude(47.123123);
        dto.setLongitude(1231.123123);

        // when
        int success = service.createVolunteer(dto);

        // then
        Assertions.assertTrue(success > 0, "봉사 공고 생성 실패");
    }

    @Test
    @DisplayName("봉사 공고 생성 이미지 업로드 테스트")
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
        long id = 1L;
        AdminVolunteerRequestDTO dto = new AdminVolunteerRequestDTO();
        dto.setVolunteerId(19L); // Assuming this ID exists
        dto.setTitle("서비스 수정 봉사 공고");
        dto.setContent("서비스 수정 봉사 공고 내용");
        dto.setType(1);
        dto.setCategory(2);
        dto.setVolunteerStartDate(Timestamp.valueOf("2024-08-01 09:00:00").toLocalDateTime());
        dto.setVolunteerEndDate(Timestamp.valueOf("2024-08-01 12:00:00").toLocalDateTime());
        dto.setAddress("서비스 수정 주소");
        dto.setTotalCount(30);
        dto.setStatus(1);
        dto.setRecruitStartDate(Timestamp.valueOf("2024-07-01 00:00:00").toLocalDateTime());
        dto.setRecruitEndDate(Timestamp.valueOf("2024-07-31 23:59:59").toLocalDateTime());
        dto.setImageUrl("https://dsdsdcsdcscdm/service_update_image.jpg");
        dto.setLatitude(37.23112);
        dto.setLongitude(123.131232);

        // when
        int success = service.updateVolunteer(id,dto); // 수정이 성공하면 true를 반환한다고 가정

        // then
        Assertions.assertTrue(success == 1, "봉사 공고 수정 실패");
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
        int page = 1;
        int size = 1;
        // when
        List<AdminVolunteerResponseDTO> list = service.getVolunteerListWithPaging(page, size);

        // then
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("봉사활동 신청 대기 리스트 조회 서비스 테스트")
    public void testGetVolunteerRequestPendingList() throws Exception {
        // given
        int page = 1;
        int size = 10;

        // when
        List<VolunteerRequestListResponseDTO> dto
                = service.getVolunteerRequestPendingList(page, size);

        // then
        Assertions.assertNotNull(dto);
    }

    @Test
    @DisplayName("봉사활동 신청 대기 상세 조회 서비스 테스트")
    public void testGetVolunteerRequestPendingDetail() throws Exception  {
        // given
        long volunteerId = 1;
        long userId = 1;

        // when
        VolunteerRequestDetailResponseDTO dto
                = service.getVolunteerRequestPendingDetail(volunteerId, userId);

        // then
        assertThat(dto.getUserId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("봉사활동 인증 대기 목록 조회 서비스 테스트")
    public void testGetVolunteerCertificationPendingList() throws Exception {
        // given
        int offset = 0;
        int size = 10;

        // when
        List<VolunteerRequestListResponseDTO> dto
                = service.getVolunteerCertificationList(offset, size);

        // then
        Assertions.assertNotNull(dto);
    }

    @Test
    @DisplayName("봉사활동 인증 대기 상세 조회 서비스 테스트")
    public void testGetVolunteerCertificationDetail() throws Exception  {
        // given
        int volunteerId = 1;
        int userId = 1;

        // when
        VolunteerCertificationDetailResponseDTO dto
                = service.getVolunteerCertificationDetail(volunteerId, userId);

        // then
        assertThat(dto.getUserId()).isEqualTo(userId);
    }
}