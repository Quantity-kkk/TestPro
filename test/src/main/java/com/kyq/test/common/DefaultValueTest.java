package com.kyq.test.common;

/**
 * Description： com.kyq.test.common
 * CopyRight:  © 2015 CSTC. All rights reserved.
 * Company: cstc
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2023-06-05 17:05
 */
public class DefaultValueTest {
    public static void main(String args[]){
        InterchargeDto interchargeDto = new InterchargeDto();
        System.out.println(interchargeDto.getCredible());
        System.out.println(interchargeDto.isIntegration());

    }

    static class InterchargeDto{
        //基本数据类型，默认值为false
        private boolean isIntegration;
        //包装类型，默认值为null
        private Boolean isCredible;

        public Boolean getCredible() {
            return isCredible;
        }

        public void setCredible(Boolean credible) {
            isCredible = credible;
        }

        public boolean isIntegration() {
            return this.isIntegration;
        }

        public void setIntegration(final boolean isIntegration) {
            this.isIntegration = isIntegration;
        }
    }
}
