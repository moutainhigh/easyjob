package com.github.yanghr.easyjob.domainmodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.yanghr.easyjob.enums.DependType;
import com.github.yanghr.easyjob.enums.YNOption;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class JobInstanceDepend{

    private static final long serialVersionUID = 1L;

    @TableId(value = "depend_id", type = IdType.ASSIGN_ID)
    private String dependId;

    /**
     * 本作业依赖其他作业的id
     */
    @TableField("job_instance_dependon")
    private String jobInstanceDependon;

    /**
     * 当前作业id
     */
    @TableField("job_instance_id")
    private String jobInstanceId;

    /**
     * 依赖类型，时间，事件
     */
    @TableField("depend_type")
    private DependType dependType;

    /**
     * 依赖，强弱
     */
    @TableField("strong_depend")
    private YNOption strongDepend;

    @TableField(exist = false)
    private JobInstance dependOn;

    @TableField(exist = false)
    private JobInstance dependBy;

}