package cn.congee.api.controller;

import cn.congee.api.annotation.RequestLimit;
import cn.congee.api.common.JsonResult;
import cn.congee.api.common.PageResult;
import cn.congee.api.entity.PatInfo;
import cn.congee.api.service.PatInfoService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    //{
    //    "code": 9999,
    //    "success": true,
    //    "message": "操作过于频繁"
    //}
    @RequestLimit        //被该注解注释默认：一分钟内请求超过15次会被提示上述情况
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

    @GetMapping("/searchByPageAndSize/{page}/{size}")
    @ApiOperation(value = "[7]-分页查询用户信息")
    public JsonResult<PageResult<PatInfo>> searchByPageAndSize(@PathVariable(value = "page") Integer page,
                                                               @PathVariable(value = "size") Integer size){
        return new JsonResult(patInfoService.getPatInfoList(page, size));
    }

    /**
     * Cron表达式参数分别表示：
     *
     * 秒（0~59） 例如0/5表示每5秒
     * 分（0~59）
     * 时（0~23）
     * 日（0~31）的某天，需计算
     * 月（0~11）
     * 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
     * @Scheduled：除了支持灵活的参数表达式cron之外，还支持简单的延时操作，例如 fixedDelay ，fixedRate 填写相应的毫秒数即可。
     * @return
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    @PostMapping("/addPatInfo")
    @ApiOperation(value = "[8]-定时添加随机用户信息")
    public JsonResult addPatInfo(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.error("执行静态定时任务时间: " + format.format(new Date()));

        return patInfoService.addPatInfo();
    }

}
