package cn.congee.api.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author: yang
 * @Date: 2020-12-11 3:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)         //自动的给model bean实现equals方法和hashcode方法。
public class PatInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 用户ID 绝对唯一
     */
    private Integer patId;

    /**
     * 用户名
     */
    private String patName;

    /**
     * 密码
     */
    private String patPass;

    /**
     * 年龄
     */
    private Integer patAge;

    /**
     * 性别
     */
    private String patGender;

    /**
     * 地址
     */
    private String patAddress;

    /**
     * 手机
     */
    private String patPhone;

    /**
     * 身份证
     */
    private String patIdcard;

    /**
     * 生日
     */
    private String birthday;


}
