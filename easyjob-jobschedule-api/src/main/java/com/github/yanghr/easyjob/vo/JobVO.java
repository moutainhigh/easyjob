package com.github.yanghr.easyjob.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "Job对象", description = "JobVO")
public class JobVO implements Serializable {private static final long serialVersionUID = 1L;

    private String jobId;

    private String createdBy;

    private Date createdDate;

    private String lastModifiedBy;

    private Date lastModifiedDate;

    private String description;

    private String jobType;

    private String appCode;

    private String reminders;

    private String statusOnReminder;

    private String remindOnTimeout;

    private String timeout;

    private String fileType;

    private String supportRollback;

}
