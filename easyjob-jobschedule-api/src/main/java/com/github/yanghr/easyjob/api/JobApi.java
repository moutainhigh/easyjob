package com.github.yanghr.easyjob.api;

import com.github.yanghr.easyjob.vo.AdhocJobDTO;
import com.github.yanghr.easyjob.vo.JobInstanceVO;
import com.github.yanghr.easyjob.vo.JobVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface JobApi {
    /**
     * 按id查询.
     *
     * @param id id
     * @return OprJobVO
     */
    @ApiOperation("按id查询")
    @ApiImplicitParam(name = "id", value = "OprJob id", required = true, dataType = "String")
    @GetMapping("/job/id/{id}")
    JobVO findOne(@PathVariable String id);


    /**
     * 修改.
     *
     * @param id id
     * @param vo OprJob
     * @return OprJobVO
     */
    @ApiOperation("修改")
    @ApiImplicitParam(name = "id", value = "OprJob id", required = true, dataType = "String")
    @PutMapping("/jb/id/{id}")
    JobVO put(@PathVariable String id, @RequestBody @Valid JobVO vo);

    /**
     * 添加.
     *
     * @param vo OprJob
     * @return OprJobVO
     */
    @ApiOperation("添加")
    @PostMapping("/job")
    JobVO add(@RequestBody @Valid JobVO vo);

    /**
     * 删除.
     *
     * @param id id
     * @return String
     */
    @ApiOperation("按id删除")
    @ApiImplicitParam(name = "id", value = "OprJob id", required = true, dataType = "String")
    @DeleteMapping("/job/id/{id}")
    boolean delete(@PathVariable String id);

    /**
     * 提交adHoc的作业.
     *
     * @param jobVO AdhocJobVO
     * @return ResponseDetail
     */
    @PostMapping("/job/adhoc")
    @ApiOperation("提交adHoc的作业.")
    JobInstanceVO runAdhoc(@RequestBody AdhocJobDTO jobVO);



    /**
     * 创建jobInstance
     *
     * @param dto dto
     * @return OprJobInstanceVO
     */
    @ApiOperation("创建jobInstance")
    @PostMapping("/job/createAdhoc")
    JobInstanceVO createAdhocJobInstance(@RequestBody AdhocJobDTO dto);

    /**
     * 启动作业
     *
     * @param jobInstanceVO jobInstanceVO
     * @return OprJobInstanceVO
     */
    @ApiOperation("启动作业")
    @PostMapping("/job/runInstance")
    JobInstanceVO updateAndRunJobInstance(@RequestBody JobInstanceVO jobInstanceVO);
}
