package com.halo.blog.mapper;

import com.halo.blog.dto.QuestionQueryDTO;
import com.halo.blog.model.Question;

import java.util.List;

public interface QuestionExMapper {
    int incView(Question record);

    int incComment(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}