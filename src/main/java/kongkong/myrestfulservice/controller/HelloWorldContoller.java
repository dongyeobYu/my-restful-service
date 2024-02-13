package kongkong.myrestfulservice.controller;

import kongkong.myrestfulservice.bean.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldContoller {

    // GET
    // URL - /hello-world
    // @RequestMapping(method=ReuqestMethod.GET, path="/hello-world")

    @GetMapping(path = "/hello-world")
    public String helloworld() {
        return "hello-world";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloworldBean(){
        return new HelloWorldBean("Hello World");
    }

}
