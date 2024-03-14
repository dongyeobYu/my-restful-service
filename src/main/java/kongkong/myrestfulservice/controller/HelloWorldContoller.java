package kongkong.myrestfulservice.controller;

import kongkong.myrestfulservice.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldContoller {

    private final MessageSource messageSource;

    public HelloWorldContoller(MessageSource messageSource){
        this.messageSource=messageSource;
    }

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

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloworldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(path = "/hello-world-internationalize")
    public String helloworldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
