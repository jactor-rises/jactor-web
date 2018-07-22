package com.gitlab.jactor.rises.web.controller;

import com.gitlab.jactor.rises.model.datatype.Username;
import com.gitlab.jactor.rises.model.domain.User;
import com.gitlab.jactor.rises.io.facade.UserFacade;
import com.gitlab.jactor.rises.model.facade.JactorFacade;
import com.gitlab.jactor.rises.model.facade.MenuFacade;
import com.gitlab.jactor.rises.model.facade.menu.MenuItem;
import com.gitlab.jactor.rises.web.JactorWeb;
import com.gitlab.jactor.rises.web.i18n.MyMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static com.gitlab.jactor.rises.model.facade.menu.MenuItem.aMenuItem;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {JactorWeb.class})
@DisplayName("The UserController")
@PropertySource("classpath:application.properties")
class UserControllerTest {

    private static final String REQUEST_USER = "choose";
    private static final String USER_ENDPOINT = "/user";
    private static final String USER_JACTOR = "jactor";

    private MockMvc mockMvc;
    private @Autowired MyMessages myMessages;
    private @MockBean UserFacade userFacadeMock;
    private @MockBean MenuFacade menuFacadeMock;
    private @Value("${spring.mvc.view.prefix}") String prefix;
    private @Value("${spring.mvc.view.suffix}") String suffix;

    @BeforeEach void mockMvcWithViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix(prefix);
        internalResourceViewResolver.setSuffix(suffix);

        mockMvc = standaloneSetup(new UserController(userFacadeMock, menuFacadeMock))
                .setViewResolvers(internalResourceViewResolver)
                .build();
    }

    @DisplayName("should not fetch user by username if the username is missing from the request")
    @Test void shouldNotFetchUserByUsernameIfTheUsernameIsMissongFromTheRequest() throws Exception {
        mockMvc.perform(get(USER_ENDPOINT)).andExpect(status().isOk());

        verify(userFacadeMock, never()).find(any(Username.class));
    }

    @DisplayName("should not fetch user by username when the username is requested, but is only whitespace")
    @Test void shouldNotFetchUserByUsernameIfTheUsernameInTheRequestIsNullOrAnEmptyString() throws Exception {
        mockMvc.perform(
                get(USER_ENDPOINT).requestAttr(REQUEST_USER, " \n \t")
        ).andExpect(status().isOk());

        verify(userFacadeMock, never()).find(any(Username.class));
    }

    @DisplayName("should fetch user by username when the username is requested")
    @Test void shouldFetchTheUserIfChooseParameterExist() throws Exception {
        when(userFacadeMock.find(new Username(USER_JACTOR))).thenReturn(Optional.of(mock(User.class)));

        ModelAndView modelAndView = mockMvc.perform(
                get(USER_ENDPOINT).param(REQUEST_USER, USER_JACTOR)
        ).andExpect(status().isOk()).andReturn().getModelAndView();

        assertThat(modelAndView.getModel().get("user")).isNotNull();
    }

    @DisplayName("should fetch user by username, but not find user")
    @Test void shouldFetchTheUserByUsernameButNotFindUser() throws Exception {
        when(userFacadeMock.find(any(Username.class))).thenReturn(Optional.empty());

        ModelAndView modelAndView = mockMvc.perform(
                get(USER_ENDPOINT).param(REQUEST_USER, "someone")
        ).andExpect(status().isOk()).andReturn().getModelAndView();

        assertThat(modelAndView.getModel().get("unknownUser")).isEqualTo("someone");
    }

    @DisplayName("should add the users menu to the model")
    @Test void shouldAddUserMenuToTheModel() throws Exception {
        when(menuFacadeMock.fetchMenuItems(JactorFacade.MENU_USERS)).thenReturn(singletonList(aMenuItem().build()));

        Map<String, Object> model = mockMvc.perform(
                get(USER_ENDPOINT).param(REQUEST_USER, USER_JACTOR)
        ).andExpect(status().isOk()).andReturn().getModelAndView().getModel();

        //noinspection unchecked
        assertThat((Collection<MenuItem>) model.get("usersMenu")).isNotEmpty();
    }
}
