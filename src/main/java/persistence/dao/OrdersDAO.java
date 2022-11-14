package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import persistence.MyBatisConnectionFactory;
import persistence.dto.OrdersDTO;
import persistence.enums.OrdersStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrdersDAO extends DAO<OrdersDTO> {
    public OrdersDAO(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory, "mapper.OrdersMapper.");
    }

    @Override
    protected List<OrdersDTO> selectList(SqlSession session, Object[] arg) {
        return session.selectList(sqlMapperPath + arg[0], arg[1]);
    }
    @Override
    protected OrdersDTO selectOne(SqlSession session, Object[] arg) {
        return session.selectOne(sqlMapperPath + arg[0], arg[1]);
    }
    @Override
    protected int insert(SqlSession session, Object[] arg) throws Exception{
        int sign = 0;
        sign += session.insert(sqlMapperPath + arg[0], arg[1]);
        sign += session.update(sqlMapperPath + "updateForInsert", arg[1]);
        if (sign < 2) {
            throw new Exception("Error");
        }
        return sign;
    }
    @Override
    protected int update(SqlSession session, Object[] arg) {
        return session.update(sqlMapperPath + arg[0], arg[1]);
    }
    @Override
    protected int delete(SqlSession session, Object[] arg) { return 0; }


    public Long insertOrders(LocalDateTime regdate, String details, Integer price, String comment, Long menu_id, Long user_pk, Long store_id) {
        String stmt = "insertOrders";
        OrdersDTO ordersDTO = new OrdersDTO(null, OrdersStatus.HOLD.getCode(), regdate, details, price, comment, menu_id, user_pk, store_id);
        insert(stmt, ordersDTO);

        return ordersDTO.getId();
    }

    public List<OrdersDTO> selectAllWithStore_id (Long store_id) {
        String stmt = "selectAllWithStore_id";
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setStore_id(store_id);

        return selectList(stmt, ordersDTO);
    }

    public List<OrdersDTO> selectAllWithUser_pk (Long user_pk) {
        String stmt = "selectAllWithUser_pk";
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setUser_pk(user_pk);

        return selectList(stmt, ordersDTO);
    }

    public List<OrdersDTO> selectAllEndedWithUser_pk (Long user_pk) {
        String stmt = "selectAllEndedWithUser_pk";
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setUser_pk(user_pk);

        return selectList(stmt, ordersDTO);
    }

    public OrdersDTO selectOneWithId (Long id) {
        String stmt = "selectOneWithId";
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setId(id);

        return selectOne(stmt, ordersDTO);
    }

    public int updateStatus(OrdersStatus status, Long id) {
        String stmt = "updateStatus";
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setStatus(status.getCode());
        ordersDTO.setId(id);

        return update(stmt, ordersDTO);
    }

    public int updateForInsert(Long menu_id) {
        String stmt = "updateForInsert";
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setMenu_id(menu_id);

        return update(stmt, ordersDTO);
    }
}