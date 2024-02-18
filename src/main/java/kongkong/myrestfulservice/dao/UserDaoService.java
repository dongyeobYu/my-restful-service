package kongkong.myrestfulservice.dao;

import kongkong.myrestfulservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class UserDaoService {

    private static List<User> userList = new ArrayList<>();

    private static Long userCount = 3L;

    static {
        userList.add(new User(1L, "Yu", new Date()));
        userList.add(new User(2L, "Jeon", new Date()));
        userList.add(new User(3L, "Kim", new Date()));
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

    public User finaOne(Long id){
        for(User user : userList){
            if(Objects.equals(user.getId(), id)){
                return user;
            }
        }
            return null;
    }

}
