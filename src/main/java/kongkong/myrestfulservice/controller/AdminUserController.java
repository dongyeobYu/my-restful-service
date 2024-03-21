package kongkong.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.SimpleBeanPropertyDefinition;
import kongkong.myrestfulservice.dao.UserDaoService;
import kongkong.myrestfulservice.domain.AdminUser;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final UserDaoService service;

    @GetMapping("/users/{id}")
    public ResponseEntity<MappingJacksonValue> retrieveUserByIdForAdmin(@PathVariable Long id){
        User user = service.findOne(id);

        if(user == null){
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
}
