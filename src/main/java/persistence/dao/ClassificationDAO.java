package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.MyBatisConnectionFactory;
import persistence.dto.ClassificationDTO;

import java.util.List;

public class ClassificationDAO extends DAO<ClassificationDTO>{
    private static ClassificationDAO classificationDAO;
    static {
        if (classificationDAO == null) {
            classificationDAO = new ClassificationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        }
    }
    public static ClassificationDAO getClassificationDAO() { return classificationDAO; }

    private ClassificationDAO(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory, "mapper.ClassificationMapper.");
    }


    @Override
    protected List<ClassificationDTO> selectList(SqlSession session, Object[] arg) throws Exception {
        return session.selectList(sqlMapperPath + arg[0], arg[1]);
    }
    @Override
    protected ClassificationDTO selectOne(SqlSession session, Object[] arg) throws Exception {
        return null;
    }
    @Override
    protected int insert(SqlSession session, Object[] arg) throws Exception {
        return session.insert(sqlMapperPath + arg[0], arg[1]);
    }
    @Override
    protected int update(SqlSession session, Object[] arg) throws Exception {
        return 0;
    }
    @Override
    protected int delete(SqlSession session, Object[] arg) throws Exception {
        return 0;
    }

    public List<ClassificationDTO> selectAllWithStore_id(Long store_id) {
        String stmt = "selectAllWithStore_id";
        ClassificationDTO classificationDTO = new ClassificationDTO(null, null, store_id);

        return selectList(stmt, classificationDTO);
    }

    public int insertClassification(String name, Long store_id) {
        String stmt = "insertClassification";
        ClassificationDTO classificationDTO = new ClassificationDTO(null, name, store_id);

        return insert(stmt, classificationDTO);
    }
}
