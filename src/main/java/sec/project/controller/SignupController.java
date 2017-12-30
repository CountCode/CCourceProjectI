package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.domain.Signup;
import sec.project.repository.FeedbackRepository;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    CustomUserDetailsService userService;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Model model) {
    //    model.addAttribute("feedbacks", feedbackRepository.findByAudience(Boolean.TRUE));
        return "form";
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String loadDone(Model model) {
        model.addAttribute("feedbacks", feedbackRepository.findByAudience(Boolean.TRUE));
        return "done";
    }    
    
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
//        System.out.println("Name: "+name);
// For demo purposes I use name and address as a username and password
// This not one of the meant flaws in the assigment
        if (signupRepository.findByName(name)==null){
            userService.newUser(name, address);
            signupRepository.save(new Signup(name, address));
            return "redirect:/done";
        } else {
            return "redirect:/form";
        }
    }

    // @Secured("ADMIN") // to fix A6 this needs to enabled
    @RequestMapping(value = "/signup/{id}", method = RequestMethod.DELETE)
    public String deleteSignup(@PathVariable Long id){
//        System.out.println("DELETE SIGNUP");
        userService.removeUser(signupRepository.findOne(id).getName());
        signupRepository.delete(id);
        return "redirect:/register";
    }

    @RequestMapping(value = "/signup/{id}", method = RequestMethod.GET)
    public String getSignup(@PathVariable Long id, Model model){
//        System.out.println("DELETE SIGNUP");
        model.addAttribute("signup", signupRepository.findOne(id));
        return "signup";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupQuery(@RequestParam String who, Model model) throws SQLException {
  
        // Open connection jdbc:h2:mem:testdb
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");

        // Execute query and retrieve the query results
        String query = "SELECT * FROM signup where name='"+who+"';";
        System.out.println("Q: "+query);
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM signup where name='"+who+"';");

        // List results
        List<Signup> signups = new ArrayList<>();
        while (resultSet.next()) {
            Long id = Long.parseLong(resultSet.getString("id"), 10);    
            Signup signup = signupRepository.findOne(id);
            signups.add(signup);
        }
        // Close the connection
        resultSet.close();
        connection.close();
        model.addAttribute("signups", signups);
        model.addAttribute("feedbacks", feedbackRepository.findByAdm(Boolean.TRUE));        
        return "register";
    }      
    
}
