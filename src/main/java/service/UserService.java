package service;

import persistence.dao.MenuDAO;
import persistence.dao.OrdersDAO;
import persistence.dao.ReviewDAO;
import persistence.dao.UserDAO;
import persistence.dto.OrdersDTO;
import persistence.dto.ReviewDTO;
import persistence.dto.UserDTO;
import persistence.enums.Authority;
import persistence.enums.OrdersStatus;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {
    private UserDAO userDAO;
    private OrdersDAO ordersDAO;
    private MenuDAO menuDAO;
    private ReviewDAO reviewDAO;

    public UserService(UserDAO userDAO, OrdersDAO ordersDAO, MenuDAO menuDAO, ReviewDAO reviewDAO) {
        this.userDAO = userDAO;
        this.ordersDAO = ordersDAO;
        this.menuDAO = menuDAO;
        this.reviewDAO = reviewDAO;
    }

    public Long insertUser(String id, String pw, String name, String phone, Integer age) {
        return userDAO.insertUser(Authority.USER, id, pw, name, phone, age);
    }

    public UserDTO getUserWithId(String id) {
        return userDAO.selectOneWithId(id);
    }

    public synchronized void order(String details, Integer price, String comment, Long menu_id, Long user_pk, Long store_id) {
        Long order_id = ordersDAO.insertOrders(LocalDateTime.now(), details, price, comment, menu_id, user_pk, store_id);
        if (0 < menuDAO.selectOneWithId(menu_id).getStock()) {
            ordersDAO.updateForInsert(menu_id);
        }
        else {
            ordersDAO.updateStatus(OrdersStatus.CANCEL, order_id);
        }
    }

    public void writeReview(String contents, Integer star_rating, Long user_pk, Long orders_id) {
        OrdersDTO ordersDTO = ordersDAO.selectOneWithId(orders_id);

        if(ordersDTO.getStatusEnum() == OrdersStatus.COMPLETE && (1 <= star_rating && star_rating <= 5)) {
            reviewDAO.insertReview(contents, LocalDateTime.now(), star_rating, user_pk, orders_id);
        }

    }

    public void cancelOrder(Long orders_id) {
        OrdersDTO ordersDTO = ordersDAO.selectOneWithId(orders_id);

        if(ordersDTO.getStatusEnum() == OrdersStatus.HOLD) {
            ordersDAO.updateStatus(OrdersStatus.CANCEL, orders_id);
            System.out.println("주문을 취소하였습니다.");
        }
        else {
            System.out.println("이미 배달중인 주문은 취소가 불가능합니다.");
        }
    }

    public List<OrdersDTO> getEndedOrders(Long user_pk) {
        return ordersDAO.selectAllEndedWithUser_pk(user_pk);
    }

    public List<ReviewDTO> getReviewList(Long user_pk, int page) {
        return reviewDAO.getReviewList(user_pk, page);
    }
}
