package com.plugins.mybaitslog.type;

/**
 * Java 数据类型
 *
 * @author St.kai
 * @date 2021-09-05 03:41
 * @since 2.0.5
 */
public enum JavaType {
    String(false, null),
    Date(true, LiteralType.DATE),
    Time(true, LiteralType.TIME),
    Timestamp(true, LiteralType.TIMESTAMP),
    LocalDate(true, LiteralType.DATE),
    LocalTime(true, LiteralType.TIME),
    LocalDateTime(true, LiteralType.TIMESTAMP);
    /**
     * 是否显示字面量
     */
    private final boolean showLiteral;
    /**
     * 字面量名称
     */
    private final String literalName;

    JavaType(boolean showLiteral, String literalName) {
        this.showLiteral = showLiteral;
        this.literalName = literalName;
    }


    public boolean isShowLiteral() {
        return showLiteral;
    }

    public String getLiteralName() {
        return literalName;
    }
}

class LiteralType {

    protected static final String DATE = "date";
    protected static final String TIME = "time";
    protected static final String TIMESTAMP = "timestamp";
}
