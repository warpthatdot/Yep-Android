package catchla.yep.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.hannesdorfmann.parcelableplease.ParcelBagger;
import com.hannesdorfmann.parcelableplease.annotation.Bagger;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableThisPlease;

import org.mariotaku.library.objectcursor.annotation.CursorField;
import org.mariotaku.library.objectcursor.annotation.CursorObject;
import org.mariotaku.library.objectcursor.converter.CursorFieldConverter;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import catchla.yep.R;
import catchla.yep.model.util.LoganSquareCursorFieldConverter;
import catchla.yep.model.util.ProviderConverter;
import catchla.yep.model.util.SkillListTypeConverter;
import catchla.yep.model.util.TimestampToDateConverter;
import catchla.yep.model.util.YepTimestampDateConverter;
import catchla.yep.provider.YepDataStore;
import catchla.yep.provider.YepDataStore.Users;

/**
 * Created by mariotaku on 15/5/8.
 */
@ParcelablePlease
@JsonObject
@CursorObject(valuesCreator = true, tableInfo = true)
public class User implements Parcelable {

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @CursorField(value = Users._ID, type = YepDataStore.TYPE_PRIMARY_KEY, excludeWrite = true)
    long _id;

    @ParcelableThisPlease
    @JsonField(name = "master_skills", typeConverter = SkillListTypeConverter.class)
    @CursorField(value = Users.MASTER_SKILLS, converter = LoganSquareCursorFieldConverter.class)
    List<Skill> masterSkills;
    @ParcelableThisPlease
    @JsonField(name = "learning_skills", typeConverter = SkillListTypeConverter.class)
    @CursorField(value = Users.LEARNING_SKILLS, converter = LoganSquareCursorFieldConverter.class)
    List<Skill> learningSkills;
    @ParcelableThisPlease
    @CursorField(Users.ACCOUNT_ID)
    String accountId;
    @ParcelableThisPlease
    @JsonField(name = "id")
    @CursorField(Users.FRIEND_ID)
    String id;
    @ParcelableThisPlease
    @JsonField(name = "username")
    @CursorField(Users.USERNAME)
    String username;
    @ParcelableThisPlease
    @JsonField(name = "nickname")
    @CursorField(Users.NICKNAME)
    String nickname;
    @ParcelableThisPlease
    @JsonField(name = "introduction")
    @CursorField(Users.INTRODUCTION)
    String introduction;
    @ParcelableThisPlease
    @JsonField(name = "avatar_url")
    @CursorField(Users.AVATAR_URL)
    String avatarUrl;
    @ParcelableThisPlease
    @JsonField(name = "avatar_thumb_url")
    @CursorField(Users.AVATAR_THUMB_URL)
    String avatarThumbUrl;
    @ParcelableThisPlease
    @JsonField(name = "avatar")
    Avatar avatar;
    @ParcelableThisPlease
    @JsonField(name = "mobile")
    @CursorField(Users.MOBILE)
    String mobile;
    @ParcelableThisPlease
    @JsonField(name = "phone_code")
    @CursorField(Users.PHONE_CODE)
    String phoneCode;
    @ParcelableThisPlease
    @JsonField(name = "contact_name")
    @CursorField(Users.CONTACT_NAME)
    String contactName;
    @ParcelableThisPlease
    @JsonField(name = "remarked_name")
    String remarkedName;
    @ParcelableThisPlease
    @JsonField(name = "providers", typeConverter = ProviderConverter.class)
    @CursorField(value = Users.PROVIDERS, converter = LoganSquareCursorFieldConverter.class)
    List<Provider> providers;
    @ParcelableThisPlease
    @JsonField(name = "latitude")
    double latitude = Double.NaN;
    @ParcelableThisPlease
    @JsonField(name = "longitude")
    double longitude = Double.NaN;
    @ParcelableThisPlease
    @Bagger(Badge.Bagger.class)
    @JsonField(name = "badge", typeConverter = Badge.Converter.class)
    @CursorField(value = Users.BADGE, converter = Badge.CursorConverter.class)
    @Nullable
    Badge badge;
    @ParcelableThisPlease
    @JsonField(name = "website_url")
    String websiteUrl;
    @ParcelableThisPlease
    @JsonField(name = "website_title")
    String websiteTitle;
    @ParcelableThisPlease
    LatLng location;
    @ParcelableThisPlease
    @JsonField(name = "created_at", typeConverter = YepTimestampDateConverter.class)
    @CursorField(value = Users.CREATED_AT, converter = TimestampToDateConverter.class, type = CursorField.INTEGER)
    Date createdAt;
    @ParcelableThisPlease
    @JsonField(name = "updated_at", typeConverter = YepTimestampDateConverter.class)
    @CursorField(value = Users.UPDATED_AT, converter = TimestampToDateConverter.class, type = CursorField.INTEGER)
    Date updatedAt;

    public User() {

    }

    public User(final Parcel src) {
        UserParcelablePlease.readFromParcel(this, src);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(final String accountId) {
        this.accountId = accountId;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(final LatLng location) {
        this.location = location;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(final List<Provider> providers) {
        this.providers = providers;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(final String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Nullable
    public String getAvatarUrl() {
        if (avatar != null) return avatar.url;
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Nullable
    public Avatar getAvatar() {
        return avatar;
    }

    @Nullable
    public String getAvatarThumbUrl() {
        if (avatar != null) return avatar.thumbUrl;
        return avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayUsername() {
        if (username == null) return null;
        return "@" + username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public List<Skill> getMasterSkills() {
        return masterSkills;
    }

    public void setMasterSkills(List<Skill> masterSkills) {
        this.masterSkills = masterSkills;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(final String contactName) {
        this.contactName = contactName;
    }

    public List<Skill> getLearningSkills() {
        return learningSkills;
    }

    public void setLearningSkills(List<Skill> learningSkills) {
        this.learningSkills = learningSkills;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(final String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteTitle() {
        return websiteTitle;
    }

    public void setWebsiteTitle(final String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    @Nullable
    public Badge getBadge() {
        return badge;
    }

    public void setBadge(@Nullable final Badge badge) {
        this.badge = badge;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRemarkedName() {
        return remarkedName;
    }

    public void setRemarkedName(final String remarkedName) {
        this.remarkedName = remarkedName;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", masterSkills=" + masterSkills +
                ", learningSkills=" + learningSkills +
                ", accountId='" + accountId + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", introduction='" + introduction + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", avatarThumbUrl='" + avatarThumbUrl + '\'' +
                ", avatar=" + avatar +
                ", mobile='" + mobile + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", contactName='" + contactName + '\'' +
                ", providers=" + providers +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", badge='" + badge + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", websiteTitle='" + websiteTitle + '\'' +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        if (_id != user._id) return false;
        if (Double.compare(user.latitude, latitude) != 0) return false;
        if (Double.compare(user.longitude, longitude) != 0) return false;
        if (masterSkills != null ? !masterSkills.equals(user.masterSkills) : user.masterSkills != null)
            return false;
        if (learningSkills != null ? !learningSkills.equals(user.learningSkills) : user.learningSkills != null)
            return false;
        if (accountId != null ? !accountId.equals(user.accountId) : user.accountId != null)
            return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null)
            return false;
        if (introduction != null ? !introduction.equals(user.introduction) : user.introduction != null)
            return false;
        if (avatarUrl != null ? !avatarUrl.equals(user.avatarUrl) : user.avatarUrl != null)
            return false;
        if (avatarThumbUrl != null ? !avatarThumbUrl.equals(user.avatarThumbUrl) : user.avatarThumbUrl != null)
            return false;
        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        if (mobile != null ? !mobile.equals(user.mobile) : user.mobile != null) return false;
        if (phoneCode != null ? !phoneCode.equals(user.phoneCode) : user.phoneCode != null)
            return false;
        if (contactName != null ? !contactName.equals(user.contactName) : user.contactName != null)
            return false;
        if (providers != null ? !providers.equals(user.providers) : user.providers != null)
            return false;
        if (badge != user.badge) return false;
        if (websiteUrl != null ? !websiteUrl.equals(user.websiteUrl) : user.websiteUrl != null)
            return false;
        if (websiteTitle != null ? !websiteTitle.equals(user.websiteTitle) : user.websiteTitle != null)
            return false;
        if (location != null ? !location.equals(user.location) : user.location != null)
            return false;
        if (createdAt != null ? !createdAt.equals(user.createdAt) : user.createdAt != null)
            return false;
        return updatedAt != null ? updatedAt.equals(user.updatedAt) : user.updatedAt == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (_id ^ (_id >>> 32));
        result = 31 * result + (masterSkills != null ? masterSkills.hashCode() : 0);
        result = 31 * result + (learningSkills != null ? learningSkills.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + (avatarThumbUrl != null ? avatarThumbUrl.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (phoneCode != null ? phoneCode.hashCode() : 0);
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
        result = 31 * result + (providers != null ? providers.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (badge != null ? badge.hashCode() : 0);
        result = 31 * result + (websiteUrl != null ? websiteUrl.hashCode() : 0);
        result = 31 * result + (websiteTitle != null ? websiteTitle.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @OnJsonParseComplete
    void onParseComplete() {
        if (Double.isNaN(latitude) || Double.isNaN(longitude)) {
            location = null;
        } else {
            location = new LatLng(latitude, longitude);
        }
        if (avatar != null) {
            avatarUrl = avatar.url;
            avatarThumbUrl = avatar.thumbUrl;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        UserParcelablePlease.writeToParcel(this, dest, flags);
    }

    public enum Badge {
        PALETTE("palette", R.drawable.ic_user_badge_palette),
        PLANE("plane", R.drawable.ic_user_badge_paperplane),
        HEART("heart", R.drawable.ic_user_badge_heart),
        STAR("star", R.drawable.ic_user_badge_star),
        BUBBLE("bubble", R.drawable.ic_user_badge_chatbubble),

        ANDROID("android", R.drawable.ic_user_badge_android),
        APPLE("apple", R.drawable.ic_user_badge_apple),
        PET("pet", R.drawable.ic_user_badge_paw),
        WINE("wine", R.drawable.ic_user_badge_wineglass),
        MUSIC("music", R.drawable.ic_user_badge_music),

        STEVE("steve", R.drawable.ic_user_badge_glasses),
        CAMERA("camera", R.drawable.ic_user_badge_camera),
        GAME("game", R.drawable.ic_user_badge_game),
        BALL("ball", R.drawable.ic_user_badge_basketball),
        TECH("tech", R.drawable.ic_user_badge_tech);
        private final String value;
        @DrawableRes
        private final int icon;

        Badge(String value, @DrawableRes int icon) {
            this.value = value;
            this.icon = icon;
        }

        @Nullable
        public static Badge parse(@Nullable final String string) {
            if (string == null) return null;
            for (final Badge badge : values()) {
                if (string.equals(badge.value)) {
                    return badge;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        public int getIcon() {
            return icon;
        }

        public static class Converter extends StringBasedTypeConverter<Badge> {
            @Override
            public Badge getFromString(final String string) {
                return Badge.parse(string);
            }

            @Override
            public String convertToString(final Badge object) {
                if (object == null) return null;
                return object.value;
            }
        }

        public static class Bagger implements ParcelBagger<Badge> {
            @Override
            public void write(final Badge value, final Parcel out, final int flags) {
                out.writeString(value != null ? value.value : null);
            }

            @Override
            public Badge read(final Parcel in) {
                return Badge.parse(in.readString());
            }
        }

        public static class CursorConverter implements CursorFieldConverter<Badge> {
            @Override
            public Badge parseField(final Cursor cursor, final int columnIndex, final ParameterizedType fieldType) {
                return Badge.parse(cursor.getString(columnIndex));
            }

            @Override
            public void writeField(final ContentValues values, final Badge object, final String columnName, final ParameterizedType fieldType) {
                values.put(columnName, object != null ? object.value : null);
            }
        }
    }
}
