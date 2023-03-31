package com.plugins.mybaitslog.console;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A <code>SqlVO</code> Class
 *
 * @author lk
 * @version 1.0
 * @date 2022/8/29 19:11
 */
@Data
@Accessors(chain = true)
public class SqlVO {

    private String id;

    private String originalSql;

    private String completeSql;

    private String parameter;

    private Integer total;

}
