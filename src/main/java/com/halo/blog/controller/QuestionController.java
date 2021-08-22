package com.halo.blog.controller;

import com.halo.blog.dto.CommentDTO;
import com.halo.blog.dto.T;
import com.halo.blog.enums.CommentTypesEnum;
import com.halo.blog.service.CommentService;
import com.halo.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by halo on 2019/8/6.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypesEnum.QUESTION);
        // 累加阅读数
        questionService.incView(id);
        T questionDTO = questionService.getById(id);
        List<T> relatedQuestion = questionService.selectRelated(questionDTO);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestion", relatedQuestion);

        return "question";
    }
}
