//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package apitest.restassuredFuntions;

import apitest.actions.HttpOperation;
import apitest.actions.TokenType;
import apitest.actions.ValidatorOperation;
import apitest.interfaces.IApi;
import apitest.utilities.Helper;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.*;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class API implements IApi {
    public static PrintStream log;
    public static RequestSpecification reqSpec;
    HttpOperation method;
    String url;
    public static Response resp;

    public API() {
    }

    public void url(String url, HttpOperation method) {
        this.url = url;
        this.method = method;
        reqSpec = RestAssured.given();

        try {
            if (log == null) {
                log = new PrintStream("Logs.txt");
            } else {
                log = log;
            }
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        }

        reqSpec.filter(RequestLoggingFilter.logRequestTo(log)).filter(ResponseLoggingFilter.logResponseTo(log));
        reqSpec.config(RestAssured.config().logConfig(LogConfig.logConfig().blacklistHeader("set-cookie", new String[0])));
    }

    public void baseUrl(String baseConst) {
        Helper getHelp = Helper.getInstance();
        getHelp.set_path("src/main/resources/Constants.properties");

        try {
            RestAssured.baseURI = getHelp.loadProperties(baseConst);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void setHeader(Map<String, String> header) {
        reqSpec.headers(header);
    }

    public void setHeader(String[][] head) {
        for (int row = 0; row < head.length; ++row) {
            for (int col = 0; col < head[row].length; ++col) {
                reqSpec.header(head[row][col], head[row][col + 1], new Object[0]);
            }
        }

    }

    public void setHeader(String head, String val) {
        reqSpec.header(head, val, new Object[0]);
    }

    public void setBody(Object payload) {
        reqSpec.body(payload);
    }

    public void setContentType(String key) {
        reqSpec.contentType(key);
    }

    public void setFormParam(String key, String val) {
        reqSpec.formParam(key, new Object[]{val});
    }

    public void setQueryParam(String key, String val) {
        reqSpec.queryParam(key, new Object[]{val});
    }

    public String callIt() {
        if (this.method.toString().equalsIgnoreCase("get")) {
            resp = (Response) reqSpec.get(this.url, new Object[0]);
            return resp.asString();
        } else if (this.method.toString().equalsIgnoreCase("post")) {
            resp = (Response) reqSpec.post(this.url, new Object[0]);
            return resp.asString();
        } else if (this.method.toString().equalsIgnoreCase("patch")) {
            resp = (Response) reqSpec.patch(this.url, new Object[0]);
            return resp.asString();
        } else if (this.method.toString().equalsIgnoreCase("put")) {
            resp = (Response) reqSpec.put(this.url, new Object[0]);
            return resp.asString();
        } else if (this.method.toString().equalsIgnoreCase("delete")) {
            resp = (Response) reqSpec.delete(this.url, new Object[0]);
            return resp.asString();
        } else {
            return "invalid method set for API";
        }
    }

    public String extractString(String path) {
        return resp.jsonPath().getString(path);
    }

    public int extractInt(String path) {
        return resp.jsonPath().getInt(path);
    }

    public List<String> extractList(String path) {
        return resp.jsonPath().getList(path);
    }

    public Object extractIt(String path) {
        return resp.jsonPath().get(path);
    }

    public String extractHeader(String header_name) {
        return resp.header(header_name);
    }

    public String getResponseString() {
        return resp.asString();
    }

    public void fileUpload(String key, String path, String mime) {
        reqSpec.multiPart(key, new File(path), mime);
    }

    public void multiPartString(String key, String input) {
        reqSpec.multiPart(key, input);
    }

    public void printResp() {
        resp.print();
    }

    public String getCookieValue(String cookieName) {
        return resp.getCookie(cookieName);
    }

    public void ListResponseHeaders() {
        Headers allHeaders = resp.headers();
        Iterator var2 = allHeaders.iterator();

        while (var2.hasNext()) {
            Header header = (Header) var2.next();
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

    }

    public int getStatusCode() {
        return resp.getStatusCode();
    }

    public Headers getAllHeaders() {
        return resp.getHeaders();
    }

    public API assertStatusCode(int code, int optionalCode) {
        ((ValidatableResponse) resp.then()).statusCode(Matchers.anyOf(Matchers.equalTo(code), Matchers.equalTo(optionalCode)));
        return this;
    }

    public API assertIt(String key, Object val, ValidatorOperation operation) {
        switch (operation.toString()) {
            case "EQUALS":
                ((ValidatableResponse) resp.then()).body(key, Matchers.equalTo(val), new Object[0]);
            case "HAS_ALL":
            default:
                break;
            case "NOT_EQUALS":
                ((ValidatableResponse) resp.then()).body(key, Matchers.not(Matchers.equalTo(val)), new Object[0]);
                break;
            case "EMPTY":
                ((ValidatableResponse) resp.then()).body(key, Matchers.empty(), new Object[0]);
                break;
            case "NOT_EMPTY":
                ((ValidatableResponse) resp.then()).body(key, Matchers.not(Matchers.emptyArray()), new Object[0]);
                break;
            case "NOT_NULL":
                ((ValidatableResponse) resp.then()).body(key, Matchers.notNullValue(), new Object[0]);
                break;
            case "NULL":
                ((ValidatableResponse) resp.then()).body(key, IsNull.nullValue(), new Object[0]);
                break;
            case "HAS_STRING":
                ((ValidatableResponse) resp.then()).body(key, Matchers.containsString((String) val), new Object[0]);
                break;
            case "SIZE":
                ((ValidatableResponse) resp.then()).body(key, Matchers.hasSize((Integer) val), new Object[0]);
        }

        return this;
    }

    public API assertStatusCode(int code) {
        ((ValidatableResponse) resp.then()).statusCode(code);
        return this;
    }

    public API assertJsonObject(String key, ValidatorOperation operation) throws JSONException {
        JSONObject obj = new JSONObject(resp.asString());
        switch (operation.toString()) {
            case "KEY_PRESENTS":
                if (obj.has(key)) {
                }
            default:
                return this;
        }
    }

    public static void assertJsonArrayObject(String key, ValidatorOperation operation) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp.asString());
        int i = 0;

        while (i < jsonArray.length()) {
            JSONObject responseKey = jsonArray.getJSONObject(i);
            switch (operation.toString()) {
                case "KEY_PRESENTS":
                    if (responseKey.has(key)) {
                    }
                default:
                    ++i;
            }
        }

    }

    public static <T> void assertCrossUserData(String key, T value) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp.asString());
        boolean matchFound = false;

        for (int i = 0; i < jsonArray.length(); ++i) {
            Object responseKey = jsonArray.getJSONObject(i).get(key);
            if (responseKey.equals(value)) {
                Assert.assertEquals(responseKey, value);
                matchFound = true;
            }
        }

        if (!matchFound) {
            throw new AssertionError("No matching value found for key: " + key + " with expected value: " + value);
        }
    }

    public static <T> JSONObject assertJsonArrayObject(String key, T value) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp.asString());
        JSONObject jsonObject = null;
        boolean matchFound = false;

        for (int i = 0; i < jsonArray.length(); ++i) {
            jsonObject = jsonArray.getJSONObject(i);
            String responseKey = jsonObject.getString(key);
            if (responseKey.equals(value)) {
                Assert.assertEquals(responseKey, value);
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            throw new AssertionError("No matching value found for key: " + key + " with expected value: " + value);
        } else {
            return jsonObject;
        }
    }

    public static <T> void assertSearchJsonObject(JSONObject jsonObject, String key, T value) throws JSONException {
        boolean matchFound = false;
        Object responseKey = jsonObject.get(key);
        if (responseKey.equals(value)) {
            Assert.assertEquals(responseKey, value);
            matchFound = true;
        }

        if (!matchFound) {
            throw new AssertionError("No matching value found for key: " + key + " with expected value: " + value);
        }
    }

    public static void assertJsonArrayObject(String key, List<String> value) throws JSONException {
        boolean matchFound = false;
        JSONArray jsonArray = new JSONArray(resp.asString());

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONArray responseKey = jsonArray.getJSONObject(i).getJSONArray(key);
            JSONArray valueJson = new JSONArray(value);
            if (responseKey.toString().equals(valueJson.toString())) {
                assertString(responseKey.toString(), valueJson.toString());
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            throw new AssertionError("No matching value found for key: " + key + " with expected value: " + value);
        }
    }

    public <T extends String> void assertJsonPathStringFromJsonArray(T path, T value) throws JSONException {
        boolean matchFound = false;
        JSONArray jsonArray = new JSONArray(resp.asString());

        for (int i = 0; i < jsonArray.length(); ++i) {
            String jsonResponse = jsonArray.getJSONObject(i).toString();
            JsonPath jsonPath = new JsonPath(jsonResponse);
            String key = jsonPath.getString(path);
            if (key.equalsIgnoreCase(value)) {
                assertString(key, value);
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            throw new AssertionError("No matching value found for key: " + path + " with expected value: " + value);
        }
    }

    public <T extends String> void assertStringUsingJsonPath(T path, T value) throws JSONException {
        JsonPath jsonPath = new JsonPath(resp.asString());
        boolean matchFound = false;
        if (jsonPath.getString(path).equalsIgnoreCase(value)) {
            matchFound = true;
            assertString(jsonPath.getString(path), value);
        }

        if (!matchFound) {
            throw new AssertionError("No matching value found for key: " + path + " with expected value: " + value);
        }
    }

    public void findStringInList(String key) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp.asString());
        String responseJson = jsonArray.toString();
        Assert.assertTrue(responseJson.contains(key));
    }

    public void assertJsonArrayStringFromJsonObject(String path, String key, String value) throws JSONException {
        new JSONObject(resp.asString());
        JSONArray jsonArray = new JSONArray(this.extractList(path));
        boolean matchFound = false;

        for (int i = 0; i < jsonArray.length(); ++i) {
            String jsonResponse = jsonArray.getJSONObject(i).toString();
            JsonPath jsonPath = new JsonPath(jsonResponse);
            String keyJson = jsonPath.getString(key);
            if (keyJson.equalsIgnoreCase(value)) {
                assertString(keyJson, value);
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            throw new AssertionError("No matching value found for key: " + key + " with expected value: " + value);
        }
    }

    public Map<String, String> mapParse(String mapperName) throws JSONException {
        Map<String, String> map1 = new HashMap();
        JSONObject inputJson = new JSONObject(resp.asString());
        String js = inputJson.getJSONObject(mapperName).toString();
        String[] str = js.replaceAll("[{}]", "").replaceAll("\"", "").split(",");
        String[] var6 = str;
        int var7 = str.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String s = var6[var8];
            String[] entry = s.split(":");
            String key = entry[0].trim();
            String value = entry[1].trim();
            map1.put(key, value);
        }

        return map1;
    }

    public static void assertAll(String input, String output) {
        try {
            JSONAssert.assertEquals(input, output, false);
        } catch (JSONException var3) {
            var3.printStackTrace();
        }

    }

    @DataProvider(
            name = "tokenTypes"
    )
    public static Object[] tokenTypes() {
        return new Object[]{TokenType.XToken, TokenType.APICredentials, TokenType.APIKey};
    }

    public static void assertAll(String input, String output, String strict) {
        try {
            JSONAssert.assertEquals(input, output, true);
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

    }

    public static void assertInt(int actual, int expected, String error_message) {
        Assert.assertEquals(actual, expected, error_message);
    }

    public static String getValue(String input, String array, int pos, String key) throws JSONException {
        JSONObject inputJson = new JSONObject(input);
        JSONArray jsonArray = inputJson.getJSONArray(array);
        JSONObject job = jsonArray.getJSONObject(pos);
        String Key = job.get(key).toString();
        return Key;
    }

    public static void assertSearchParamIsLesserThanResponse(String searchParam) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp.asString());

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String itineraryEndDate = jsonObject.getString("endDateTime");
            LocalDateTime responseDateTime = LocalDateTime.parse(itineraryEndDate);
            LocalDateTime searchparam = LocalDateTime.parse(searchParam);
            Assert.assertEquals(searchparam.isBefore(responseDateTime), true);
        }

    }

    public static void assertSearchParamIsGreaterThanResponse(String searchParam) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp.asString());

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String itineraryEndDate = jsonObject.getString("endDateTime");
            LocalDateTime responseDateTime = LocalDateTime.parse(itineraryEndDate);
            LocalDateTime searchparam = LocalDateTime.parse(searchParam);
            Assert.assertEquals(searchparam.isAfter(responseDateTime), true);
        }

    }

    public static void assertSearchParamIsBetweenTwoDates(String startDateTime, String endDateTime) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp.asString());

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String itineraryStartDate = jsonObject.getString("startDateTime");
            String itineraryEndDate = jsonObject.getString("endDateTime");
            LocalDateTime responseStartDateTime = LocalDateTime.parse(itineraryStartDate);
            LocalDateTime responseEndDateTime = LocalDateTime.parse(itineraryEndDate);
            LocalDateTime searchStartDateTime = LocalDateTime.parse(startDateTime);
            LocalDateTime searchEndDateTime = LocalDateTime.parse(endDateTime);
            Assert.assertTrue(searchStartDateTime.isAfter(responseStartDateTime) && searchStartDateTime.isBefore(responseEndDateTime) || searchEndDateTime.isAfter(responseStartDateTime) && searchEndDateTime.isBefore(responseEndDateTime));
        }

    }


    public static int jsonResponseCount() throws JSONException {
        JSONArray jsonArray = new JSONArray((resp.asString()));
        return jsonArray.length();
    }

    public static String getValue(String input, String key) throws JSONException {
        JSONObject inputJson = new JSONObject(input);
        String value = inputJson.get(key).toString();
        return value;
    }

    public static void assertString(String input, String output) {
        Assert.assertEquals(input, output);
    }

    public static void assertObjects(Object input, Object output) {
        Assert.assertEquals(input, output);
    }
}
