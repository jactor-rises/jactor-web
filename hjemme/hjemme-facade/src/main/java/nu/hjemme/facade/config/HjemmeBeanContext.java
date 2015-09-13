package nu.hjemme.facade.config;

import nu.hjemme.business.domain.UserDomain;
import nu.hjemme.business.domain.dao.UserDomainDao;
import nu.hjemme.business.service.MenuFacadeImpl;
import nu.hjemme.business.service.UserFacadeImpl;
import nu.hjemme.client.domain.menu.Menu;
import nu.hjemme.client.service.MenuFacade;
import nu.hjemme.client.service.UserFacade;
import nu.hjemme.facade.aop.ChosenMenuItemAspect;
import nu.hjemme.persistence.PersistentData;
import nu.hjemme.persistence.dao.UserDao;
import nu.hjemme.persistence.dao.UserDaoDb;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;
import java.util.Locale;

@Configuration
@EnableAspectJAutoProxy
public class HjemmeBeanContext {

    public HjemmeBeanContext() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Bean(name = "hjemme.menuFacade") @SuppressWarnings("unused") // initialized by spring
    public MenuFacade menuFacade(List<Menu> menus) {
        return new MenuFacadeImpl(menus);
    }

    @Bean(name = "hjemme.userFacade") @SuppressWarnings("unused")  // initialized by spring
    public UserFacade userFacade(SessionFactory sessionFactory) {
        return new UserFacadeImpl(PersistentData.getInstance().provideInstanceFor(UserDao.class, sessionFactory));
    }

    @Bean(name = "hjemme.userDao") @SuppressWarnings("unused")  // initialized by spring
    public UserDao userDao(SessionFactory sessionFactory) {
        return new UserDaoDb(sessionFactory);
    }

    @Bean(name = "hjemme.aop.chosenMenuItems") @SuppressWarnings("unused") // initialized by spring
    public ChosenMenuItemAspect chosenMenuItemAspect() {
        return new ChosenMenuItemAspect();
    }

    @Bean(name = "hjemme.domains.aware.db") @SuppressWarnings("unused") // initialized by spring
    public UserDomainDao domainConfig(UserDao userDao) {
        UserDomainDao userDomainDao = new UserDomainDao(userDao);
        UserDomain.setUserDomainDao(userDomainDao);

        return userDomainDao;
    }
}
