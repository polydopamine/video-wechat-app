package com.video.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程评论表
 * </p>
 *
 * @author wsh
 * @since 2021-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Comments对象", description="课程评论表")
public class Comments implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private String fatherCommentId;

    private String toUserId;

    @ApiModelProperty(value = "视频id")
    private String videoId;

    @ApiModelProperty(value = "留言者，评论的用户id")
    private String fromUserId;

    @ApiModelProperty(value = "评论内容")
    private String comment;

    private LocalDateTime createTime;


}
