package kongkong.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kongkong.myrestfulservice.dao.UserDaoService;
import kongkong.myrestfulservice.domain.AdminUser;
import kongkong.myrestfulservice.domain.AdminUserV2;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final UserDaoService service;

    // ---> /admin/v1/users/{id}
    //@GetMapping(value = "/users/{id}", params="version=1")      // param 으로 관리, Amazon 에서 사용
    //@GetMapping(value = "/users/{id}", headers="X-API-VERSION=1")      // header 으로 관리, MS 에서 사용
    //@GetMapping(value = "/users/{id}", produces="application/vnd.company.appv1+json")      // mime-type 으로 관리, GitHub 에서 사용
    @GetMapping("/v1/users/{id}")             // version 으로 관리
    public ResponseEntity<MappingJacksonValue> retrieveUserByIdForAdminV1(@PathVariable Long id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // adminUser 에 User 객체 복사 (불변성 o)
        AdminUser adminUser = AdminUser.copyUser(user);

        /**
         * JsonFilter 사용
         * */
        // Filter 설정 
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn", "password");

        // @JsonFilter 에 설정해둔 ID값을 쓰고 해당 도메인에 걸 필터를 지정해줌
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userInfo", filter);

        // adminUser에 filterProvider 를 걸어서 필터 설정
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminUser);
        mappingJacksonValue.setFilters(filterProvider);

        return ResponseEntity.ok(mappingJacksonValue);
    }

    //@GetMapping(value = "/users/{id}", params="version=2")      // param 으로 관리
    //@GetMapping(value = "/users/{id}", headers="X-API-VERSION=2")      // header 으로 관리
    //@GetMapping(value = "/users/{id}", produces="application/vnd.company.appv2+json")      // mime-type 으로 관리
    @GetMapping("/v2/users/{id}")             // version 으로 관리
    public ResponseEntity<MappingJacksonValue> retrieveUserByIdForAdminV2(@PathVariable Long id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // adminUser 에 User 객체 복사 (불변성 o)
        AdminUserV2 adminUserV2 = AdminUserV2.copyUser(user, "VIP");

        /**
         * JsonFilter 사용
         * */
        // Filter 설정
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");

        // @JsonFilter 에 설정해둔 ID값을 쓰고 해당 도메인에 걸 필터를 지정해줌
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userInfoV2", filter);

        // adminUser에 filterProvider 를 걸어서 필터 설정
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminUserV2);
        mappingJacksonValue.setFilters(filterProvider);

        return ResponseEntity.ok(mappingJacksonValue);
    }


    // -->> /admin/users
    @GetMapping("/users")
    public ResponseEntity<MappingJacksonValue> retrieveAllUsersAdmin() {
        List<User> user = service.findAll();

        // adminUser 에 User 객체 복사 (불변성 o)
        List<AdminUser> adminUsers = AdminUser.copyUserList(user);

        /**
         * JsonFilter 사용
         * */
        // Filter 설정
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn", "password");

        // @JsonFilter 에 설정해둔 ID값을 쓰고 해당 도메인에 걸 필터를 지정해줌
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userInfo", filter);

        // adminUser에 filterProvider 를 걸어서 필터 설정
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminUsers);
        mappingJacksonValue.setFilters(filterProvider);

        return ResponseEntity.ok(mappingJacksonValue);
    }
}
