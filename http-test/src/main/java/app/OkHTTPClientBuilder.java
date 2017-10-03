package app;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author neo
 */
public class OkHTTPClientBuilder {
    public final OkHttpClient client;

    public OkHTTPClientBuilder() {
        client = createClient();
    }

    private OkHttpClient createClient() {
        try {
            X509TrustManager trustManager = new SelfSignedTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, new java.security.SecureRandom());

            return new OkHttpClient.Builder()
                    .hostnameVerifier(new SelfSignedHostnameVerifier())
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .connectionPool(new ConnectionPool())
                    .build();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static class SelfSignedTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        }
    }

    private static class SelfSignedHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }
}
