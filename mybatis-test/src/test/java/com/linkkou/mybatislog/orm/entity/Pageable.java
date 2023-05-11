package com.linkkou.mybatislog.orm.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A <code>Pageable</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/5/10 09:47</b></p>
 */
@Data
@Accessors(chain = true)
public class Pageable {

    private int offset;

    private int pageSize;

}
