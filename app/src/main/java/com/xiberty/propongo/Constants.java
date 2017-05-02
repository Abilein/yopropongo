package com.xiberty.propongo;



public class Constants {

    /**
     * CONFIGURATION
     **/
    public static final String APPLICATION_PREFIX = "warpp";
    public static final String OAUTH_CREDENTIAL = "oauth_credential";
    public static final String CREDENTIAL_COLLECTION = "credential_collection";
    public static final String USER_PROFILE = APPLICATION_PREFIX  + "_user";

    public static final String SERVER_URL = "http://192.168.0.102:9000";
    public static final String CLIENT_ID = "bgzxrqgTnmCzHxq5CJTusph5g7WpvhQmHLGQQehH";
    public static final String CLIENT_SECRET = "L7B0YRVoIy62a8PYCZc2r3VGPQscujFW6nwV97WV7UpVlMNVHEsV5PIyCs7bWnEGZV3XKZhoTYjq6fysMWRNhTq8aSBCjFBjwW6v57ccXViWmu0DeTC7111aNlHa0XPU";


    /**
     * REQUEST / RESPONSE  - HEADERS
     **/
    public static final String HEADER_PAGE_NEXT = "Page-Next";
    public static final String HEADER_PAGE_SIZE = "Page-Size";
    public static final String HEADER_PAGE_PREV = "Page-Prev";
    public static final String HEADER_PAGINATED = "Paginated";

    /**
     * ENDPOINTS
     **/

    // Auth
    public static final String LOGIN_ENDPOINT = "/api/credentials/login/";
    public static final String LOGOUT_ENDPOINT = "/api/credentials/revoke-token/";
    public static final String RESET_PASSWORD_ENDPOINT = "/api/credentials/reset-password/";
    public static final String REGISTER_ENDPOINT = "/api/credentials/register/";
    public static final String FACEBOOK_LOGIN_ENDPOINT = "/api/credentials/facebook/login/";


    // Account
    public static final String PROFILE_ENDPOINT = "/api/accounts/profile/";
    public static final String CHANGE_EMAIL_ENDPOINT = "/api/accounts/change-email/";
    public static final String CHANGE_PASSWORD_ENDPOINT = "/api/accounts/change-password/";


}
