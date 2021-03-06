/*
 * Copyright (c) 2015. Catch Inc,
 */

package catchla.yep;

/**
 * Created by mariotaku on 15/4/30.
 */
public interface Constants {
    String YEP_DATABASE_NAME = "yep_data.db";
    int YEP_DATABASE_VERSION = 27;

    String LOGTAG = "Yep";

    String KEY_CURRENT_ACCOUNT = "current_account";

    String ACCOUNT_TYPE = BuildConfig.ACCOUNT_TYPE;
    String ACCOUNT_TYPE_PREFIX = ACCOUNT_TYPE + ":";
    String AUTH_TOKEN_TYPE = ACCOUNT_TYPE_PREFIX + "auth_token";

    String USER_DATA_ID = "id";
    String USER_DATA_AVATAR = "avatar";
    String USER_DATA_NICKNAME = "nickname";
    String USER_DATA_USERNAME = "username";
    String USER_DATA_PHONE_NUMBER = "phone_number";
    String USER_DATA_COUNTRY_CODE = "country_code";
    String USER_DATA_INTRODUCTION = "introduction";
    String USER_DATA_WEBSITE = "website";
    String USER_DATA_MASTER_SKILLS = "master_skills";
    String USER_DATA_LEARNING_SKILLS = "learning_skills";
    String USER_DATA_PROVIDERS = "providers";
    String USER_DATA_BADGE = "badge";

    String EXTRA_RESID = "resid";
    String EXTRA_TOKEN = "token";
    String EXTRA_ACCOUNT = "account";
    String EXTRA_ID = "id";
    String EXTRA_REPORT_TYPE = "report_type";
    String EXTRA_ITEM_TYPE = "item_type";
    String EXTRA_TOPIC = "topic";
    String EXTRA_CIRCLE = "circle";
    String EXTRA_USER = "user";
    String EXTRA_USER_ID = "user_id";
    String EXTRA_SKILL_ID = "skill_id";
    String EXTRA_CONVERSATION = "conversation";
    String EXTRA_SKILL = "skill";
    String EXTRA_NEW_TOPIC_TYPE = "new_topic_type";
    String EXTRA_READ_CACHE = "read_cache";
    String EXTRA_READ_OLD = "read_old";
    String EXTRA_LEARNING = "learning";
    String EXTRA_MASTER = "master";
    String EXTRA_PROVIDER_NAME = "provider_name";
    String EXTRA_SKILLS = "skills";
    String EXTRA_QUERY = "query";
    String EXTRA_MEDIA = "media";
    String EXTRA_CURRENT_MEDIA = "current_media";
    String EXTRA_PAGE = "page";
    String EXTRA_CACHING_ENABLED = "caching_enabled";
    String EXTRA_MAX_ID = "max_id";
    String EXTRA_SNAPSHOT = "snapshot";
    String EXTRA_LOCATION = "location";
    String EXTRA_NAME = "name";
    String EXTRA_BOUNDS = "bounds";
    String EXTRA_ATTACHMENT = "attachment";
    String EXTRA_POSITION = "position";
    String EXTRA_SHOW_SEARCH_BOX = "show_search_box";

    String EXTRA_INCLUDE_USER = "include_user";
    String EXTRA_INCLUDE_CHAT_HISTORY = "include_chat_history";
    String EXTRA_INCLUDE_TOPICS = "include_topics";

    int TABLE_ID_FRIENDSHIPS = 1;
    int TABLE_ID_MESSAGES = 11;
    int TABLE_ID_CONVERSATIONS = 12;

    int TABLE_ID_CONVERSATION_MESSAGES = 21;

    int VIRTUAL_TABLE_ID_CONVERSATIONS = 111;

    String AUTHORITY_YEP_CACHE = BuildConfig.APPLICATION_ID + ".cache";
    String AMAP_WEB_API_KEY = "be626270524e0af680685beabc7c65af";
}
