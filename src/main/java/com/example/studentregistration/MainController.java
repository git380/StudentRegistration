package com.example.studentregistration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String submit(@RequestParam String student_id, Model model) {
        model.addAttribute("student_id", student_id);
        return "redirect:/main.html";
    }

    @PostMapping("/finish")
    public String finish(@RequestParam String student_id, Model model) {
        String html = "";
        try {
            html = new String(Files.readAllBytes(Paths.get("templates/main.html")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(html);
        Elements inputTags = doc.select("input");
        for (Element inputTag : inputTags) {
            String nameAttr = inputTag.attr("name");
            if (!nameAttr.isEmpty()) {
                inputTag.val(nameAttr);
            }
        }
        String modifiedHtml = doc.toString();
        model.addAttribute("modifiedHtml", modifiedHtml);
        return "finish";
    }
}
