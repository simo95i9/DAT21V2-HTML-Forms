package shit.htmlforms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import shit.htmlforms.models.User;
import shit.htmlforms.services.UserService;

import java.util.stream.Collectors;

@Controller
public class FreeMoneyController {
    @Autowired
    private UserService userService;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/get", produces="text/plain")
    @ResponseBody
    public String get() {
        return userService.get()
                .stream()
                .map(User::toString)
                .collect(Collectors.joining("\n"));
    }

    @PostMapping("/submit-form")
    public String formHandler(
            RedirectAttributes attributes,
            @RequestParam(value="name") String name,
            @RequestParam(value="money") Double money,
            @RequestParam(value="email") String email
    ) {
        User user = new User(name, email, money);
        boolean success = userService.create(user);

        if (success) {
            attributes.addFlashAttribute("user", user);
            return "redirect:/success";
        }

        return "redirect:/failure";
    }

    @GetMapping("/success")
    @ResponseBody
    public String success(
            @ModelAttribute("user") User user
    ) {
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process("success", context);
    }

    @GetMapping("/failure")
    public String failure() {
        return "failure";
    }


}
