package com.practise.http;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

/**
 * @author: realz
 * @package: com.practise.http
 * @date: 2019-11-15
 * @email: zlp951116@hotmail.com
 */
public class HttpsTest {
    public static void main1(String[] args) throws IOException {
        try {
            URL url = new URL("https://sso.inovance.com/sso/serviceValidate?ticket=ST-57-nNNfAQLfa7OuCBx9GTyi-cas&service=https%3a%2f%2finovancestage.huilianyi.com%2f");
//            URL url = new URL("https://login.hand-china.com/sso/login");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();
        } catch (Exception e) {
            System.out.println("failed:".concat(e.getMessage()));
        }

    }
}
