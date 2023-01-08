package com.project.application.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", insertable=false, updatable = false)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String authorRole = "ROLE_USER";

    @ManyToMany(targetEntity=Tag.class, fetch=FetchType.EAGER, cascade =
            {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
            @JoinTable(
            name="author_tag",
            joinColumns=@JoinColumn(name="author_id"),
            inverseJoinColumns=@JoinColumn(name="tag_id"))
    private List<Tag> tagsWatched;

    @OneToMany( mappedBy = "author", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany( mappedBy = "author", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany( mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public void addTagWatched(Tag tag) {
        if(tagsWatched == null) {
            tagsWatched = new ArrayList<>();
        }
        tagsWatched.add(tag);
    }

    public void deleteTagWatched(Tag tag) {
        tagsWatched.remove(tag);
    }

    public void addQuestion (Question question){
        if(questions == null){
            questions = new ArrayList<>();
        }
        questions.add(question);
        question.setAuthor(this);
    }

    public void addAnswer (Answer answer){
        if(answers == null){
            answers = new ArrayList<>();
        }
        answers.add(answer);
        answer.setAuthor(this);
    }

    public void addComment (Comment comment){
        if(comments == null){
            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setAuthor(this);
    }
}