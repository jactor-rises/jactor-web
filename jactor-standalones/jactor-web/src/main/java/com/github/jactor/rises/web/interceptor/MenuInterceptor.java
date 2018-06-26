package com.github.jactor.rises.web.interceptor;

import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.web.menu.Menu;
import com.github.jactor.rises.web.menu.MenuFacade;
import com.github.jactor.rises.web.menu.MenuItem;
import com.github.jactor.rises.web.menu.MenuItemTarget;
import com.github.jactor.rises.web.menu.MenuTarget;
import com.github.jactor.rises.web.menu.MenuTargetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * The {@link MenuInterceptor} is a {@link HandlerInterceptorAdapter}  which put the menus on the model.
 */
@Component
public class MenuInterceptor extends HandlerInterceptorAdapter {

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
