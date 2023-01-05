package com.project.application.service;

import com.project.application.domain.Tag;
import com.project.application.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> saveTag(List<String> tagNames) {
        List<Tag> tags = new ArrayList<Tag>();
        for(String tagName : tagNames) {
            Tag tag = new Tag();
            if(!checkTag(tagName)) {
                tag.setName(tagName);
                tags.add(tag);
            }
            else {
                tag = tagRepository.findByName(tagName);
                tags.add(tag);
            }
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteTag(Tag tag) {

    }

    @Override
    public boolean checkTag(String tagName) {
        List<Tag> listTags = getAllTags();
        boolean check = false;
        for(Tag theTag : listTags) {
            if(theTag.getName().equals(tagName)) {
                check = true;
            }
        }
        System.out.println("check : " + check);
        return check;
    }
}