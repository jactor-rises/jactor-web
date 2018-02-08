package com.github.jactorrises.web.interceptor;

import com.github.jactorrises.web.menu.MenuFacade;
import com.github.jactorrises.web.menu.MenuItem;
import com.github.jactorrises.web.menu.MenuTarget;
import com.github.jactorrises.web.menu.MenuTargetRequest;
import com.github.jactorrises.web.menu.Menu;
import com.github.jactorrises.web.menu.MenuItemTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/** The {@link MenuInterceptor} is a {@link HandlerInterceptorAdapter}  which put the menus on the model. */
@Component
class MenuInterceptor extends HandlerInterceptorAdapter {

    private MenuFacade menuFacade;

    @Override public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) {
        MenuItemTarget menuItemTarget = new MenuItemTarget(request);
        MenuTargetRequest mainMenuTargetRequest = new MenuTargetRequest(new MenuTarget(menuItemTarget, InterceptorValues.MAIN_MENU));
        MenuTargetRequest personMenuTargetRequest = new MenuTargetRequest(new MenuTarget(menuItemTarget, InterceptorValues.PERSON_MENU));

        Menu mainMenu = new Menu(InterceptorValues.MAIN_MENU, menuFacade.fetchMenuItemBy(mainMenuTargetRequest));
        Menu personMenu = new Menu(InterceptorValues.PERSON_MENU, menuFacade.fetchMenuItemBy(personMenuTargetRequest));

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
        modelMap.put(InterceptorValues.ATTRIBUTE_MAIN_ITEMS, menuItemsFromMainMenu);
        modelMap.put(InterceptorValues.ATTRIBUTE_PERSON_ITEMS, menuItemsFromPersonMenu);
    }

    @Autowired public void setMenuFacade(MenuFacade menuFacade) {
        this.menuFacade = menuFacade;
    }
}
