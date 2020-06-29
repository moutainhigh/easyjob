package com.github.yanghr.easyjob.vo;

import java.io.Serializable;
import java.util.Date;
import com.github.yanghr.easyjob.enums.DependType;
import com.github.yanghr.easyjob.enums.YNOption;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * OprJobInstanceDependVO.
 * </p>
 *
 * @version
 * @author
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "JobInstanceDepend对象", description = "JobInstanceDependVO")
public class JobInstanceDependVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dependId;

    private String createdBy;

    private Date createdDate;

    private String lastModifiedBy;

    private Date lastModifiedDate;

    private String jobInstanceDependon;

    private String jobInstanceId;

    private DependType dependType;

    private YNOption strongDepend;

    private JobInstanceVO dependOn;

    private JobInstanceVO dependBy;

}