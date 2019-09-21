package ruslan.kovshar.view;

public interface SQL {
    /*Sql queries for Users*/
    String INSERT_NEW_USER = "INSERT INTO users (email,password,first_name_ua,second_name_ua,first_name_en,second_name_en) VALUES (?,?,?,?,?,?)";
    String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";

    /*Sql queries for Money*/
    String INSERT_NEW_AMOUNT = "INSERT INTO user_money VALUES (?,?)";
    String UPDATE_AMOUNT_OF_MONEY = "UPDATE user_money SET value = ? WHERE user_id = ?";

    /*Sql queries for Roles*/
    String INSERT_NEW_ROLE = "INSERT INTO user_roles VALUES (?,?)";
    String SELECT_ROLE = "SELECT * FROM user_roles WHERE user_id = ?";

    /*Sql queries for Products*/
    String INSERT_NEW_PRODUCT = "INSERT INTO products (code,name_en,name_ua,price,type) VALUES (?,?,?,?,?)";
    String SELECT_PRODUCT_BY_CODE = "SELECT * FROM products WHERE code = ?";
    String SELECT_PRODUCT_BY_NAME = "SELECT * FROM products WHERE name_en = ? OR name_ua = ?";

    /*Sql queries for Stock*/
    String UPDATE_STOCK = "UPDATE stock SET count_of_product = ? WHERE product_id = ?";
    String ADD_PRODUCT_TO_STOCK = "INSERT INTO stock (count_of_product,product_id) VALUES (?,?)";
    String SELECT_STOCK_BY_PRODUCT = "SELECT * FROM stock WHERE product_id = ?";

    /*Sql queries for Check*/
    String INSERT_NEW_CHECK = "INSERT INTO checks (total_price, user_id, buyer_id) VALUES (?,?,?)";
    String SELECT_ALL_CHECK = "SELECT * FROM checks";
    String SELECT_CHECK_BY_USER = "SELECT * FROM checks WHERE user_id = ?";
    String DELETE_CHECK = "DELETE FROM checks WHERE id = ?";

    /*Sql queries for ProductInCheck*/
    String INSERT_NEW_PRODUCT_IN_CHECK = "INSERT INTO product_in_check (price, value, check_id, product_id) VALUES (?,?,?,?)";
    String SELECT_PRODUCTS_BY_CHECK = "SELECT * FROM product_in_check JOIN products ON products.id = product_id WHERE check_id = ?";

    /*Sql queries for Buyer*/
    String INSERT_NEW_BUYER = "INSERT INTO buyer_info (name_on_card,card_number) VALUES (?,?)";
}
