package com.kyq.test.liferay;


//import com.liferay.client.soap.portal.model.UserSoap;
//import com.liferay.client.soap.portal.service.http.UserServiceSoap;
//import com.liferay.client.soap.portal.service.http.UserServiceSoapServiceLocator;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-07-02 10:31
 */
public class CallLiferayService {

    public static void main(String args[]) {
        new CallLiferayService().portalClient();
    }

    private static URL getURL(String serviceName) {
        try {
//            return new URL(sb.toString());
            return new URL("http://test:test@127.0.0.1:8080/api/axis/Portal_UserService");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void portalClient(){
//        UserServiceSoapServiceLocator locator = new UserServiceSoapServiceLocator();
//        UserServiceSoap portalUserService = null;
//        try {
//            portalUserService = locator.getPortal_UserService(getURL("Portal_UserService"));
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        try {
//            //38911L
//            UserSoap userSoap = portalUserService.getUserById(38911L);
//            System.out.println("userContact:"+userSoap.getContactId()+"");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//

    }
}
