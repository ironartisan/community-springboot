package com.halo.blog.service;

import com.halo.blog.dto.NotificationDTO;
import com.halo.blog.dto.PaginationDTO;
import com.halo.blog.enums.NotificationStatusEnum;
import com.halo.blog.enums.NotificationTypesEnum;
import com.halo.blog.exception.CustomizeErrorCode;
import com.halo.blog.exception.CustomizeException;
import com.halo.blog.mapper.NotificationMapper;
import com.halo.blog.model.*;
import com.halo.blog.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by halo on 2019/8/27.
 */

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     * 实现分页功能
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Long userId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;

        // 获取通知总数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        try {
            //实现分页功能
            Integer offset = size * (page - 1);
            NotificationExample example = new NotificationExample();
            example.createCriteria()
                    .andReceiverEqualTo(userId);
            example.setOrderByClause("GMT_CREATE desc");
            List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

            if (notifications.size() == 0) {
                return paginationDTO;
            }

            List<NotificationDTO> notificationDTOS = new ArrayList<>();

            // 将上面获取的值赋予notificationDTO
            for (Notification notification : notifications) {
                NotificationDTO notificationDTO = new NotificationDTO();
                BeanUtils.copyProperties(notification, notificationDTO);
                notificationDTO.setTypeName(NotificationTypesEnum.nameOfType(notification.getType()));
                notificationDTOS.add(notificationDTO);
            }
            paginationDTO.setData(notificationDTOS);
            return paginationDTO;
        } catch (Exception e) {
            log.error("PaginationDTO list handle error", e);
        }
        return null;
    }

    /**
     * 获取未读通知数
     *
     * @param userId: 用户ID
     * @return
     */
    public long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long unreadCount = notificationMapper.countByExample(notificationExample);
        return unreadCount;
    }

    /**
     * 更新为已读状态
     *
     * @param id
     * @param user
     * @return 返回问题id等信息，为跳转页面提供信息
     */
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypesEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}

