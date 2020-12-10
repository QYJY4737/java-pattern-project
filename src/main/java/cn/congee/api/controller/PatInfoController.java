package cn.congee.api.controller;

import cn.congee.api.common.JsonResult;
import cn.congee.api.entity.PatInfo;
import cn.congee.api.service.PatInfoService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yang
 * @Date: 2020-12-11 4:17
 */
@Slf4j
@RestController
@RequestMapping(value = "/patInfo")
@Api(value = "PatInfoController", description = "用户信息相关接口")
public class PatInfoController {

    @Autowired
    private PatInfoService patInfoService;

    @PostMapping(value = "/savePatInfo")
    @ApiOperation(value = "[1]-新增用户信息")
    public JsonResult savePatInfo(@RequestBody PatInfo patInfo){
        return patInfoService.savePatInfo(patInfo);
    }

    @DeleteMapping(value = "/deletePatInfoById/{patId}")
    @ApiOperation(value = "[2]-根据id删除用户信息")
    public JsonResult deletePatInfoById(@PathVariable(value = "patId") Integer patId){
        return patInfoService.deletePatInfoById(patId);
    }

    @GetMapping(value = "/findAll")
    @ApiOperation(value = "[3]-查询所有用户信息")
    public JsonResult<List<PatInfo>> findAll(){
        return patInfoService.findAll();
    }

    @GetMapping(value = "/findById/{patId}")
    @ApiOperation(value = "[4]-根据patId查询用户")
    public JsonResult<PatInfo> findById(@PathVariable(value = "patId") Integer patId){
        JsonResult result = patInfoService.findById(patId);
        log.info("根据patId查询用户: " + JSON.toJSONString(result.getData()));
        return result;
    }

    @PutMapping(value = "/updatePatName")
    @ApiOperation(value = "[5]-修改用户信息")
    public JsonResult updatePatName(@RequestBody PatInfo patInfo){
        return patInfoService.updatePatName(patInfo);
    }

    @GetMapping(value = "/findLikeConcat")
    @ApiOperation(value = "[6]-模糊查询")
    public JsonResult<List<PatInfo>> findLikeConcat(@RequestParam(value = "keyword") String keyword){
        return patInfoService.findLikeConcat(keyword);
    }

}
