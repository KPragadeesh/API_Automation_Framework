package apitest.interfaces;

import java.util.List;

import apitest.actions.HttpOperation;
import apitest.actions.ValidatorOperation;


public interface IApi {

	public void url(String url, HttpOperation method);

	public void baseUrl(String baseConst);
	
	public void setHeader(String[][] head);

	public void setHeader(String head, String val);

	public void setBody(Object body);

	public void setFormParam(String key, String val);
	
	public void setContentType(String key);

	public void setQueryParam(String key, String val);

	public String callIt();

	public Object assertStatusCode(int code);

	public Object assertIt(String key, Object val, ValidatorOperation operation);

	public static void failTest(String expected, String present) {
		throw new AssertionError("Does not contain the expected\t " + expected + "\n but had\t" + present);
	}

	public static void failTest(String message) {
		throw new AssertionError(message);
	}

	public void fileUpload(String key, String path, String mime);

	public String extractString(String path);

	public int extractInt(String path);

	public List<String> extractList(String path);

	public Object extractIt(String path);

	public String extractHeader(String header_name);

	public String getResponseString();

	public void printResp();

}
