package com.we_are_team.school_background_service_system.mapper;

import com.github.pagehelper.Page;
import com.we_are_team.school_background_service_system.pojo.entity.Feedback;
import com.we_are_team.school_background_service_system.pojo.vo.GetFeedbackListVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface FeedbacksMapper {

    /**
     * 插入反馈
     * @param
     */
@Insert("insert into feedback (user_id, content,is_anonymous,created_time,image_urls) " +
        "values (#{feedback.userId}, #{feedback.content},#{feedback.isAnonymous} ,#{feedback.createdTime},#{feedback.imageUrls})")
    void insert(@Param("feedback") Feedback feedback);

    Page<GetFeedbackListVO> getList(@Param("userId") Integer userId, @Param("orderKey") String orderKey, @Param("beginTime") LocalDate beginTime, @Param("endTime") LocalDate endTime);

    @Select("select f.*,u.student_number from feedback f left join user as u on f.user_id = u.user_id where f.feedback_id = #{feedbackId}")
    Feedback getFeedbackByFeedbackId(@Param("feedbackId") Integer feedbackId);

    @Delete("delete from feedback where feedback_id = #{feedbackId}")
    void deleteFeedback(@Param("feedbackId") Integer feedbackId);
}
