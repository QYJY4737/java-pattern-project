package cn.congee.api.service;

import cn.congee.api.common.JsonResult;
import cn.congee.api.common.PageResult;
import cn.congee.api.entity.PatInfo;

import java.util.List;

/**
 * @Author: yang
 * @Date: 2020-12-11 3:48
 */
public interface PatInfoService {

    /**
     * 保存用户信息
     *
     * @param patInfo
     * @return
     */
    JsonResult savePatInfo(PatInfo patInfo);

    /**
     * 根据id删除用户信息
     *
     * @param patId
     * @return
     */
    JsonResult deletePatInfoById(Integer patId);

    /**
     * 查询所有
     *
     * @return
     */
    JsonResult<List<PatInfo>> findAll();

    /**
     * 根据patId查询用户信息
     *
     * @param patId
     * @return
     */
    JsonResult<PatInfo> findById(Integer patId);

    /**
     * 更新用户信息
     *
     * @param patInfo
     * @return
     */
    JsonResult updatePatName(PatInfo patInfo);

    /**
     * 根据关键字模糊查询
     *
     * @param keyword
     * @return
     */
    JsonResult<List<PatInfo>> findLikeConcat(String keyword);

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<PatInfo> getPatInfoList(Integer page, Integer size);

}
