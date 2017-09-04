package com.xiberty.propongo;



public class Constants {

    /**
     * CONFIGURATION
     **/
    public static final String APPLICATION_PREFIX = "yopropongo";
    public static final String PACKAGE_NAME = "com.xiberty.propongo";
    public static final String SYNC_ACCOUNT_TYPE = PACKAGE_NAME;

    public static final String OAUTH_CREDENTIAL = "oauth_credential";
    public static final String CREDENTIAL_COLLECTION = "credential_collection";
    public static final String USER_PROFILE = APPLICATION_PREFIX  + "_user";

    public static final String DATABASE_NAME = "yopropongo";
    public static final int DATABASE_VERSION = 3;

    public static final int DEFAULT_COUNCIL = 1;

    /**
     * PREFERENCE KEYS
     **/

    public static final String COUNCIL_COLLECTION = "councils";
    public static final String COUNCILMAN_COLLECTION = "councilman";
    public static final String COMMISSION_COLLECTION = "commission";
    public static final String COUNCIL_SINGLE = "council";


    public static final String SERVER_URL = "http://192.168.0.112:8000";
//    public static final String SERVER_URL = "http://192.168.1.225:8000";
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
    public static final String LOGIN_ENDPOINT = "/api/auth/login/";
    public static final String LOGOUT_ENDPOINT = "/api/auth/logout/";

    public static final String RESET_PASSWORD_ENDPOINT = "/api/auth/reset-password/";
    public static final String REGISTER_ENDPOINT = "/api/auth/register/";
    public static final String FACEBOOK_LOGIN_ENDPOINT = "/api/auth/facebook/login/";


    // Account
    public static final String PROFILE_ENDPOINT = "/api/account/profile/";
    public static final String CHANGE_EMAIL_ENDPOINT = "/api/account/change-email/";
    public static final String CHANGE_PASSWORD_ENDPOINT = "/api/account/change-password/";
    public static final String MY_PROPOSALS_ENDPOINT ="/api/account/proposals/" ;


    // Councils EndPoints
    public static final String COUNCIL_ENDPOINT = "/api/councils/";
    public static final String COMISSIONS_ENDPOINT = "/api/councils/{pk}/commissions/";
    public static final String COUNCILMEN_ENDPOINT = "/api/councils/{pk}/councilmen/";
    public static final String COUNCILMEN_INBOX_ENDPOINT = "/api/councils/councilmen/inbox/";
    public static final String PROPOSALS_ENDPOINT = "/api/councils/{pk}/proposals/";
    public static final String PROPOSAL_ROOT_ENDPOINT = "/api/councils/proposals/";
    public static final String CONTACTS_ENDPOINT = "/api/councils/contacts/";
    /**
     * KEYS BETWEEN UI
     **/
    public static final String KEY_PROPOSAL_ID = "proposal_id";
    public static final String KEY_COUNCILMAN_ID = "councilman_id";
    public static final String KEY_COMMISSION_ID = "commission_id";
    public static final String KEY_COUNCIL_ID = "council_id";


    public static final String KEY_BASE_CLASS = "base_class";


    public static final String KEY_COMMENTS = "comments_list";

    public static final String KEY_IS_ALONE = "is_alone";


    public static final String MENU_STATE ="menu_state";
}
