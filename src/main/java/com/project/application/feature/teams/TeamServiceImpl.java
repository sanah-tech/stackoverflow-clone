package com.project.application.feature.teams;

import com.project.application.domain.Answer;
import com.project.application.domain.Author;
import com.project.application.domain.Tag;
import com.project.application.domain.TeamQuestion;
import com.project.application.feature.teams.repository.TeamQuestionRepository;
import com.project.application.feature.teams.repository.TeamRepository;
import com.project.application.service.AuthorService;
import com.project.application.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    AuthorService authorService;

    @Autowired
    TagService tagService;

    @Autowired
    TeamQuestionRepository teamQuestionRepository;


    @Override
    public Team saveTeam(String teamName,String email) {
        Author author=authorService.findByEmail(email);
        Team team=new Team();
        team.setName(teamName);
        team.setAdmin(author);
        team.addMember(author);
//        author.addTeam(team);
        teamRepository.save(team);
        return team;
    }

    @Override
    public void deleteTeam(long teamId) {
        teamRepository.deleteById(teamId);
    }

    @Override
    public Team addMember(long teamId,String email) {
        Team team=teamRepository.findById(teamId).get();
        Author author=authorService.findByEmail(email);
        if(author==null){
            return team;
        }
        if(team.getMembers().contains(author)){
            return team;
        }
        team.addMember(author);
//        author.addTeam(team);
        teamRepository.save(team);
        return team;
    }

    @Override
    public Team removeMember(long teamId,String email) {
        Team team=teamRepository.findById(teamId).get();
        Author author= authorService.findByEmail(email);
        if(author==null){
            return team;
        }
        if(!team.getMembers().contains(author)){
            return team;
        }
        team.removeMember(author);
        author.removeTeam(team);
        teamRepository.save(team);
//        authorService.saveAuthor(author);
        return team;
    }

    @Override
    public Team makeAdmin(long teamId,String email) {
        Team team=teamRepository.findById(teamId).get();
        Author author= authorService.findByEmail(email);
        team.setAdmin(author);
        return team;
    }

    @Override
    public Team getTeamById(long teamId) {
        return teamRepository.findById(teamId).get();
    }

    // ----- Team Question service methods -----

    @Override
    public void saveTeamQuestion(long teamId, TeamQuestion teamQuestion, String tagNames) {
        List<Tag> tags = tagService.saveTag(Arrays.asList(tagNames.split(",")));
        teamQuestion.setTags(tags);
        getTeamById(teamId).addQuestion(teamQuestion);
        teamQuestionRepository.save(teamQuestion);
    }

    @Override
    public void saveTeamAnswer(long questionId, Answer answer) {
        TeamQuestion question = teamQuestionRepository.findById(questionId).get();
        question.addAnswer(answer);
        teamQuestionRepository.save(question);
    }

    @Override
    public List<TeamQuestion> getAllTeamQuestions(long teamId) {
        return teamRepository.findById(teamId).get().getTeamQuestions();
    }

    @Override
    public TeamQuestion getTeamQuestionById(long teamQuestionId) {
        return teamQuestionRepository.findById(teamQuestionId).get();
    }
}
