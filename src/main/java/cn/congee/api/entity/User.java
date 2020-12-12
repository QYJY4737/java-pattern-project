package cn.congee.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Author: yang
 * @Date: 2020-12-12 9:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "测试用户信息表")
public class User implements Serializable {

    private final static long serialVersionUID = -1L;

    @ApiModelProperty(value = "测试用户ID")
    private Integer id;

    @ApiModelProperty(value = "测试用户名")
    private String username;

    @ApiModelProperty(value = "测试用户密码")
    private String password;

    @ApiModelProperty(value = "测试用户性别")
    private String sex;

    @ApiModelProperty(value = "测试用户年龄")
    private Integer age;

    @ApiModelProperty(value = "测试用户手机")
    private String phone;

    @ApiModelProperty(value = "测试用户邮箱")
    private String email;

    @ApiModelProperty(value = "测试用户身份证")
    private String idcard;

    @ApiModelProperty(value = "测试用户地址")
    private String address;

    @ApiModelProperty(value = "测试用户创建时间")
    private String createtime;

    @ApiModelProperty(value = "测试用户扩展参数")
    private String extendparams;

    @ApiModelProperty(value = "测试用户生日")
    private String birth;

}
