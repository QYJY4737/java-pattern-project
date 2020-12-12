package cn.congee.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "用户信息表")
public class PatInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "用户ID")
    private Integer patId;

    @ApiModelProperty(value = "用户名")
    private String patName;

    @ApiModelProperty(value = "密码")
    private String patPass;

    @ApiModelProperty(value = "年龄")
    private Integer patAge;

    @ApiModelProperty(value = "性别")
    private String patGender;

    @ApiModelProperty(value = "地址")
    private String patAddress;

    @ApiModelProperty(value = "手机")
    private String patPhone;

    @ApiModelProperty(value = "身份证")
    private String patIdcard;

    @ApiModelProperty(value = "生日")
    private String birthday;

}
