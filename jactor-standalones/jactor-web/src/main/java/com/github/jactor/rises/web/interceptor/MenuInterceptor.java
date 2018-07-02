package com.github.jactor.rises.web.interceptor;

import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.model.facade.menu.Menu;
import com.github.jactor.rises.model.facade.menu.MenuFacade;
import com.github.jactor.rises.model.facade.menu.MenuItem;
import com.github.jactor.rises.model.facade.menu.MenuItemTarget;
import com.github.jactor.rises.model.facade.menu.MenuTarget;
import com.github.jactor.rises.model.facade.menu.MenuTargetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class MenuInterceptor implements HandlerInterceptor {

    static final String ATTRIBUTE_MAIN_ITEMS = "mainItems";
    static final String ATTRIBUTE_PERSON_ITEMS = "personItems";
    private static final Name MAIN_MENU = new Name("main");
    private static final Name PERSON_MENU = new Name("person");

    private final MenuFacade menuFacade;

    public @Autowired MenuInterceptor(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;
    }

    public @Override void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) {
        MenuItemTarget menuItemTarget = new MenuItemTarget(request);
        MenuTargetRequest mainMenuTargetRequest = new MenuTargetRequest(new MenuTarget(menuItemTarget, MAIN_MENU));
        MenuTargetRequest personMenuTargetRequest = new MenuTargetRequest(new MenuTarget(menuItemTarget, PERSON_MENU));

        Menu mainMenu = new Menu(MAIN_MENU, menuFacade.fetchMenuItem(mainMenuTargetRequest));
        Menu personMenu = new Menu(PERSON_MENU, menuFacade.fetchMenuItem(personMenuTargetRequest));

        List<MenuItem> menuItemsFromMainMenu = mainMenu.getMenuItems();
        List<MenuItem> menuItemsFromPersonMenu = personMenu.getMenuItems();

        addMenuItemsToModelAndView(modelAndView, menuItemsFromMainMenu, menuItemsFromPersonMenu);
    }

    private void addMenuItemsToModelAndView(
            ModelAndView modelAndView,
            List<MenuItem> menuItemsFromMainMenu,
            List<MenuItem> menuItemsFromPersonMenu
    ) {
        Map<String, Object> modelMap = modelAndView.getModel();
        modelMap.put(ATTRIBUTE_MAIN_ITEMS, menuItemsFromMainMenu);
        modelMap.put(ATTRIBUTE_PERSON_ITEMS, menuItemsFromPersonMenu);
    }
}
