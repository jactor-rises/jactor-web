package com.github.jactor.rises.web.controller;

import com.github.jactor.rises.client.datatype.Username;
import com.github.jactor.rises.client.domain.User;
import com.github.jactor.rises.client.facade.UserFacade;
import com.github.jactor.rises.web.dto.UserDto;
import com.github.jactor.rises.web.html.ParameterConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private @Mock UserFacade userFacadeMock;

    private @InjectMocks UserController testUserController;

    @BeforeEach
    void initMocking() {
        MockitoAnnotations.initMocks(this);
    }

    @Test void shouldNotFetchUserByUserNameIfTheUserNameInTheWebRequestIsNullOrAnEmptyString() {
        WebRequest webRequestMock = mock(WebRequest.class);
        when(webRequestMock.getParameter(ParameterConstants.CHOOSE_USER)).thenReturn(null);

        testUserController.doUser(mock(ModelMap.class), webRequestMock);

        verify(userFacadeMock, never()).find(any(Username.class));
        when(webRequestMock.getParameter(ParameterConstants.CHOOSE_USER)).thenReturn(" \n \t");

        testUserController.doUser(mock(ModelMap.class), webRequestMock);

        verify(userFacadeMock, never()).find(any(Username.class));
    }

    @Test void shouldFetchTheUserIfChooseParameterExist() {
        WebRequest mockedWebRequest = mock(WebRequest.class);
        ModelMap mockedModelMap = mock(ModelMap.class);
        User mockedUser = mock(User.class);

        when(mockedWebRequest.getParameter(ParameterConstants.CHOOSE_USER)).thenReturn("user");
        when(userFacadeMock.find(new Username("user"))).thenReturn(Optional.of(mockedUser));

        testUserController.doUser(mockedModelMap, mockedWebRequest);

        verify(mockedModelMap, atLeastOnce()).put(eq(ControllerValues.ATTRIBUTE_USER), any(UserDto.class));
    }

    @Test void shouldNotPutTheUserOnTheModelIfNotFound() {
        WebRequest mockedWebRequest = mock(WebRequest.class);
        ModelMap mockedModelMap = mock(ModelMap.class);

        testUserController.doUser(mockedModelMap, mockedWebRequest);

        verify(mockedModelMap, never()).put(eq(ControllerValues.ATTRIBUTE_USER), any(UserDto.class));
    }
}
