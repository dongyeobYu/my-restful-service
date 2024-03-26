package kongkong.myrestfulservice.dao;

import kongkong.myrestfulservice.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class UserDaoService {

    private static List<User> userList = new ArrayList<>();


    static {
        userList.add(User.builder()
                .id(1L)
                .name("Yu")
                .joinDate(LocalDateTime.now())
                .password("TEST1")
                .ssn("111111-1111111")
                .build()
        );
        User user2 = User.builder()
                .id(2L)
                .name("Jeon")
                .joinDate(LocalDateTime.now())
                .password("TEST2")
                .ssn("222222-2222222")
                .build();
        User user3 = User.builder()
                .id(3L)
                .name("Park")
                .joinDate(LocalDateTime.now())
                .password("TEST3")
                .ssn("333333-3333333")
                .build();
        User user4 = User.builder()
                .id(4L)
                .name("Kim")
                .joinDate(LocalDateTime.now())
                .password("TEST4")
                .ssn("444444-4444444")
                .build();

        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
    }

    public List<User> findAll() {
        return userList;
    }

    public User save(User user) {

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
