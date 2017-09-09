package com.github.jactorrises.web.menu;

import com.github.jactorrises.web.menu.builder.MenuBuilder;
import com.github.jactorrises.client.datatype.Name;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/** A {@link Menu} that contains a collection of {@link MenuItem}s. */
public class Menu {

    private final Name menuName;
    private final List<MenuItem> menuItems = new ArrayList<>();

    public Menu(Name menuName, List<MenuItem> menuItems) {
        this.menuName = menuName;
        this.menuItems.addAll(menuItems);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(menuName)
                .append(menuItems)
                .toString();
    }

    public Name getName() {
        return menuName;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }
}