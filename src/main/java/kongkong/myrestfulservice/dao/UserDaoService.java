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
                .build()
        );
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

    /**
     * 관계형 DB 사용 X
     * */
    public User findOne(Long id){
        for(User user : userList){
            if(Objects.equals(user.getId(), id)){
                return user;
            }
        }
            return null;
    }

    public User deleteById(Long id){
        Iterator<User> iterator = userList.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();

            if(Objects.equals(user.getId(), id)){
                iterator.remove();
                return user;
            }
        }

        return null;
    }

}
