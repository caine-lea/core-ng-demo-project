import app.OkHTTPClientBuilder;
import app.demo.api.product.CreateProductRequest;
import core.framework.api.http.ContentType;
import core.framework.api.http.HTTPClient;
import core.framework.api.http.HTTPClientBuilder;
import core.framework.api.http.HTTPRequest;
import core.framework.api.http.HTTPResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.util.Charsets;
import core.framework.api.util.JSON;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author neo
 */
public class Main {
    static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        String url = "https://localhost:8443/product";
//        String url = "https://localhost:8443/product";
        int times = 5000;
        testHTTPClient(url, times);
////        Thread.sleep(5000);
        testOkHTTP(url, times);

        executor.shutdown();
    }

    private static void testHTTPClient(String uri, int times) throws InterruptedException {
        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.id = "123";
        createProductRequest.name = "name";
        String json = JSON.toJSON(createProductRequest);

        HTTPClient client = new HTTPClientBuilder().build();
        long start = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            executor.execute(() -> {
                HTTPResponse response = client.execute(HTTPRequest.post(uri).body(json, ContentType.APPLICATION_JSON));
                if (response.status() != HTTPStatus.CREATED) throw new Error("non 200");
                response = client.execute(HTTPRequest.get(uri + "/123"));
                if (response.status() != HTTPStatus.OK) throw new Error("non 200 " + response.status());
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("httpClient end => " + (System.currentTimeMillis() - start));
    }

    private static void testOkHTTP(String url, int times) throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException {
        OkHttpClient client = new OkHTTPClientBuilder().client;

        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.id = "123";
        createProductRequest.name = "name";
        byte[] json = JSON.toJSON(createProductRequest).getBytes(Charsets.UTF_8);
        MediaType mediaType = MediaType.parse(ContentType.APPLICATION_JSON.toString());

        CountDownLatch latch = new CountDownLatch(times);
        long start = System.currentTimeMillis();

        for (int i = 0; i < times; i++) {
            executor.execute(() -> {
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .post(RequestBody.create(mediaType, json))
                            .build();
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new Error("non 200");
                    byte[] bytes = response.body().bytes();
                    response.close();

                    request = new Request.Builder()
                            .url(url + "/123")
                            .get().build();
                    response = client.newCall(request).execute();
                    if (!response.isSuccessful()) throw new Error("non 200");
                    response.body().bytes();
                    response.close();
                    latch.countDown();
                } catch (Exception e) {
                    throw new Error(e);
                }
            });
        }
        latch.await();
        System.out.println("ok Http end => " + (System.currentTimeMillis() - start));
    }
}
