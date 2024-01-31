package apitest.baseAPI;
import java.util.HashMap;
import java.util.Map;
import apitest.actions.HttpOperation;
import apitest.actions.TokenType;
import apitest.restassuredFuntions.API;
import apitest.utilities.Helper;


public class APIConstructor extends API{
	public static String token;
	public static String bearerToken;
	public static String apitoken;
	Helper helper = Helper.getInstance();
	String clientIdGlobal = helper.loadProperties("clientId");
	String nonceGlobal = helper.loadProperties("nonce");
	String apiUserNameGlobal = helper.loadProperties("apiusername");


	private void createToken(String url, String clientId, String passWord) {
		baseUrl("baseUrl");
		url(url , HttpOperation.POST);
		setContentType("application/json");
		setBody("{\r\n"
				+ "    \"username\": \""+clientId+"\",\r\n"
				+ "    \"password\": \""+passWord+"\",\r\n"
				+ "    \"populateValidTenants\": true\r\n"
				+ "}");
	}


	private void createBearerToken(String url, String grantType, String clientId, String passWord) {
		baseUrl("baseUrl");
		url(url , HttpOperation.POST);
		setContentType("application/x-www-form-urlencoded");
		setFormParam("grant_type", grantType);
		setFormParam("client_id", clientId);
		setFormParam("client_secret", passWord);

	}

	private void apiKeyToken(String url, String clientId, String nonce, String apiusername) {
		baseUrl("baseUrl");
		url(url , HttpOperation.POST);
		setContentType("application/json");
		setBody("{\r\n"
				+ "    \"clientId\": \""+clientId+"\",\r\n"
				+ "    \"nonce\": \""+nonce+"\",\r\n"
				+ "    \"apiUserName\": \""+apiusername+"\"\r\n"
				+ "}");
	}


	public void withPayload(String url, HttpOperation operation, Object payload , TokenType tokenType) {
		switch (tokenType) {
		case XToken:
			baseUrl("baseUrl");
			url(url , operation);
			setHeader("X-Token",token);
			setContentType("application/json");
			setBody(payload);
			callIt();
			break;

		case APIKey:
			baseUrl("baseUrl");
			url(url , operation);
			setHeader("Api-Key-Token",apitoken);
			setContentType("application/json");
			setBody(payload);
			callIt();
			break;

		case APICredentials:
			baseUrl("baseUrl");
			url(url , operation);
			Map<String, String> mapHeader = new HashMap<String, String>();
			mapHeader.put("Client-Id", clientIdGlobal);
			mapHeader.put("Nonce", nonceGlobal);
			mapHeader.put("Api-User-Name", apiUserNameGlobal);
			setHeader(mapHeader);
			setContentType("application/json");
			setBody(payload);
			callIt();
			break;	

		case WithoutToken:
			baseUrl("baseUrl");
			url(url , operation);
			setContentType("application/json");
			setBody(payload);
			callIt();
			break;

		case Bearer:
			baseUrl("baseUrl");
			url(url , operation);
			setHeader("Authorization","Bearer "+bearerToken);
			setContentType("application/json");
			setBody(payload);
			callIt();
			break;
		}

	}

	public void withPayloadCustom(String url, HttpOperation operation, Object payload , TokenType tokenType, 
			Map<String, String> header, String contentType  ) {
		baseUrl("baseUrl");
		url(url , operation);
		setHeader(header);
		setContentType(contentType);
		setBody(payload);
		callIt();
	}

	public void withoutPayloadCustom(String url, HttpOperation operation, TokenType tokenType, 
			Map<String, String> header, String contentType ) {
		baseUrl("baseUrl");
		url(url , operation);
		setHeader(header);
		setContentType(contentType);
		callIt();
	}

	public void withoutPayload(String url, HttpOperation operation, TokenType tokenType) {
		switch (tokenType) {
		case XToken:
			baseUrl("baseUrl");
			url(url , operation);
			setHeader("X-Token",token);
			setContentType("application/json");
			callIt();
			break;

		case APIKey:
			baseUrl("baseUrl");
			url(url , operation);
			setHeader("Api-Key-Token",apitoken);
			setContentType("application/json");
			callIt();
			break;

		case APICredentials:
			baseUrl("baseUrl");
			url(url , operation);
			Map<String, String> mapHeader = new HashMap<String, String>();
			mapHeader.put("Client-Id", clientIdGlobal);
			mapHeader.put("Nonce", nonceGlobal);
			mapHeader.put("Api-User-Name", apiUserNameGlobal);
			setHeader(mapHeader);
			setContentType("application/json");
			callIt();
			break;

		case WithoutToken:
			baseUrl("baseUrl");
			url(url , operation);
			setContentType("application/json");
			callIt();
			break;

		case Bearer:
			baseUrl("baseUrl");
			url(url , operation);
			setHeader("Authorization","Bearer "+bearerToken);
			setContentType("application/json");
			callIt();
			break;


		}
	}

	public String getApiKeyToken(String url, String clientId, String nonce, String apiusernmae ) {
		if(url.contains(helper.loadProperties("invalidtenant")) || !clientId.equalsIgnoreCase(clientIdGlobal) 
				|| !nonce.equalsIgnoreCase(nonceGlobal) || !apiusernmae.equalsIgnoreCase(apiUserNameGlobal)){
			apiKeyToken(url, clientId, nonce, apiusernmae);
			String response = callIt();
			return response;
		}
		else{
			apiKeyToken(url, clientId, nonce, apiusernmae);
			String response = callIt();
			apitoken = extractString("token");
			return response;
		}
	}


	public String getV2ApiKeyToken(String url, String clientId, String nonce, String apiusernmae ) {
		apiKeyToken(url, clientId, nonce, apiusernmae);
		String response = callIt();
		apitoken = extractString("token");
		return response;
	}

	public String getLoginToken(String url, String clientId, String passWord) {
		createToken(url, clientId, passWord);
		String response = callIt();
		token=extractString("token");
		return response;
	}

	public String getAccessToken(String url, String grantType, String clientId, String passWord) {
		createBearerToken(url, grantType, clientId, passWord);
		String response = callIt();
		bearerToken=extractString("access_token");
		return response;
	}


	public String UrlConstructor(String api, String pathParam1) {
		return String.format(api, pathParam1);
	}

	public String UrlConstructor(String api, String pathParam1, String pathParam2) {
		return String.format(api, pathParam1, pathParam2);
	}

	public String UrlConstructor(String api, String pathParam1, String pathParam2, String pathParam3) {
		return String.format(api, pathParam1, pathParam2, pathParam3);
	}

}
