package cn.congee.api.constants;

/**
 * 字典定义统一规则：
 * 1）字典名，按照类命名规则
 * 2）字典值，统一按照大写，多个单词用下划线隔开
 *
 * @Author: yang
 * @Date: 2020-12-14 2:12
 */
public final class CommonDic {

    public enum SexDic implements IBaseDic {
        /**
         *
         */
        UNKNOWN("0", "保密"),
        MALE("1", "男性"),
        FEMALE("2", "女性");

        private String code;
        private String name;

        SexDic(String code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }

        static public String getDicCode() {
            return "stan_gender";
        }

        static public String getDicName() {
            return "性别";
        }


        public static String getNameByCode(String code) {
            for (SexDic sex : values()) {
                if (sex.getCode().equals(code)) {
                    return sex.getName();
                }
            }
            return null;
        }
    }

}
