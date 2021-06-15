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
        final String[] strings = SqlProUtil.restoreSql(preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }

    @Test
    public void format2() {
        String preparingLine = "2020.09.19 11:12:40 CST DEBUG com.cms.dao.OrgAdminRoleDao.queryInForParentId - ==>  Preparing: select `rows`, `f_id`, `f_parent`, `f_role_id`, `createtime`, `updatedtime` from cms.t_org_admin_role WHERE f_parent in ( ? , ? , ? ) \n";
        String parametersLine = "2020.09.19 11:12:40 CST DEBUG com.cms.dao.OrgAdminRoleDao.queryInForParentId - ==> Parameters: 4378097006489370624(String), 43829'9488411451,3'920(String), 4383415103714754575(String)\n";
        final String[] strings = SqlProUtil.restoreSql(preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }

    @Test
    public void format3() {
        String preparingLine = "2020.09.19 11:12:40 CST DEBUG com.cms.dao.OrgAdminRoleDao.queryInForParentId - ==>  Preparing: select `rows`, `f_id`, `f_parent`, `f_role_id`, `createtime`, `updatedtime` from cms.t_org_admin_role WHERE f_parent in ( ? , ? , ? ) \n";
        String parametersLine = "2020.09.19 11:12:40 CST DEBUG com.cms.dao.OrgAdminRoleDao.queryInForParentId - ==> Parameters: (String), 43829'9488411451,3'920(String), 4383415103714754575(String)\n";
        final String[] strings = SqlProUtil.restoreSql(preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }

    @Test
    public void format4() {
        String preparingLine = "13:17:00.087 [schedule-pool-8] DEBUG n.p.s.i.r.C.updateByPrimaryKeySelective - [debug,159] - ==>  Preparing: update \"COLLABORATIVE_MANAGEMENT\" SET \"TASK_ID\" = ?, \"ISSUE_DATE\" = ?, \"ISSUE_PERSON\" = ?, \"ISSUE_PERSON_ID\" = ?, \"TYPE\" = ?, \"TASK_NAME\" = ?, \"COMPLETION_PERIOD\" = ?, \"COLLABORATIVE_RULE\" = ?, \"SYS_USER\" = ?, \"COLLABORATIVE_OBJECT\" = ?, \"EXECUTION_RULE\" = ?, \"EXECUTION_CONTENT\" = ?, \"TRIGGER_RULE\" = ?, \"LINKED_DATA_SOURCES\" = ?, \"PERIODICITY\" = ?, \"EXECUTIVE_UNIT\" = ?, \"MISSION_REQUIREMENT\" = ?, \"RELATED_ATTACHMENT\" = ?, \"TASK_STATUS\" = ?, \"SITE_SELECTION\" = ?, \"DIVISION\" = ?, \"EXECUTION_STATUS\" = ? where \"ID\" = ? ";
        String parametersLine = "13:17:00.088 [schedule-pool-8] DEBUG n.p.s.i.r.C.updateByPrimaryKeySelective - [debug,159] - ==> Parameters: (String), 2020-11-07 00:00:00.0(Timestamp), 3(String), 74(Integer), 30(Integer), ddaaaaaaaaaaaaaaaaaaaaaaaa(String), 2020-11-04 00:00:00.0(Timestamp), 10(Integer), 2696(Integer), 刘明(String), 20(Integer), [{\"feedback\":\"0\",\"nowTask\":\"0\",\"time\":\"2020-11-02 00:11:01\",\"status\":\"10\"},{\"feedback\":\"0\",\"nowTask\":\"0\",\"time\":\"2020-12-09 00:12:03\",\"status\":\"0\"},{\"feedback\":\"0\",\"nowTask\":\"0\",\"time\":\"2020-12-22 00:12:03\",\"status\":\"0\"},{\"feedback\":\"0\",\"nowTask\":\"0\",\"time\":\"2020-12-06 00:12:03\",\"status\":\"0\"}](String), 10(Integer), ddd(String), (String), (String), 1616(String), (String), 10(Integer), (String), (String), (String), 1(Integer)";
        final String[] strings = SqlProUtil.restoreSql(preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }

    @Test
    public void format5() {
        String preparingLine = "" +
                "c.s.w.c.m.C.find Preparing: \n" +
                "SELECT count(0) FROM cust_consign a\n" +
                "cust_consign a LEFT JOIN sys_users ON a.create_by=s.id AND a.consign_source=\n" +
                "\n" +
                "1LEFTJOINapp_user au ON a.create_by=au.id AND a.consign_source= 2 LEFT JOIN\n" +
                "\n" +
                "cuSt_customer cON a.customer_id=c.id LEFT JOIN C SELECT*FROM\n" +
                "\n" +
                "cust_electric_fence WHERE del_fLag=O GROUP BY consign_id) cef ONce f.consign_id\n" +
                "\n" +
                "=a.idLEFTJ0INsys_or gorgON a.org_id=org.id WHERE a.del_fLag= 0 AND\n" +
                "\n" +
                "a.is_common= 1 AND a.type = ? AND a.org_seq LIKE CONCAT(?， '%')";
        String parametersLine = "sc.s.w.c.m.C.find List_COUNT\n" +
                "\n" +
                "[debug： 159]]-==> Parameters:\n" +
                "\n" +
                "1(String), /root/蓝物流/豆腐店/(String)";
        final String[] strings = SqlProUtil.restoreSql(preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }


    @Test
    public void format6() {
        String preparingLine ="c.s.w.c.m.C.find Preparing: \n" +
                "select\n" +
                "    \"trade_way\" :: jsonb -> 0 ->>0 AS tradeWay,\n" +
                "    COALESCE( sum(substring(\"trade_way\"::jsonb -> 0 ->> 1,'(-?([0-9]*)(.[0-9]+)?)')::NUMERIC ),0) as amount\n" +
                "    from\n" +
                "    \"?\" as t1\n" +
                "    left join\n" +
                "    \"?\" as t2\n" +
                "    on t1.his_id = t2.id\n" +
                "    where\n" +
                "        cast(matching_result -> 0 ->> 'recordId' as INTEGER) = ?\n" +
                "        and \"trade_way\" :: jsonb -> 0 ->>0 is not null\n" +
                "        and t1.is_invalid = 0\n" +
                "    group by\n" +
                "    tradeWay";
        String parametersLine = "sc.s.w.c.m.C.find List_COUNT\n" +
                "\n" +
                "[debug： 159]]-==> Parameters:\n" +
                "\n" +
                "hisMatch_2020(String), hisMatch_2020(String), ddd(String)";
        final String[] strings = SqlProUtil.restoreSql(preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }


    @Test
    public void format7() {
        String preparingLine ="16:51:11.228 DEBUG [Test worker] c.p.d.h.m.H.insert             : ==>  Preparing: insert into his_match_main (his_id, is_invalid, matching_result, create_time, modify_time) values (?, ?, ?, ?, ?)\n";
        String parametersLine = "16:51:11.246 DEBUG [Test worker] c.p.d.h.m.H.insert             : ==> Parameters: 999999(Integer), null, {}(String), 2021-02-24 16:51:11.184(Timestamp), 2021-02-24 16:51:11.184(Timestamp)\n";
        final String[] strings = SqlProUtil.restoreSql(preparingLine, parametersLine);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }
}
