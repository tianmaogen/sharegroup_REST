package com.sharegroup.rest.packet;

import com.sharegroup.rest.bean.AuthenticationToken;
import org.jivesoftware.openfire.plugin.rest.entity.UserEntities;
import org.jivesoftware.openfire.plugin.rest.entity.UserEntity;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class RestApiClient.
 */
public class RestApiClient {

	/** The rest client. */
	private RestClient restClient;

	/**
	 * Instantiates a new rest api client.
	 *
	 * @param url
	 *            the url
	 * @param port
	 *            the port
	 * @param authenticationToken
	 *            the authentication token
	 */
	public RestApiClient(String url, int port, AuthenticationToken authenticationToken) {
		if (!url.startsWith("http")) {
			url = "http://" + url;
		}
		restClient = new RestClient.RestClientBuilder(url + ":" + port).authenticationToken(authenticationToken)
				.connectionTimeout(5000).build();
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public UserEntities getUsers() {
		UserEntities userEntities = restClient.get("users", UserEntities.class, new HashMap<String, String>());
		return userEntities;
	}

	/**
	 * Gets the users.
	 *
	 * @param queryParams
	 *            the query params
	 * @return the users
	 */
	public UserEntities getUsers(Map<String, String> queryParams) {
		UserEntities userEntities = restClient.get("users", UserEntities.class, queryParams);
		return userEntities;
	}

	/**
	 * Gets the user.
	 *
	 * @param username
	 *            the username
	 * @return the user
	 */
	public UserEntity getUser(String username) {
		UserEntity userEntity = restClient.get("users/" + username, UserEntity.class, new HashMap<String, String>());
			return userEntity;
}

	/**
	 * Creates the user.
	 *
	 * @param userEntity
	 *            the user entity
	 * @return the response
	 */
	public Response createUser(UserEntity userEntity) {
		return restClient.post("users", userEntity, new HashMap<String, String>());
	}

	/**
	 * Update user.
	 *
	 * @param userEntity
	 *            the user entity
	 * @return the response
	 */
	public Response updateUser(UserEntity userEntity) {
		return restClient.put("users/" + userEntity.getUsername(), userEntity, new HashMap<String, String>());
	}

	/**
	 * Delete user.
	 *
	 * @param username
	 *            the username
	 * @return the response
	 */
	public Response deleteUser(String username) {
		return restClient.delete("users/" + username, new HashMap<String, String>());
	}


	/**
	 * Lockout user.
	 *
	 * @param username
	 *            the username
	 * @return the response
	 */
	public Response lockoutUser(String username) {
		return restClient.post("lockouts/" + username, null, new HashMap<String, String>());
	}

	/**
	 * Unlock user.
	 *
	 * @param username
	 *            the username
	 * @return the response
	 */
	public Response unlockUser(String username) {
		return restClient.delete("lockouts/" + username, new HashMap<String, String>());
	}


	/**
	 * Gets the rest client.
	 *
	 * @return the rest client
	 */
	public RestClient getRestClient() {
		return restClient;
	}

}
