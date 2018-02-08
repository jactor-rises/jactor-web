package com.github.jactorrises.web.menu;

import java.util.List;

/** Finds {@link MenuItem}s by {@link MenuTarget} */
public interface MenuFacade {

    /**
     * @param menuTargetRequest determine the menu where the menu item target belongs.
     * @return a list of {@link MenuItem} according to the request.
     */
    List<MenuItem> fetchMenuItemBy(MenuTargetRequest menuTargetRequest);
}
