package cn.congee.api.mapper;

import cn.congee.api.entity.PatInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author: yang
 * @Date: 2020-12-11 3:33
 */
@Mapper
@Repository
public interface PatInfoMapper extends BaseMapper<PatInfo> {

    /**
     * 添加用户
     * @param patInfo
     * @return
     */
    boolean savePatInfo(PatInfo patInfo);

    /**
     * 删除用户
     * @param patId
     * @return
     */
    boolean deletePatInfoById(@Param(value = "patId") Integer patId);

    /**
     * 查询所有
     * @return
     */
    List<PatInfo> findAll();

    /**
     * 根据ID查询
     * @param patId
     * @return
     */
    PatInfo findById(@Param(value = "patId") Integer patId);

    /**
     * 修改用户
     * @param patInfo
     * @return
     */
    boolean updatePatName(PatInfo patInfo);

    /**
     * 批量修改
     * @param patIdList
     * @return
     */
    boolean updateBatch(List<Integer> patIdList);

    List<PatInfo> findLikeConcat(@Param(value = "keyword") String keyword);

    List<PatInfo> queryTheSameAge(@Param("ages") Set<Integer> ages);

    List<PatInfo> searchByPatGenderAsFOrM();

    PatInfo findPatInfoByPatNameAndPatGender(@Param(value = "patName") String patName,@Param(value = "patIdCard") String patIdCard);

    PatInfo findByName(String patName);//姓名

    PatInfo findByPhone(String patPhone);//手机号

}
