import com.example.web.model.entity.User;
import com.example.web.model.mapper.UserMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class LoginTest {
    @Test
    public void method1() {
        Long userid = 1111111111L; //测试账户
        String password = "111";   //测试密码
        User user = null;
        try (SqlSession session = MyBatisUtil.getFactory().openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.getUserByLogin(userid, password);
            if (user != null) {
                if (user.getLevel() == 1) {
                    System.out.println("管理员");
                } else if (user.getLevel() == 2) {
                    System.out.println("教师");
                } else {
                    System.out.println("学生");
                }
            } else {
                System.out.println("密码错误");
            }
        }
    }

    @Test
    public void method2() {
        Long userid = 2L; //测试账户
        String password = "111";   //测试密码
        User user = null;
        try (SqlSession session = MyBatisUtil.getFactory().openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.getUserByLogin(userid, password);
            if (user != null) {
                if (user.getLevel() == 1) {
                    System.out.println("管理员");
                } else if (user.getLevel() == 2) {
                    System.out.println("教师");
                } else {
                    System.out.println("学生");
                }
            } else {
                System.out.println("密码错误");
            }
        }
    }

    @Test
    public void method3() {
        Long userid = 9999999999L; //测试账户
        String password = "111";   //测试密码
        User user = null;
        try (SqlSession session = MyBatisUtil.getFactory().openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.getUserByLogin(userid, password);
            if (user != null) {
                if (user.getLevel() == 1) {
                    System.out.println("管理员");
                } else if (user.getLevel() == 2) {
                    System.out.println("教师");
                } else {
                    System.out.println("学生");
                }
            } else {
                System.out.println("密码错误");
            }
        }
    }

    @Test
    public void method4() {
        Long userid = 9999999999L; //测试账户
        String password = "admin";   //测试密码
        User user = null;
        try (SqlSession session = MyBatisUtil.getFactory().openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.getUserByLogin(userid, password);
            if (user != null) {
                if (user.getLevel() == 1) {
                    System.out.println("管理员");
                } else if (user.getLevel() == 2) {
                    System.out.println("教师");
                } else {
                    System.out.println("学生");
                }
            } else {
                System.out.println("密码错误");
            }
        }
    }
}
