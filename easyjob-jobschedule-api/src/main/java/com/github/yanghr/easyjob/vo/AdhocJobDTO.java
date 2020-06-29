package com.github.yanghr.easyjob.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * AdhocJobVO. 点对点(ad hoc)的作业提交，使用的实体
 *
 * @version Araf v2.0
 * @author Wang Yu, 2018年6月29日
 */

@Data
@ApiModel(value="AdhocJobDTO对象", description="AdhocJobDTO")
public class AdhocJobDTO {

    @ApiModelProperty(value = "作业名称")
    private String jobType;

    @ApiModelProperty(value = "定时作业的名称")
    private String scheduleName;

    @ApiModelProperty(value = "模块，子系统的名称")
    private String applicationCode;

    @ApiModelProperty(value = "参数列表")
    private Map<String, String> parameters = new HashMap<>();

    @ApiModelProperty(value = "提交人")
    private String submitter;

    @ApiModelProperty(value = "作业配置表中维护的id")
    private String jobId;

}