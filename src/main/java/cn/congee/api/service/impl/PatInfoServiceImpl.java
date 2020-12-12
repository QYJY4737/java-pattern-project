package cn.congee.api.service.impl;

import cn.congee.api.common.JsonResult;
import cn.congee.api.common.PageResult;
import cn.congee.api.constants.GlobalConstants;
import cn.congee.api.entity.PatInfo;
import cn.congee.api.exception.BaseExceptionMsg;
import cn.congee.api.mapper.PatInfoMapper;
import cn.congee.api.service.PatInfoService;
import cn.congee.api.util.IdcardUtils;
import cn.congee.api.util.Validates;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: yang
 * @Date: 2020-12-11 3:53
 */
@Slf4j
@Service
public class PatInfoServiceImpl implements PatInfoService {

    @Autowired
    private PatInfoMapper patInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存用户信息
     *
     * @param patInfo
     * @return
     */
    @Override
    public JsonResult savePatInfo(PatInfo patInfo) {
        boolean flag = patInfoMapper.savePatInfo(patInfo);
        JsonResult result = null;
        result.setData(flag ? 1 : 0);
        return result;
    }

    /**
     * 删除用户策略：删除数据表中数据，然后删除缓存
     * @param patId
     * @return
     */
    @Override
    public JsonResult deletePatInfoById(Integer patId) {
        boolean b = patInfoMapper.deletePatInfoById(patId);
        String key = getCacheKey(patId);
        if(b){
            Boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey){
                redisTemplate.delete(key);
                log.info("删除了缓存中的key: " + key);
            }
        }
        JsonResult result = new JsonResult();
        result.setData(b);
        return result;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public JsonResult<List<PatInfo>> findAll() {
        List<PatInfo> patInfoList = patInfoMapper.findAll();
        //升序
        //List<PatInfo> newList = patInfoList.stream().sorted(Comparator.comparing(PatInfo::getPatAge)).collect(Collectors.toList());
        //降序
        //List<PatInfo> newList2 = patInfoList.stream().sorted(Comparator.comparing(PatInfo::getPatAge).reversed()).collect(Collectors.toList());
        return new JsonResult(patInfoList);
    }

    /**
     * 获取用户策略：先从缓存中获取用户，没有则取数据表中数据，再将数据写入缓存
     *
     * @param patId
     * @return
     */
    @Override
    public JsonResult<PatInfo> findById(Integer patId) {
        Validates.mustNotNull(patId, BaseExceptionMsg.GIRLSID_IS_NULL,"请输入用户id");
        String key = getCacheKey(patId);
        ValueOperations<String,PatInfo> operations = redisTemplate.opsForValue();

        //判断redis中是否有键为key的缓存
        Boolean hasKey = redisTemplate.hasKey(key);

        PatInfo patInfo = null;
        if(hasKey){
            patInfo = operations.get(key);
            patInfo.setBirthday(IdcardUtils.getBirthDay(patInfo.getPatIdcard()));
            log.info("从缓存中获得数据: " + patInfo.getPatName());
            log.info("=========================================");
        }else {
            patInfo = patInfoMapper.findById(patId);
            patInfo.setBirthday(StringUtils.isNotBlank(patInfo.getPatIdcard()) ? IdcardUtils.getBirthDay(patInfo.getPatIdcard()) : " ");
            log.info("查询数据库获得数据: " + patInfo.getPatName());
            log.info("===========================================");
            //写入缓存
            operations.set(key,patInfo,5, TimeUnit.MINUTES);
        }
        JsonResult result = new JsonResult();
        result.setData(patInfo);
        result.setSuccess(true);
        return result;
    }

    /**
     * 更新用户策略：先更新数据表，成功之后，删除原来的缓存，再更新缓存
     * @param patInfo
     * @return
     */
    @Override
    public JsonResult updatePatName(PatInfo patInfo) {
        ValueOperations<String,PatInfo> operations = redisTemplate.opsForValue();
        PatInfo info = patInfoMapper.findById(patInfo.getPatId());
        JsonResult result = new JsonResult();
        if(info != null){
            PatInfo build = PatInfo.builder()
                    .patId(patInfo.getPatId() == null ? info.getPatId() : patInfo.getPatId())
                    .patName(patInfo.getPatName() == null ? info.getPatName() : patInfo.getPatName())
                    .patPass(patInfo.getPatPass() == null ? info.getPatPass() : patInfo.getPatPass())
                    .patAge(patInfo.getPatAge() == null ? info.getPatAge() : patInfo.getPatAge())
                    .patGender(patInfo.getPatGender() == null ? info.getPatGender() : patInfo.getPatGender())
                    .patAddress(patInfo.getPatAddress() == null ? info.getPatAddress() : patInfo.getPatAddress())
                    .patPhone(patInfo.getPatPhone() == null ? info.getPatPhone() : patInfo.getPatPhone())
                    .patIdcard(patInfo.getPatIdcard() == null ? info.getPatIdcard() : patInfo.getPatIdcard())
                    .build();
            boolean flag = patInfoMapper.updatePatName(build);
            if(flag){
                String key = getCacheKey(patInfo.getPatId());
                Boolean hasKey = redisTemplate.hasKey(key);
                if(hasKey){
                    redisTemplate.delete(key);
                    log.info("删除缓存中的key--------> " + key);
                }
                //再将更新后的数据加入缓存
                PatInfo fo = patInfoMapper.findById(patInfo.getPatId());
                if(fo != null){
                    operations.set(key, fo, 3, TimeUnit.MINUTES);
                }
                result.setData(build);
                result.setSuccess(true);
            }
        }
        return result;
    }

    /**
     * #会将传入的内容当做字符串，而有什么区别？
     * 而会直接将传入值拼接在sql语句中，所以#可以在一定程度上预防sql注入攻击。
     *
     * @param keyword
     * @return
     */
    @Override
    public JsonResult<List<PatInfo>> findLikeConcat(String keyword) {
        List<PatInfo> patInfoList = patInfoMapper.findLikeConcat(keyword);
        List<PatInfo> list = patInfoList.stream().sorted(Comparator.comparing(PatInfo::getPatAge)).collect(Collectors.toList());
        String patNames = patInfoList.stream().map(PatInfo::getPatName).collect(Collectors.joining(","));
        log.info("patNames=[{}] " + patNames);
        return new JsonResult(list);
    }

    /**
     * 分页查询
     * @param page 当前页数
     * @param size 每页行数
     * @return
     */
    @Override
    public PageResult<PatInfo> getPatInfoList(Integer page, Integer size) {
        Validates.mustNotNull(page, BaseExceptionMsg.PARAMS_MUST_NOT_NULL);
        Validates.mustNotNull(size, BaseExceptionMsg.PARAMS_MUST_NOT_NULL);
        List<PatInfo> patInfoList = patInfoMapper.findAll();
        List<PatInfo> list = new ArrayList<>();
        patInfoList.stream().forEach(item -> {
            if(StringUtils.isNotBlank(item.getPatIdcard())){
                item.setBirthday(IdcardUtils.getBirthDay(item.getPatIdcard()));
            }
            list.add(item);
        });
        List<PatInfo> lastResult = list.subList(Math.min(size * page, list.size()), Math.min(size * (page + 1), list.size()));
        //first 为false表示还有下一页
        return new PageResult<>(page, size, list.size(), lastResult);
    }

    private String getCacheKey(Integer patId) {
        return GlobalConstants.APPLICATION_YML_SERVER_PORT + "::" + patId;
    }
}
