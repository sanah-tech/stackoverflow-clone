package com.project.application.service;

import com.project.application.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestons();

    void saveQuestion(Question question, String tagName);

    Question getQuestionById(long questionId);

    void updateQuestion(Question question, long questionId);

    void deleteQuestionById(long questionId);
}