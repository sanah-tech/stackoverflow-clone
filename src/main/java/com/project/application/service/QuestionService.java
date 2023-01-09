package com.project.application.service;

import com.project.application.domain.Question;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    List<Question> getAllQuestons();

    void saveQuestion(Question question, String tagName, String email);

    Question getQuestionById(long questionId);

    void updateQuestion(Question question, long questionId);

    void deleteQuestionById(long questionId);

    Set<Question> getSearchedOrFilteredQuestions(String searchKey, String filterByTags, boolean filterByNoAnswer, boolean noAcceptedAnswer);

    Page<Question> findPaginatedQuestions(int pageNo, int pageSize, String sortField);

    List<Question> getSearchRelatedQuestions(String searchKey);

}
