package service;

import persistence.dao.MenuDAO;
import persistence.dao.OrdersDAO;
import persistence.dto.OrdersDTO;
import persistence.dao.UserDAO;
import persistence.enums.Authority;
import persistence.enums.OrdersStatus;
import view.OrdersView;

import java.util.List;

public class UserService {
    private UserDAO userDAO;
    private MenuDAO menuDAO;
    private OrdersDAO ordersDAO;
    OrdersView ordersView;

    public UserService(UserDAO userDAO, MenuDAO menuDAO, OrdersDAO ordersDAO, OrdersView ordersView) {
        this.userDAO = userDAO;
        this.menuDAO = menuDAO;
        this.ordersDAO = ordersDAO;
        this.ordersView = ordersView;
    }

    public Long insertUser(String id, String pw, String name, String phone, Integer age) {
        return userDAO.insertUser(Authority.USER, id, pw, name, phone, age);
    }

    public void cancelOrder(Long orders_id) {
        OrdersDTO ordersDTO = ordersDAO.selectOneWithId(orders_id);

        if(ordersDTO.getStatusEnum() == OrdersStatus.HOLD) {
            ordersDAO.updateStatus(OrdersStatus.CANCEL, orders_id);
            System.out.println("주문을 취소하였습니다.");
            viewOrdersList(ordersDTO.getUser_pk());
        }
        else {
            System.out.println("이미 배달중인 주문은 취소가 불가능합니다.");
        }
    }

    public void viewOrdersList(Long user_pk) {
        List<OrdersDTO> ordersList = getOrdersList(user_pk);
        ordersView.ordersViewForOwner(ordersList, menuDAO);
    }

    public List<OrdersDTO> getOrdersList(Long user_pk) {
        return ordersDAO.selectAllWithUser_pk(user_pk);
    }
}