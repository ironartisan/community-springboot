package com.halo.blog.service;

import com.halo.blog.dto.CommentDTO;
import com.halo.blog.enums.CommentTypesEnum;
import com.halo.blog.enums.NotificationStatusEnum;
import com.halo.blog.enums.NotificationTypesEnum;
import com.halo.blog.exception.CustomizeErrorCode;
import com.halo.blog.exception.CustomizeException;
import com.halo.blog.mapper.*;
import com.halo.blog.model.*;
import com.halo.blog.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by halo on 2019/8/9.
 */

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExMapper commentExMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExMapper questionExMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     * @Transactional 此处应使用Transactional方法保证事务一致性.
     */
    public void insert(Comment comment, User commentator) throws CustomizeException {
        // 判断评论的对象是否存在，若不存在，抛出异常
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        // 判断评论的类型是否在自定义的类型(问题或评论)
        if (comment.getType() == null || !CommentTypesEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_EXIST);
        }
        if (comment.getType().equals(CommentTypesEnum.COMMENT.getType())) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_EXIST);
            }
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            // 增加评论数
            if (comment.getCommentCount() == null) {
                comment.setCommentCount(0);
            }
            commentMapper.insert(comment);
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExMapper.incComment(parentComment);

            // 增加通知
            createNotification(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypesEnum.REPLY_COMMENT, question.getId());


        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            // 增加通知
            createNotification(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypesEnum.REPLY_QUESTION, question.getId());

            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExMapper.incComment(question);
        }
    }

    /**
     * 设置通知，
     *
     * @param comment      评论
     * @param receiver     被通知人
     * @param notifierName 通知内容
     * @param outerTitle   问题或评论标题
     * @param replyComment 回复内容
     * @param outId        评论或问题ID
     */
    private void createNotification(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypesEnum replyComment, Long outId) {
        if (receiver.equals(comment.getCommentator())) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(replyComment.getType());
        notification.setOuterid(outId);
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setOuterTitle(outerTitle);
        notification.setNotifierName(notifierName);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypesEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 根据commentator字段去重
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        // 转换成list
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // 根据id获取user列表
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        // 获取一个map<id, user>
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 将user和comment使用map进行关联
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            MyUtils.myCopyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
