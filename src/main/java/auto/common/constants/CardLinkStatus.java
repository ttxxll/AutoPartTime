package auto.common.constants;

public enum CardLinkStatus {

    invalid(0, "无效"),

    valid(1, "有效");

    private int status;

    private String msg;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    CardLinkStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static String getMsgByCode(int code) {
        for (CardLinkStatus value : CardLinkStatus.values()) {
            if (value.getStatus() == code) {
                return value.getMsg();
            }
        }
        return invalid.getMsg();
    }
}
