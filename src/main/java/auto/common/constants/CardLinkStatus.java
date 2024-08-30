package auto.common.constants;

public enum CardLinkStatus {

    invalid(0),

    valid(1);

    private int status;

    public int getStatus() {
        return status;
    }

    CardLinkStatus(int status) {
        this.status = status;
    }
}
