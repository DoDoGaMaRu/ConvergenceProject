package service;

import persistence.dao.*;
import persistence.dto.*;
import persistence.enums.OrdersStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class OwnerService {
    private UserDAO userDAO;
    private StoreDAO storeDAO;
    private MenuDAO menuDAO;
    private TotalOrdersDAO totalOrdersDAO;
    private OrdersDAO ordersDAO;
    private ReviewDAO reviewDAO;
    private ClassificationDAO classificationDAO;

    public OwnerService(UserDAO userDAO, StoreDAO storeDAO, MenuDAO menuDAO, OrdersDAO ordersDAO) {
        this.userDAO = userDAO;
        this.storeDAO = storeDAO;
        this.menuDAO = menuDAO;
        this.ordersDAO = ordersDAO;
    }

    public Long insertOwner(UserDTO user) {
        return userDAO.insertOwner(user);
    }

    public int insertStore(StoreDTO store) {
        return storeDAO.insertStore(store);
    }


    public void insertMenu(MenuDTO menu, List<Long> detailsList) {
        Long menu_id = menuDAO.insertMenu(menu);
        for (Long details_id : detailsList) {
            menuDAO.insertMenuDetails(menu_id, details_id);
        }
    }

    public int updateTime(Long store_id, LocalDateTime open_time, LocalDateTime close_time) {
        return storeDAO.updateTime(store_id, open_time, close_time);
    }

    public int updateMenu(Long menu_id, String name, Integer price) {
        return menuDAO.updateNameAndPrice(menu_id, name, price);
    }

    public List<TotalOrdersDTO> getTotalOrders(Long store_id) {
        return totalOrdersDAO.selectAllWithStoreId(store_id);
    }
    
    public List<OrdersDTO> getOrders(Long total_order_id) {
        return ordersDAO.selectAllWithTotal_orders_id(total_order_id);
    }

    public int acceptOrders(Long id) {
        return totalOrdersDAO.updateStatus(id, OrdersStatus.IN_DELIVERY);
    }

    public int cancelOrders(Long id) {
        return totalOrdersDAO.updateStatus(id, OrdersStatus.CANCEL);
    }

    public int deliveryFinish(Long id) {
        return totalOrdersDAO.updateStatus(id, OrdersStatus.COMPLETE);
    }

    public List<ReviewDTO> getReviewList(Long store_id, Integer page) {
        return reviewDAO.selectAllWithStoreId(store_id, page);
    }

    public int updateOwnerComment(Long review_id, String comment) {
        return reviewDAO.updateOwnerComment(review_id, comment);
    }

    public void viewInformation(Long store_id) {
        List<ClassificationDTO> groups = classificationDAO.selectAllWithStore_id(store_id);
        List<MenuDTO> menus;
        MenuDTO menu;

        int idx = 1;
        for (ClassificationDTO group : groups) {
            menus = menuDAO.selectAllWithClassification_id(group.getId());

            for(MenuDTO dto : menus) {
                menu = menuDAO.viewInformation(dto.getId());
                System.out.println(idx++ + ". " + menu.getName() + ", " + menu.getStock() + ", " + menu.getPrice());
            }
        }
    }
}
