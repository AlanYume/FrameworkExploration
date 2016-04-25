package org.smart4j.plugin.security.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * 判断当前用户是否拥有其中某一种权限（逗号分隔，表示“或”的关系）
 */
public class HasAnyPermissionsTag extends PermissionTag {

    private static final String PERMISSION_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(final String permissionNames) {
        boolean hasAnyPermission = false;
        final Subject subject = getSubject();
        if (subject != null) {
            for (final String permissionName : permissionNames.split(PERMISSION_NAMES_DELIMITER)) {
                if (subject.isPermitted(permissionName.trim())) {
                    hasAnyPermission = true;
                    break;
                }
            }
        }
        return hasAnyPermission;
    }
}
