package com.sbsj.dreamwing.mission.domain;

import lombok.Data;

/**
 * 퀴즈 VO
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	정은지        최초 생성
 * </pre>
 */
@Data
public class QuizVO {

    private long quizId;
    private String question;
    private int answer;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

}
