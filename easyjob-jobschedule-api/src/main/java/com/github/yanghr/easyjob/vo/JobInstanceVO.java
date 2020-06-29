package com.github.yanghr.easyjob.vo;

import com.github.yanghr.easyjob.jobexecution.domainmodel.JobOperation;
import com.github.yanghr.easyjob.jobexecution.domainmodel.JobStatus;
import com.github.yanghr.easyjob.utils.EasyStringUtils;
import com.github.yanghr.easyjob.utils.json.JacksonHelper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@ApiModel(value = "JobInstance对象", description = "JobInstanceVO")
public class JobInstanceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String jobInstanceId;

    private String createdBy;

    private Date createdDate;

    private String lastModifiedBy;

    private Date lastModifiedDate;

    private Date actualEndtime;

    private Date actualStarttime;

    private String appCode;

    private String comments;

    private String description;

    private String exeServer;

    private Date expectedStarttime;

    private JobOperation jobOperation;

    private String jobScheduleId;

    private JobStatus jobStatus;

    private String name;

    private String jobId;

    private String submitter;

    private Integer percentage;

    private String jobType;

    private String parameters;

    private List<JobInstanceDependVO> dependOns;

    private List<JobInstanceDependVO> dependBys;

    /**
     * job参数.
     *
     * @return Map<String, String>
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getJobParameters() {
        if (!EasyStringUtils.isEmpty(this.parameters)) {
            return JacksonHelper.jsonToEntity(Map.class, this.parameters);
        }
        return Collections.emptyMap();
    }
}
