package org.smart4j.chapter5;

import org.smart4j.framework.helper.DatabaseHelper;
import org.smart4j.plugin.security.SmartSecurity;

import java.util.Set;

/**
 * 应用安全控制
 */
public class AppSecurity implements SmartSecurity {

    public String getPassword(final String username) {
        final String sql = "SELECT password FROM user WHERE username = ?";
        return DatabaseHelper.query(sql, username);
    }

    public Set<String> getRoleNameSet(final String username) {
        final String sql =
                "SELECT r.role_name FROM user u, user_role ur, role r WHERE u.id = ur.user_id AND r.id = ur.role_id AND u.username = ?";
        return DatabaseHelper.querySet(sql, username);
    }

    public Set<String> getPermissionNameSet(final String roleName) {
        final String sql =
                "SELECT p.permission_name FROM role r, role_permission rp, permission p WHERE r.id = rp.role_id AND p.id = rp.permission_id AND r.role_name = ?";
        return DatabaseHelper.querySet(sql, roleName);
    }
}
