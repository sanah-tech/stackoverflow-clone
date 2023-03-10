package com.project.application.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tag")
public class Tag {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@CreationTimestamp
	@Column(name="created_at", updatable = false)
	private Timestamp createdAt; 
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;

	@ManyToMany(cascade = {
			CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name = "team_question_tag",
			joinColumns = @JoinColumn(name = "tag_id"),
			inverseJoinColumns = @JoinColumn(name = "team_question_id"))
	private Set<TeamQuestion> teamQuestions;

	@ManyToMany(targetEntity=Author.class, fetch=FetchType.LAZY, cascade =
			{CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH})
	@JoinTable(
			name="author_tag",
			joinColumns=@JoinColumn(name="tag_id"),
			inverseJoinColumns=@JoinColumn(name="author_id"))
	List<Author> authorsWatching;
	
	public void addQuestion(Question question) {
        if (questions == null) {
            questions = new HashSet<>();
        }
        questions.add(question);
    }
	
	public void removeQuestion(Question question) {
		if(questions.contains(question)) {
			questions.remove(question);
		}
	}
}
