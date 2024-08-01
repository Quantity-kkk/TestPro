package com.kyq.test.common;


/**
 * Description:  CiimpDAOKeyAttribute
 *
 * @author kuangyongqiang
 * @version 1.0
 */
public class HttpConnTest {
    public static void main(String args[]){
//        String requestUrl = "http://101.204.240.105:50040/scdp/controller/json.action";
        String requestUrl = "http://localhost:8080/controller/json.action";
        String OTP = HttpConnectionHelper.getTOTP(requestUrl);

        System.out.print(OTP);
        //==service请求参数======
        //5110003
        /*
        String json="{\"companyCode\":\"5110002\",\"departmentCode\":\"5110002\",\"userId\":\"5110002160001\",\"key\":\"JD0217091\",\"specialProjBidDto\":{\"uuid\":\"zb20171122153313\",\"bidType\":\"OPEN\",\"bidDate\":\"2017-11-22 15:33:00\",\"code\":\"JD0217091\",\"bidJudgeType\":\"LOWEST\",\"bidPerson\":null,\"bidPhone\":null,\"bidDisclosedate\":\"2017-11-23 15:55:00\",\"bidDesc\":null,\"winOrgName\":\"单位名称\",\"winOrgDesc\":null,\"winOrgManager\":\"联系人\",\"winOrgPhone\":null,\"winAmount\":\"1.0000\",\"winDate\":\"2017-11-21 15:35:00\",\"winDesc\":\"中标内容\",\"bidStage\":\"3\",\"fileListDto\":[{\"uuid\":\"zbr20171122153313\",\"fileName\":\"文件名\",\"fileNo\":\"文号\",\"editflag\":\"+\",\"auditUser\":\"报送人姓名\",\"auditContact\":\"155341534532\",\"description\":\"描述\",\"auditTime\":\"2017-11-14 02:46:00\"}],\"specialProjFilemappingDto\":[{\"uuid\":\"738\",\"fileName\":\"20171122153313_合同文件.xlsx\",\"fileUuid\":null,\"editflag\":\"+\",\"fileType\":\"BID_OPEN\"},{\"uuid\":\"739\",\"fileName\":\"20171122153423_中标文件.xlsx\",\"fileUuid\":null,\"editflag\":\"+\",\"fileType\":\"BID_WIN\"}]}}";
        //==构造postData=========
        Map postData = new HashMap<>();
        postData.put("service","submitProjBid");
        postData.put("TOTP",OTP);
        postData.put("requestData",JSONUtil.deserialize(json));
        //============================
        Map params = new HashMap<>();
        params.put("actionName","upload-data-action");
        params.put("postData",JSONUtil.serialize(postData));
        HttpConnectionHelper.doPost(requestUrl,params);
        */
    }


}
