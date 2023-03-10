package com.project.application.service;

import com.project.application.domain.Answer;
import com.project.application.domain.Author;
import com.project.application.domain.Comment;
import com.project.application.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
    @Autowired
    private AnswerService answerService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthorService authorService;

    @Override
    public void saveComment(Comment comment, long answerId, String email) {
        Answer answer=answerService.getAnswerById(answerId);
        answer.addComment(comment);

        Author author = authorService.findByEmail(email);
        author.addComment(comment);

        commentRepository.save(comment);

    }

    @Override
    public void updateComment(Comment comment, long commentId) {
        comment.setId(commentId);
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment getCommentById(long commentId) {
        Comment comment=commentRepository.findById(commentId).get();
        return comment;
    }
}
