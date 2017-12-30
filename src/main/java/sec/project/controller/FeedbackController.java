package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Feedback;
import sec.project.domain.Signup;
import sec.project.repository.FeedbackRepository;
import sec.project.repository.SignupRepository;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private SignupRepository signupRepository;    
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("signups", signupRepository.findAll());
        model.addAttribute("feedbacks", feedbackRepository.findByAdm(Boolean.TRUE));
        
        return "register";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String submitFeeback(@RequestParam String content, @RequestParam(value = "adm", required=false) String adm, @RequestParam(value = "audience", required=false) String audience){
//        System.out.println("FEEDBACK");
        Feedback feedback = new Feedback(content);
        if (adm!=null) feedback.setAdm(Boolean.TRUE);
        if (audience!=null) feedback.setAudience(Boolean.TRUE);        
        feedbackRepository.save(feedback);
        return "redirect:/done";
    }

    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.DELETE)
    public String deleteFeedback(@PathVariable Long id){
//        System.out.println("DELETE FEEDBACK");

        feedbackRepository.delete(id);
        return "redirect:/register";
    }
    

    
    
    
}
