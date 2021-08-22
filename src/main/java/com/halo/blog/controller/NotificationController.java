package com.halo.blog.controller;

import com.halo.blog.dto.NotificationDTO;
import com.halo.blog.dto.PaginationDTO;
import com.halo.blog.enums.NotificationTypesEnum;
import com.halo.blog.model.User;
import com.halo.blog.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by halo on 2019/8/28.
 */

@Controller
@Slf4j
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * 当用户点击未读通知
     *
     * @param id
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request,
                          Model model
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "redirect:/";
        }
        // user用来验证是否是本人，并根据id更改状态
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypesEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypesEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            log.error("reply type is wrong");
            return "redirect:/";
        }
    }


}

