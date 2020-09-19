package com.plugins.mybaitslog;

import com.plugins.mybaitslog.util.SqlProUtil;
import org.junit.Test;

/**
 * @author lk
 * @version 1.0
 * @date 2020/9/19 11:17
 */
public class SqlTest {

    @Test
    public void format() {
        String preparingLine = "2020.09.19 11:12:40 CST DEBUG com.cms.dao.OrgAdminRoleDao.queryInForParentId - ==>  Preparing: select `rows`, `f_id`, `f_parent`, `f_role_id`, `createtime`, `updatedtime` from cms.t_org_admin_role WHERE f_parent in ( ? , ? , ? ) \n";
        String parametersLine = "2020.09.19 11:12:40 CST DEBUG com.cms.dao.OrgAdminRoleDao.queryInForParentId - ==> Parameters: 4378097006489370624(String), 4382994884114513920(String), 4383415103714754575(String)\n";
        final String[] strings = SqlProUtil.restoreSql(null, preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }
}
