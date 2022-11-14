package view;

import persistence.dao.MenuDAO;
import persistence.dto.*;

import java.util.List;

public class OrdersView {
    public void ordersViewForOwner(List<OrdersDTO> ordersList, MenuDAO menuDAO) {

        System.out.println("[주문내역]");
        String menu_name = null;

        for (OrdersDTO order : ordersList) {
            menu_name = menuDAO.selectOneWithId(order.getMenu_id()).getName();
            System.out.println("회원" + order.getUser_pk() + ", " + menu_name + ", " + order.getDetails() + ", " + order.getStatus());
        }
    }
}
