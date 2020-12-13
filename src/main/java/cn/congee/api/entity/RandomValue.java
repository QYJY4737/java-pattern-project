package cn.congee.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: yang
 * @Date: 2020-12-14 1:59
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "TABLE")
public class RandomValue {

    private String chineseName;

    private String sex;

    private String road;

    private String tel;

    private String email;

    @XmlAttribute(name = "CHINESENAME")
    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @XmlAttribute(name = "SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @XmlAttribute(name = "ROAD")
    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    @XmlAttribute(name = "TEL")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @XmlAttribute(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
