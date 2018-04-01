package springbox.test.web.web;

import org.springbox.core.BeanDefinitionRegistrySupport;
import org.springbox.core.ContextBox;

@ContextBox(classes = {
        springbox.test.account.RootConfig.class,
        springbox.test.sms.RootConfig.class,
        springbox.test.member.RootConfig.class
})
public class SpringBoxConfig extends BeanDefinitionRegistrySupport {
}