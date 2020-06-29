package com.github.yanghr.easyjob.domainmodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @version
 * @author
 */
@Data
@Accessors(chain = true)
public class Job {

    private static final long serialVersionUID = 1L;

    @TableId(value = "job_id", type = IdType.ASSIGN_ID)
    private String jobId;

    /**
     * 具体描述
     */
    @TableField("description")
    private String description;

    /**
     * 作业名称
     */
    @TableField("job_type")
    private String jobType;

    /**
     * 子系统代码
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 关注人
     */
    @TableField("reminders")
    private String reminders;

    /**
     * 发送状态提醒
     */
    @TableField("status_on_reminder")
    private String statusOnReminder;

    @TableField("remind_on_timeout")
    private String remindOnTimeout;

    /**
     * 过期时间
     */
    @TableField("timeout")
    private String timeout;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 是否支持回滚
     */
    @TableField("support_rollback")
    private String supportRollback;

}