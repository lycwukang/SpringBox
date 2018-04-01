package springbox.test.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class HomeController {

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET)
    public Object home() {
        String profile = "";
        if (env.getDefaultProfiles().length != 0) {
            profile = env.getDefaultProfiles()[env.getDefaultProfiles().length - 1];
        }
        if (env.getActiveProfiles().length != 0) {
            profile = env.getActiveProfiles()[env.getActiveProfiles().length - 1];
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "true");
        result.put("profile", profile);
        return result;
    }
}