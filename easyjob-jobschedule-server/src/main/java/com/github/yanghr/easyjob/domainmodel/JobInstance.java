package com.github.yanghr.easyjob.domainmodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.yanghr.easyjob.jobexecution.domainmodel.JobOperation;
import com.github.yanghr.easyjob.jobexecution.domainmodel.JobStatus;
import com.github.yanghr.easyjob.utils.EasyStringUtils;
import com.github.yanghr.easyjob.utils.json.JacksonHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class JobInstance {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "job_instance_id", type = IdType.ASSIGN_ID)
    private String jobInstanceId;

    /**
     * 实际结束时间
     */
    @TableField("actual_endtime")
    private Date actualEndtime;

    /**
     * 实际开始时间
     */
    @TableField("actual_starttime")
    private Date actualStarttime;

    /**
     * 子系统名
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 日志
     */
    @TableField("comments")
    private String comments = "";

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 执行节点名称
     */
    @TableField("exe_server")
    private String exeServer;

    /**
     * 预计开始时间
     */
    @TableField("expected_starttime")
    private Date expectedStarttime;

    /**
     * 操作
     */
    @TableField("job_operation")
    private JobOperation jobOperation;

    /**
     * 作业计划id
     */
    @TableField("job_schedule_id")
    private String jobScheduleId;

    /**
     * 作业状态
     */
    @TableField("job_status")
    private JobStatus jobStatus;

    /**
     * 作业名称
     */
    @TableField("name")
    private String name;

    /**
     * 作业id
     */
    @TableField("job_id")
    private String jobId;

    /**
     * 操作人
     */
    @TableField("submitter")
    private String submitter;

    /**
     * 百分比
     */
    @TableField("percentage")
    private Integer percentage;

    /**
     * 作业类型
     */
    @TableField("job_type")
    private String jobType;

    /**
     * 作业参数
     */
    @TableField("parameters")
    private String parameters;

    /**
     * 作业输出参数
     */
    @TableField("output_params")
    private String outputParams;

    @TableField(exist = false)
    private List<JobInstanceDepend> dependOns;

    @TableField(exist = false)
    private List<JobInstanceDepend> dependBys;

    /**
     * 原来自带方法.
     *
     * @param comment String
     * @param date    boolean
     */
    public void appendComments(String comment, boolean date) {

        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        comments = comments + dateStr + " : " + (comment == null ? "" : comment) + "\n\r";
    }

    /**
     * appendComments.
     *
     * @param comment String
     */
    public void appendComments(String comment) {
        this.appendComments(comment, true);
    }

    /**
     * 记录作业操作
     *
     * @param jobOperation JobOperation
     */
    public void recordJobOperation(JobOperation jobOperation) {
        this.jobOperation = jobOperation;

        String formatStr = "Operation on Job is %s %s";
//        String userId = RequestHeaderUtils.getUserId();
        String userId = null;
        userId = EasyStringUtils.isEmpty(userId) ? "" : "by " + userId;
        this.appendComments(String.format(formatStr, jobOperation, userId));
    }

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

    /**
     * job参数.
     *
     * @return Map<String, String>
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getJobOutputParameters() {
        if (!EasyStringUtils.isEmpty(this.outputParams)) {
            return JacksonHelper.jsonToEntity(Map.class, this.outputParams);
        }
        return Collections.emptyMap();
    }
}