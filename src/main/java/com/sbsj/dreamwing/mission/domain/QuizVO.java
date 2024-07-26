package com.sbsj.dreamwing.mission.domain;

import lombok.Data;

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
