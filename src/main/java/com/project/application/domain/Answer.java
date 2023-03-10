package com.project.application.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="answer")
public class Answer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	@Column(name="id", updatable = false)
	private long id;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@Column(name="content")
	private String content;
	
	@Column(name="is_accepted")
	private boolean isAccepted = false;
	
	@CreationTimestamp
	@Column(name="created_at", updatable = false)
	private Timestamp createdAt; 
	
	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "answer_id")
	private List<Comment> comments;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="author_id")
	private Author author;

	public void addComment(Comment comment){
		if(comments == null){
			comments = new ArrayList<>();
		}
		comments.add(comment);
	}
	
}
