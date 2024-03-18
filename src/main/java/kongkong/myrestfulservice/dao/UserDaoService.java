package kongkong.myrestfulservice.dao;

import kongkong.myrestfulservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoService {

    private static List<User> userList = new ArrayList<>();

    private static Long userCount = 3L;

    static {
        userList.add(User.builder()
                .id(1L)
                .name("Yu")
                .joinDate(new Date())
                .password("TEST1")
                .ssn("SSN1")
                .build()
        );
        User user2 = User.builder()
                .id(2L)
                .name("Jeon")
                .joinDate(new Date())
                .password("TEST2")
                .ssn("SSN2")
                .build();
        User user3 = User.builder()
                .id(3L)
                .name("Park")
                .joinDate(new Date())
                .password("TEST3")
                .ssn("SSN3")
                .build();
        User user4 = User.builder()
                .id(4L)
                .name("Kim")
                .joinDate(new Date())
                .password("TEST4")
                .ssn("SSN4")
                .build();

        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
    }

    public List<User> findAll() {
        return userList;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }

        if (user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        userList.add(user);

        return user;
    }

    /**
     * 관계형 DB 사용 X
     */
    public User findOne(Long id) {
        for (User user : userList) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(Long id) {
        Iterator<User> iterator = userList.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (Objects.equals(user.getId(), id)) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

}
