package com.video.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 视频信息表
 * </p>
 *
 * @author wsh
 * @since 2021-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Videos对象", description="视频信息表")
public class Videos implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "发布者id")
    private String userId;

    @ApiModelProperty(value = "用户使用音频的信息")
    private String audioId;

    @ApiModelProperty(value = "视频描述")
    private String videoDesc;

    @ApiModelProperty(value = "视频存放的路径")
    private String videoPath;

    @ApiModelProperty(value = "视频秒数")
    private Float videoSeconds;

    @ApiModelProperty(value = "视频宽度")
    private Integer videoWidth;

    @ApiModelProperty(value = "视频高度")
    private Integer videoHeight;

    @ApiModelProperty(value = "视频封面图")
    private String coverPath;

    @ApiModelProperty(value = "喜欢/赞美的数量")
    private Long likeCounts;

    @ApiModelProperty(value = "视频状态： 1、发布成功 2、禁止播放，管理员操作")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
