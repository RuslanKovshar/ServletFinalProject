package ruslan.kovshar.view;

public interface URI {
    String HOME = "/";
    String ANY_PATH = "/**";
    String LOGIN = "/login";
    String MERCHANDISER = "/merchandiser";
    String REGISTRATION = "/registration";
    String SENIOR_CASHIER = "/senior_cashier";
    String STOCK = "/stock";
    String CHECK = "/check";
    String PRODUCT = "/product";
    String ADD_PRODUCT = "/add_product";
    String REMOVE_PRODUCT = "/remove_product";
    String ID = "/{id}";
    String OPEN_CHECK = "/open_check";
    String CLOSE_CHECK = "/close_check";
    String ADD = "/add";
    String X_REPORT = "/x-report";
    String Z_REPORT = "/z-report";
    String API = "/api";
    String CASHIER = "/cashier";
    String REDIRECT = "redirect:";
    String LOGOUT = "/logout";
    String PAYMENT = "/payment";
    String CHECKS = "/checks";
}
