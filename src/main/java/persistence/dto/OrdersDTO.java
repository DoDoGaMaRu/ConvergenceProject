package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import persistence.enums.OrdersStatus;
import persistence.enums.RegistStatus;

import java.time.LocalDateTime;


@Getter
@Setter
public class OrdersDTO extends DTO {
    private Long id;
    private OrdersStatus status;
    private LocalDateTime regdate;
    private String details;
    private Integer price;
    private String comment;
    private Long menu_id;           // FK
    private Long total_orders_id;    // FK
    private Long store_id;          // FK

    public OrdersDTO() {}

    public OrdersDTO(Long id, Integer status, LocalDateTime regdate, String details, Integer price, String comment, Long menu_id, Long total_orders_id, Long store_id) {
        this.id = id;
        this.status = OrdersStatus.of(status);
        this.regdate = regdate;
        this.details = details;
        this.price = price;
        this.comment = comment;
        this.menu_id = menu_id;
        this.total_orders_id = total_orders_id;
        this.store_id = store_id;
    }

    public OrdersDTO(byte[] arr) {
        setMembersByByteArray(arr);
    }

    public void setStatus(Integer status) {
        this.status = OrdersStatus.of(status);
    }

    public Integer getStatus() {
        return status.getCode();
    }

    public OrdersStatus getStatusEnum() {
        return status;
    }
}