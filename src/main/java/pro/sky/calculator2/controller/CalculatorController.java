package pro.sky.calculator2.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.calculator2.service.CalculatorService;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String hello() {
        return "Добро пожаловать в калькулятор!";
    }

    @GetMapping("/plus")
    public String plus(@RequestParam(value = "num1", required = false) Integer a,
                       @RequestParam(value = "num2", required = false) Integer b) {
        return buildView(a, b, "+");
    }

    @GetMapping("/minus")
    public String minus(@RequestParam(value = "num1", required = false) Integer a,
                        @RequestParam(value = "num2", required = false) Integer b) {
        return buildView(a, b, "-");
    }

    @GetMapping("/multiply")
    public String multiply(@RequestParam(value = "num1", required = false) Integer a,
                           @RequestParam(value = "num2", required = false) Integer b) {
        return buildView(a, b, "*");
    }

    @GetMapping("/divide")
    public String divide(@RequestParam(value = "num1", required = false) Integer a,
                         @RequestParam(value = "num2", required = false) Integer b) {
        return buildView(a, b, "/");
    }

    private String buildView(Integer a, Integer b, String operation) {
        if (a == null || b == null) {
            return "Не передан один или оба параметра :(";
        }
        if ("/".equals(operation) && b == 0) {
            return "На ноль делить нельзя :(";
        }
        Number result = switch (operation) {
            case "+" -> calculatorService.plus(a, b);
            case "-" -> calculatorService.minus(a, b);
            case "*" -> calculatorService.multiply(a, b);
            default -> calculatorService.divide(a, b);
        };
        return a + " " + operation + " " + b + " = " + result;
    }
}
