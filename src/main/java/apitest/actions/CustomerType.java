package apitest.actions;

public enum CustomerType {

    Service_Account("SERVICE_ACCOUNT"),
    Business_Customer("BUSINESS_CUSTOMER");


    public String url;

    private CustomerType(String tenantType) {
        this.url = tenantType;
    }

    public String toString() {
        return url;
    }

}
