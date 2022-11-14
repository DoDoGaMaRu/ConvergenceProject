package service;

import persistence.dao.MenuDAO;
import persistence.dao.OrdersDAO;
import persistence.dao.StoreRegistDAO;
import persistence.dao.UserDAO;
import persistence.dto.OrdersDTO;
import persistence.dto.StoreDTO;
import persistence.dto.StoreRegistDTO;
import persistence.dto.UserDTO;
import persistence.enums.Authority;
import persistence.enums.OrdersStatus;
import persistence.enums.RegistStatus;
import view.OrdersView;

import java.util.ArrayList;
import java.util.List;

public class OwnerService {
    private UserDAO userDAO;
    private StoreRegistDAO storeRegistDAO;
    private MenuDAO menuDAO;
    private OrdersDAO ordersDAO;
    private OrdersView ordersView;

    public OwnerService(UserDAO userDAO, StoreRegistDAO storeRegistDAO, MenuDAO menuDAO, OrdersDAO ordersDAO, OrdersView ordersView) {
        this.userDAO = userDAO;
        this.storeRegistDAO = storeRegistDAO;
        this.menuDAO = menuDAO;
        this.ordersDAO = ordersDAO;
        this.ordersView = ordersView;
    }

    public Long insertOwner(String id, String pw, String name, String phone, Integer age) {
        return userDAO.insertUser(Authority.OWNER, id, pw, name, phone, age);
    }

    public void insertStoreRegist(String name, String comment, String phone, String address, Long user_pk) {
        storeRegistDAO.insertRegistration(name, comment, phone, address, user_pk);
    }

    public void updateMenu(Long menu_id, String name, Integer price) {
        menuDAO.updateNameAndPrice(menu_id, name, price);
    }

    public void viewOrdersList(Long store_id) {
        List<OrdersDTO> ordersList = getOrdersList(store_id);
        ordersView.ordersViewForOwner(ordersList, menuDAO);
    }

    public List<OrdersDTO> getOrdersList(Long store_id) {
        return ordersDAO.selectAllWithStore_id(store_id);
    }

    public void acceptOrder(Long id) {
        ordersDAO.updateStatus(OrdersStatus.IN_DELIVERY, id);
    }

    public void completeOrder(Long id) {
        ordersDAO.updateStatus(OrdersStatus.COMPLETE, id);
    }

    public void cancelOrder(Long id) {
        ordersDAO.updateStatus(OrdersStatus.CANCEL, id);
    }
}
