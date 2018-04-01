package springbox.test.member.web;

import org.springbox.core.BeanDefinitionRegistrySupport;
import org.springbox.core.ContextBox;

@ContextBox(classes = {
        springbox.test.sms.RootConfig.class
})
public class SpringBoxConfig extends BeanDefinitionRegistrySupport {
}